import { validate } from "../validation/validation.js";
import { db } from "../application/firestore.js";
import { LoginUserValidation, getUserValidation, registerUserValidation, updateUserValidation, logoutUserValidation } from "../validation/user_validation.js";
import { ResponseError } from "../error/response_error.js";
import jwt from "jsonwebtoken";
import bcrypt from "bcrypt";
import dotenv from "dotenv";
dotenv.config();


const userCollection = db.collection("users");

const register = async (request) => {
    const user = validate(registerUserValidation, request);

    const userSnapshot = await userCollection.where("email", "==", user.email).get();
    if (!userSnapshot.empty) {
        throw new ResponseError(400, "email already exists");
    }

    user.password = await bcrypt.hash(user.password, 10);
    const userDoc = userCollection.doc(user.email);
    await userDoc.set(user);
    return {
        email: user.email,
        name: user.name,
    };
};

const login = async (request) => {
    const loginRequest = validate(LoginUserValidation, request);
    const userDoc = await userCollection.doc(loginRequest.email).get();

    if (!userDoc.exists) {
        throw new ResponseError(401, "Email or password is wrong");
    }

    const user = userDoc.data();
    const passwordValidation = await bcrypt.compare(
        loginRequest.password,
        user.password
    );

    if (!passwordValidation) {
        throw new ResponseError(401, "Email or password is wrong");
    }
 
    const accessToken = jwt.sign(
        { email: user.email, name: user.name },
        process.env.ACCESS_TOKEN_SECRET,
        { expiresIn: "3d" } 
    );

    await userCollection.doc(user.email).update({ status: "active" });
    return {
        accessToken,
        status: "active"
    };
};

const get = async (request) => {
    const email = validate(getUserValidation, request);

    const userSnapshot = await userCollection.where("email", "==", email).get();
    if (userSnapshot.empty) {
        throw new ResponseError(404, "user not found");
    }

    const user = userSnapshot.docs[0].data();
    return {
        email: user.email,
        name: user.name,
        status: user.status
    };
};

const update = async (request) => {
    const user = validate(updateUserValidation, request);
    const userSnapshot = await userCollection.where("email", "==", user.email).get();

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

    await userCollection.doc(userDoc.id).update(dataToUpdate);
    return {
        email: user.email,
        name: user.name,
    };
};

const logout = async (email) => {
    email = validate(logoutUserValidation, email);


    const userDoc = await userCollection.doc(email).get();
    if (!userDoc.exists) {
        throw new ResponseError(404, "User not found");
    }


    await userCollection.doc(email).update({
        status: "inactive"
    });

    return {};
}

export default {
    register,
    login,
    get,
    update,
    logout,
};