import bcrypt from "bcrypt";
import jwt from "jsonwebtoken";
import { db } from "../src/application/firestore.js";
require('dotenv').config();

const userCollection = db.collection("users");
// const activityCollection = db.collection("activities");

export const removeTestUser = async () => {
    const userDoc = await userCollection.doc("test").get();
    if (userDoc.exists) {
        await userCollection.doc("test").delete();
    }
};

export const createTestUser = async () => {
    const hashedPassword = await bcrypt.hash("xxx", 10);
    await userCollection.doc("test").set({
        email: "test",
        password: hashedPassword,
        name: "test",
        status: "active",
    });
};

export const createAndLoginTestUser = async () => {
    const hashedPassword = await bcrypt.hash("xxx", 10);

    const accessToken = jwt.sign(
        { email: "test", name: "test" }, 
        process.env.ACCESS_TOKEN_SECRET,
        { expiresIn: "3d" } 
    );

    await userCollection.doc("test").set({
        email: "test",
        password: hashedPassword,
        name: "test",
        status: "active",
    });

    return accessToken; 
};


export const getUser = async () => {
    const userDoc = await userCollection.doc("test").get();
    if (!userDoc.exists) {
        return null;
    }
    return userDoc.data();
};

// export const removeAllActivityUser = async () => {
//     const activitySnapshot = await activityCollection.where("email", "==", "test").get();
//     activitySnapshot.forEach(async (doc) => {
//         await doc.ref.delete();
//     });
// };

// export const createTestActivityUser = async () => {
//     await activityCollection.add({
//         email: "test",
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
//             email: "test",
//             title: `test${i}`,
//             information: "test",
//             day: `hari${i}`,
//             date: "2024-01-01",
//             time: "12:00",
//         });
//     }
// };

// export const getTestActivity = async () => {
//     const activitySnapshot = await activityCollection.where("email", "==", "test").limit(1).get();
//     if (activitySnapshot.empty) {
//         return null;
//     }
//     return activitySnapshot.docs[0].data();
// };
