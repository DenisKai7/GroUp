import {web} from "./application/web.js";
import { logger } from "./application/loggin.js";

const PORT = process.env.NODE_ENV !== "production" ? 3000 : process.env.PORT;

web.listen(PORT, () => {
    logger.info(`app running in port ${PORT}`);
});