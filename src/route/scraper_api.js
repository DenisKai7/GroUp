import  express from "express";
import { getContent } from "../controller/scraper_controller.js";

const publicRouter = new express.Router();

publicRouter.get("/api/scraping", getContent);

export {
    publicRouter
}