import axios from 'axios';
import * as cheerio from 'cheerio';
import { db } from '../application/firestore.js';

const articlesCollection = db.collection('articles');

const getContent = async (req, res) => {
  try {

    const snapshot = await articlesCollection.get();

    if (snapshot.empty) {
      return res.json({ source: 'firestore', data: [] });
    }

    const articles = snapshot.docs.map(doc => doc.data());

    return articles;

  } catch (error) {
    console.error('Error fetching data from Firestore:', error);
    res.status(500).json({ message: 'Failed to fetch data', error: error.message });
  }
}


const scrapeWebsite = async (website) => {
  try {
    const response = await axios.get(website.url);
    const $ = cheerio.load(response.data);
    const articles = [];

    $("article").each((i, element) => {

      const title = $(element).find('span.ratiobox_content').find("img").attr("title") || 'kosong';
      const urlImage = $(element).find('span.ratiobox_content').children("img").attr('data-src') || 'kosong';
      const urlWeb = $(element).find('a').attr('href') || 'kosong';

      if (title.toLowerCase().includes('stunting')) {
        articles.push({
          title,
          urlImage,
          urlWeb,
          scrapedAt: new Date().toISOString()
        });
      }
    });

    await saveToFirestore(articles);

    return articles;

  } catch (error) {
    console.error(`Error scraping ${website.url}:`, error);
    return [];
  }
}

const saveToFirestore = async (articles) => {
  try {

    const snapshot = await articlesCollection.get();
    const batchDelete = db.batch();

    snapshot.forEach(doc => {
      batchDelete.delete(doc.ref);
    });

    await batchDelete.commit();
    console.log('Existing articles deleted.');

    const batchSave = db.batch();

    for (const article of articles) {
      const docRef = articlesCollection.doc(); 
      batchSave.set(docRef, article);
    }

    await batchSave.commit();
    console.log(`${articles.length} new articles saved to Firestore.`);
  } catch (error) {
    console.error('Error saving to Firestore:', error);
  }
}

export default {
  getContent,
  scrapeWebsite
}