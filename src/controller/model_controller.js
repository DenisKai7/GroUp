import modelService from "../service/model_service.js";

const stunting = async (req, res, next) => {
    try {
        const request = req.body;
        const result = await modelService.stunting(request);
        res.status(200).json({
            data: result
        });
    } catch (e) {
        next(e);
    }
}

const similiarity = async (req, res, next) => {
    try {
        const request = req.body;
        const result = await modelService.similiarity(request);
        res.status(200).json({
            data: result
        });
    } catch (e) {
        next(e);
    }
}



export default {
    stunting,
    similiarity
}
