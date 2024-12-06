import { web } from './application/web.js';
import scrapper from "./service/scraper_service.js";
import schedule from 'node-schedule';
import { website } from './web/web_config.js';

let result = {};

schedule.scheduleJob('0 0 * * *', async () => {
result = await scrapper.scrapeWebsite(website);
}); 

result = await scrapper.scrapeWebsite(website);
console.log(result)
    
const PORT = process.env.NODE_ENV !== "production" ? 3000 : 8080;
const HOST = process.env.NODE_ENV === "production" ? "0.0.0.0" : "localhost";

web.listen(PORT, HOST,() => {
    console.info(`app running in this url http//:${HOST}:${PORT}`);
});