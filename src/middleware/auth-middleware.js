import jwt from "jsonwebtoken";
import userService from "../service/user_service.js"; 
import dotenv from "dotenv";
dotenv.config();

export const authMiddleware = async (req, res, next) => {
    const token = req.headers.authorization?.split(" ")[1]; 
    if (!token) {
        return res.status(401).json({ errors: "Authorization token is missing" });
    }

    try {
        const decoded = jwt.verify(token, process.env.ACCESS_TOKEN_SECRET);

        req.user = decoded;
        next();
    } catch (err) {

        if (err instanceof jwt.TokenExpiredError) {

            const email = req.headers.authorization?.split(" ")[1]; 

            try {
                await userService.logout(email); 

                return res.status(401).json({
                    errors: "Access token expired, please login again",
                });
            } catch (logoutErr) {
                return res.status(500).json({
                    errors: "Error logging out the user",
                });
            }
        } else {
            return res.status(500).json({ errors: "Internal Server Error" });
        }
    }
};
