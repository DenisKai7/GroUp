const { Firestore } = require('@google-cloud/firestore');

// const db = new Firestore();
const db = new Firestore({
  projectId: 'capstone-test-442616',
  keyFilename: './key.json',
});


module.exports = { db };