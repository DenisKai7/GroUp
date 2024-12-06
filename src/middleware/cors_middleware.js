import cors from "cors";

export const corsMiddleware = cors({
    origin: '*',
    methods: 'GET',
    credentials: true,
    allowedHeaders: 'Content-Type, Authorization'
})