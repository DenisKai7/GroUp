import { Firestore } from '@google-cloud/firestore';

// const db = new Firestore();
const db = new Firestore({
  projectId: 'capstone-test-442616',
  keyFilename: './key.json',
});


export { 
  db
};