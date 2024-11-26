import userService from "../service/user_service.js";

const register = async (req, res, next) => {
    try {
       const result = await userService.register(req.body);
       res.status(200).json({
        data: result
       });
    } catch (e) {
        next(e);
    }
}
const login = async (req, res, next) => {
    try {
        const result = await userService.login(req.body);
        res.status(200).json({
            data: result
        });
    } catch (e) {
        next(e)
        
    }
}

const get = async (req, res, next) => {
    try {
        const email = req.user.email;
        const result = await userService.get(email);
        res.status(200).json({
            data:result
        })
    } catch (e) {
        next(e);
    }
};

const update = async (req, res, next) => {
    try {
        const email = req.user.email;
        const request = req.body;
        request.email = email;
        const result = await userService.update(request);
        res.status(200).json({
            data: result
        });
    } catch (e) {
        next(e);
        
    }
}

const logout = async (req, res, next) => {
    try {
       await userService.logout(req.user.email);
       res.status(200).json({
        data: "ok"
       });
    } catch (e) {
        next(e);
    }
}
export default {
    register,
    login,
    get,
    update,
    logout
}