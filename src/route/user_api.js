import  express from "express";
import userController from "../controller/user_controller.js";
import activityController from "../controller/activity_controller.js";
import { authMiddleware} from "../middleware/auth-middleware.js";

const userRouter = new express.Router();

// User Api
userRouter.use(authMiddleware);
userRouter.get("/api/users/current", userController.get);
userRouter.patch("/api/users/current", userController.update);
userRouter.delete("/api/users/logout", userController.logout);

// Activity Api
userRouter.post("/api/activities", activityController.create);
userRouter.get("/api/activities/:activityId", activityController.get);
userRouter.patch("/api/activities/:activityId", activityController.update);
userRouter.delete("/api/activities/:activityId", activityController.remove);
userRouter.get("/api/activities/", activityController.search);

export {
    userRouter
}