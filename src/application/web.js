import express from "express";
import { publicRouter } from "../route/public_api.js";
import { errorMiddleware } from "../middleware/error_middleware.js";
import { userRouter } from "../route/user_api.js";
import { corsMiddleware } from "../middleware/cors_middleware.js";


export const web = express();

web.use(corsMiddleware);
web.use(express.json());
web.use(publicRouter);
web.use(userRouter);
web.use(errorMiddleware);