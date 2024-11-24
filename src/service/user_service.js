import { validate } from "../validation/validation.js";
import { db } from "../application/firestore.js";
import { LoginUserValidation, getUserValidation, registerUserValidation, updateUserValidation } from "../validation/user_validation.js";
import { ResponseError } from "../error/response_error.js";
import bcrypt from "bcrypt";
import {v4 as uuid} from "uuid";
import { logger } from "../application/loggin.js";


const userCollection = db.collection("users");

const register = async (request) => {
    const user = validate(registerUserValidation, request);

    // Periksa apakah username sudah ada
    const userSnapshot = await userCollection.where("username", "==", user.username).get();
    if (!userSnapshot.empty) {
        throw new ResponseError(400, "username already exists");
    }

    // Hash password sebelum menyimpan
    user.password = await bcrypt.hash(user.password, 10);

    // Buat dokumen baru di Firestore
    const userDoc = userCollection.doc();
    await userDoc.set(user);

    return {
        username: user.username,
        name: user.name,
    };
};

const login = async (request) => {
    const loginRequest = validate(LoginUserValidation, request);

    // Cari user berdasarkan username
    const userSnapshot = await userCollection.where("username", "==", loginRequest.username).get();
    if (userSnapshot.empty) {
        throw new ResponseError(401, "username or password wrong");
    }

    const userDoc = userSnapshot.docs[0];
    const user = userDoc.data();

    // Validasi password
    const passwordValidation = await bcrypt.compare(loginRequest.password, user.password);
    if (!passwordValidation) {
        throw new ResponseError(401, "username or password wrong");
    }

    // Generate token
    const token = uuid();
    logger.info(token);

    // Update token di Firestore
    await userCollection.doc(userDoc.id).update({ token });

    return { token };
};

const get = async (request) => {
    const username = validate(getUserValidation, request);

    // Cari user berdasarkan username
    const userSnapshot = await userCollection.where("username", "==", username).get();
    if (userSnapshot.empty) {
        throw new ResponseError(404, "user not found");
    }

    const user = userSnapshot.docs[0].data();
    return {
        username: user.username,
        name: user.name,
    };
};

const update = async (request) => {
    const user = validate(updateUserValidation, request);

    // Cari user berdasarkan username
    const userSnapshot = await userCollection.where("username", "==", user.username).get();
    if (userSnapshot.empty) {
        throw new ResponseError(404, "user not found");
    }

    const userDoc = userSnapshot.docs[0];
    const dataToUpdate = {};

    if (user.name) {
        dataToUpdate.name = user.name;
    }
    if (user.password) {
        dataToUpdate.password = await bcrypt.hash(user.password, 10);
    }

    // Update user di Firestore
    await userCollection.doc(userDoc.id).update(dataToUpdate);

    return {
        username: user.username,
        name: user.name || userDoc.data().name,
    };
};

const logout = async (username) => {
    username = validate(getUserValidation, username);

    // Cari user berdasarkan username
    const userSnapshot = await userCollection.where("username", "==", username).get();
    if (userSnapshot.empty) {
        throw new ResponseError(404, "user not found");
    }

    const userDoc = userSnapshot.docs[0];

    // Set token menjadi null
    await userCollection.doc(userDoc.id).update({ token: null });

    return { username };
};

export default {
    register,
    login,
    get,
    update,
    logout,
};