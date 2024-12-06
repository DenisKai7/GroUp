import Joi from 'joi';

const registerUserValidation = Joi.object({
    email: Joi.string().max(100).required(),
    password: Joi.string().max(100).required(),
    name: Joi.string().max(100).required()
});

const LoginUserValidation = Joi.object({
    email: Joi.string().max(100).required(),
    password: Joi.string().max(100).required()
});

const getUserValidation = Joi.string().max(100).required();

const updateUserValidation = Joi.object({
    email: Joi.string().max(100).optional(),
    password: Joi.string().max(100).optional(),
    name: Joi.string().max(100).optional()
})

const logoutUserValidation = Joi.string().max(100).required();


export {
    registerUserValidation,
    LoginUserValidation,
    getUserValidation,
    updateUserValidation,
    logoutUserValidation
}