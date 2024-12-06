import scrapper from "../service/scraper_service.js";

export const getContent = async (req, res, next) => {
    try {
       const result = await scrapper.getContent(req.body);
       res.status(200).json({
        data: result
       });
    } catch (e) {
        next(e);
    }
}