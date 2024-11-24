import { prismaClient } from "../application/database.js";
import { logger } from "../application/loggin.js";

export const authMiddleware = async (req, res, next) => {
    const token = req.headers.authorization;
    
    logger.info(token);
    if (!token){
        res.status(401).json({
            errors: "unauthorization"
        }).end();
    } else {
        const user = await prismaClient.user.findFirst({
            where: {
                token: token
            }
        });
        if (!user) {
            res.status(401).json({
                errors: "unauthorization"
        }).end();
        } else {
            req.user = user;
            next();
        }
    } 
}