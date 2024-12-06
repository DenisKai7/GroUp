import supertest from "supertest";
import { web } from "../src/application/web.js";
import { logger } from "../src/application/loggin.js";
import { createAndLoginTestUser, createTestUser, getUser, removeTestUser } from "./test-util.js";
import bcrypt from "bcrypt";


describe ("POST /api/users", function () {

    afterEach(async () => {
        await removeTestUser();
    });

    it("should can register new user", async () => {
        const result = await supertest(web)
        .post("/api/users")
        .send({
            email: "test",
            password: "xxx",
            name: "test"
        })
 
        expect(result.status).toBe(200);
        expect(result.body.data.email).toBe("test");
        expect(result.body.data.name).toBe("test");
        expect(result.body.data.password).toBeUndefined();

    });  
    it("should reject data is not be empty", async () => {
        const result = await supertest(web)
        .post("/api/users")
        .send({
            email: "",
            password: "",
            name: ""
        })
        logger.info(result.body);
        expect(result.status).toBe(400);
        expect(result.body.errors).toBeDefined();

    });

    it("should can already exsist", async () => {
        let result = await supertest(web)
        .post("/api/users")
        .send({
            email: "test",
            password: "xxx",
            name: "test"
        })
        logger.info(result.body);
        expect(result.status).toBe(200);
        expect(result.body.data.email).toBe("test");
        expect(result.body.data.name).toBe("test");
        expect(result.body.data.password).toBeUndefined();

        result = await supertest(web)
        .post("/api/users")
        .send({
            email: "test",
            password: "xxx",
            name: "test"
        })
        logger.info(result.body);
        expect(result.status).toBe(400);
        expect(result.body.errors).toBeDefined();

    });
});

describe ("POST /api/users/login", function () {
    beforeEach(async () => {
        await createTestUser();
    });
    afterEach(async () => {
        await removeTestUser();
    });

    it("should can login", async () => {
        const result = await supertest(web)
        .post("/api/users/login")
        .send({
            email: "test",
            password: "xxx",
        });

        logger.info(result.body);
        expect(result.status).toBe(200);
        expect(result.body.data.accessToken).toBeDefined();
        expect(result.body.data.status).toBe("active");
        expect(result.body.data.accessToken).not.toBe("test");
    });
    it("should reject data is not be empty", async () => {
        const result = await supertest(web)
        .post("/api/users/login")
        .send({
            email: "",
            password: "",
        });

        logger.info(result.body);
        expect(result.status).toBe(400);
        expect(result.body.errors).toBeDefined();
    });
    it("should reject data is invalid", async () => {
        const result = await supertest(web)
        .post("/api/users/login")
        .send({
            email: "test",
            password: "bbcv",
        });

        logger.info(result.body);
        expect(result.status).toBe(401);
        expect(result.body.errors).toBeDefined();
    });
});

describe("GET /api/users/current", function() {

    let token;

    beforeEach(async () => {
        token = await createAndLoginTestUser();
    });
    afterEach(async () => {
        await removeTestUser();
    });
    it("should can get user", async () => {
        const result = await supertest(web)
        .get("/api/users/current")
        .set("Authorization", `Bearer ${token}`);

        logger.info(result.body);
        expect(result.status).toBe(200);
        expect(result.body.data.email).toBe("test");
        expect(result.body.data.name).toBe("test");
    });
    it("should reject accessToken is not found", async () => {
        const result = await supertest(web)
        .get("/api/users/current")
        .set("Authorization", "testi");

        logger.info(result.body);
        expect(result.status).toBe(401);
        expect(result.body.errors).toBeDefined();

    });
});

describe("PATCH /api/users/current", function() {
    let token;
    beforeEach(async () => {
        token = await createAndLoginTestUser();
    });
    afterEach(async () => {
        await removeTestUser();
    });
    it("should can update name and password", async () => {
        const result = await supertest(web)
        .patch("/api/users/current")
        .set("Authorization", `Bearer ${token}`)
        .send({
            name: "gue",
            password: "tauahhhh"
        })

        logger.info(result.body);
        expect(result.status).toBe(200);
        expect(result.body.data.email).toBe("test");
        expect(result.body.data.name).toBe("gue");

        const user = await getUser();
        expect(await bcrypt.compare("tauahhhh", user.password)).toBe(true);
    });
    it("should can update email and name", async () => {
        const result = await supertest(web)
        .patch("/api/users/current")
        .set("Authorization", `Bearer ${token}`)
        .send({
            name: "gue"
        })

        logger.info(result.body);
        expect(result.status).toBe(200);
        expect(result.body.data.email).toBe("test");
        expect(result.body.data.name).toBe("gue");
    });

    it("should can update password", async () => {
        const result = await supertest(web)
        .patch("/api/users/current")
        .set("Authorization", `Bearer ${token}`)
        .send({
            password: "tauahhhh"
        })

        logger.info(result.body);
        expect(result.status).toBe(200);
        expect(result.body.data.email).toBe("test");
        const user = await getUser();
        expect(await bcrypt.compare("tauahhhh", user.password)).toBe(true);
    });
    it("should reject update", async () => {
        const result = await supertest(web)
        .patch("/api/users/current")
        .set("Authorization", "testi")
        .send({});

        logger.info(result.body);
        expect(result.status).toBe(401);
        
    });
});

describe("DELETE /api/users/logout", function() {
    let token;
    beforeEach(async () => {
        token = await createAndLoginTestUser();
    });
    afterEach(async () => {
        await removeTestUser();
    });
    it("should can logout", async () => {
        const result = await supertest(web)
        .delete("/api/users/logout")
        .set("Authorization", `Bearer ${token}`);

        logger.info(result.body);
        expect(result.status).toBe(200);
        expect(result.body.data).toBe("ok");

        const user = await getUser();
        expect(user.status).toBe("inactive");
    });
    it("should reject logout accessToken is invalid", async () => {
        const result = await supertest(web)
        .delete("/api/users/logout")
        .set("Authorization", "testi");

        logger.info(result.body);
        expect(result.status).toBe(401);
        
        const user = await getUser();
        expect(user.status).toBe("active");
    });
});