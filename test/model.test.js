import supertest from "supertest";
import { logger } from "../src/application/loggin.js";
import { web } from "../src/application/web.js";

describe("POST /api/predicts/stunting", function() {

    it("should can send data for prediction stunting", async () => {
        
        const result = await supertest(web)
        .post("/api/predicts/stunting")
        .send({
            name: "testimoni", 
            age: 10, 
            weight: 65, 
            height: 10, 
            gender: "Male"
          });

        logger.info(result.body);
        expect(result.status).toBe(200);
        expect(result.body.data.name).toBe("testimoni");
        expect(result.body.data.age).toBe(10);
        expect(result.body.data.weight).toBe(65);
        expect(result.body.data.gender).toBe("Male");
        expect(result.body.data.status).toBeDefined();
    });
    it("should reject age is required", async () => {
        const result = await supertest(web)
        .post("/api/predicts/stunting")
        .send({
            name: "testimoni", 
            age: "", 
            weight: "65", 
            height: "10", 
            gender: "Male"
          });

        logger.info(result.body);
        expect(result.status).toBe(400);
        expect(result.body.errors).toBeDefined();
    });
});