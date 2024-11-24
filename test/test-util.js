import bcrypt from "bcrypt";
import { db } from "../src/application/firestore.js";

const userCollection = db.collection("users");
const activityCollection = db.collection("activities");

export const removeTestUser = async () => {
    const userSnapshot = await userCollection.where("username", "==", "test").get();
    userSnapshot.forEach(async (doc) => {
        await doc.ref.delete();
    });
};

export const createTestUser = async () => {
    const hashedPassword = await bcrypt.hash("xxx", 10);
    await userCollection.doc("test").set({
        username: "test",
        password: hashedPassword,
        name: "test",
        token: "test",
    });
};

export const getUser = async () => {
    const userDoc = await userCollection.doc("test").get();
    if (!userDoc.exists) {
        return null;
    }
    return userDoc.data();
};

// export const removeAllActivityUser = async () => {
//     const activitySnapshot = await activityCollection.where("username", "==", "test").get();
//     activitySnapshot.forEach(async (doc) => {
//         await doc.ref.delete();
//     });
// };

// export const createTestActivityUser = async () => {
//     await activityCollection.add({
//         username: "test",
//         title: "test",
//         information: "test",
//         day: "test",
//         date: "2024-01-01",
//         time: "12:00",
//     });
// };

// export const createManyTestActivityUser = async () => {
//     for (let i = 1; i <= 5; i++) {
//         await activityCollection.add({
//             username: "test",
//             title: `test${i}`,
//             information: "test",
//             day: `hari${i}`,
//             date: "2024-01-01",
//             time: "12:00",
//         });
//     }
// };

// export const getTestActivity = async () => {
//     const activitySnapshot = await activityCollection.where("username", "==", "test").limit(1).get();
//     if (activitySnapshot.empty) {
//         return null;
//     }
//     return activitySnapshot.docs[0].data();
// };
