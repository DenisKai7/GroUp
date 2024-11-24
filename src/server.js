import {web} from "./application/web.js";
import { logger } from "./application/loggin.js";


web.listen(3000, () => {
    logger.info("app start");
});