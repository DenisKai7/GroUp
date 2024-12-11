import  express from "express";
import modelController from "../controller/model_controller.js";

const modelRouter = new express.Router();

// model API
modelRouter.post("/api/predicts/stunting", modelController.stunting);
modelRouter.post("/api/predicts/similiarity", modelController.similiarity);

export {
    modelRouter
}