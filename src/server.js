import {web} from "./application/web.js";
import { logger } from "./application/loggin.js";
import dotenv from "dotenv";
dotenv.config();

const PORT = process.env.NODE_ENV !== "production" ? 3000 : 8080;
const HOST = process.env.NODE_ENV === "production" ? "0.0.0.0" : "localhost";


web.listen(PORT, HOST,() => {
    logger.info(`app running in this url http//:${HOST}:${PORT}`);
});