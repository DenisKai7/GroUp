import Joi from 'joi';

const modelValidation = Joi.object({
  name: Joi.string().max(20).required(),
  age: Joi.number().max(200).required(),
  weight: Joi.number().max(200).required(),
  height: Joi.number().max(300).required(),
  gender: Joi.string().valid('Male', 'Female').required(),
});



export {
    modelValidation
}