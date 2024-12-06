import express from "express";
import { publicRouter } from "../route/scraper_api.js";
import { corsMiddleware } from "../middleware/cors_middleware.js";


export const web = express();

web.use(corsMiddleware);
web.use(express.json());
web.use(publicRouter);
