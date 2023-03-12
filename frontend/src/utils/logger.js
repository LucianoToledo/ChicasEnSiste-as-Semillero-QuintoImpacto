import { ENV } from "../environment/environment";

export const logger = (msg, title = "") => {
    if (ENV.MODE === "PROD") {
        return
    }
    console.log("---------------------------------------");
    console.log(`[${title}]`, msg);
    console.log("---------------------------------------");
}

export const logerror = (msg, title = "") => {
    if (ENV.MODE === "PROD") {
        return
    }
    console.error("---------------------------------------");
    console.error(`[${title}]`, msg);
    console.error("---------------------------------------");
}