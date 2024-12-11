import { validate } from "../validation/validation.js";
import { modelValidation } from "../validation/model_validation.js"
import { ResponseError } from "../error/response_error.js";
import tf from '@tensorflow/tfjs-node';
import { loadModelStunting, loadModelSimiliarity } from "../application/loadmodel.js";

const stunting = async (request) => {
    const dataModel = await validate(modelValidation, request);

    if(!dataModel) {
        throw new ResponseError(401, "data is not valid");
    }

    const dataTensor = prepareData(dataModel.age, dataModel.weight, dataModel.height, dataModel.gender);
    const model = await loadModelStunting();

    const prediction = model.predict(dataTensor);
    const predictionValue = prediction.dataSync()[0]; 

    const status = predictionValue >= 0.7 ? "Stunting" : "Normal";

    // console.log(`Prediction Value: ${predictionValue}`);
    // console.log(`Status: ${status}`);

    return {
      name: dataModel.name,
      age: dataModel.age,
      weight: dataModel.weight,
      height: dataModel.height,
      gender: dataModel.gender,
      status: status
    };
}

const prepareData = (age, weight, height, gender)  => {
    const genderNumeric = gender.toLowerCase() === 'male' ? 1 : 0;

    const inputData = [age, weight, height, genderNumeric];
    const inputTensor = tf.tensor2d([inputData]);
    return inputTensor;
}

const similiarity = async (request) => {
    const dataModel = await validate(modelValidation, request);

    if(!dataModel) {
        throw new ResponseError(401, "data is not valid");
    }

    const dataTensor = prepareData(dataModel.age, dataModel.weight, dataModel.height, dataModel.gender);
    const model = await loadModelSimiliarity();

    const prediction = model.predict(dataTensor);
    prediction.print();

    const predictionValue = prediction.dataSync()[0]; 

    const status = predictionValue >= 0.7 ? "Stunting" : "Normal";

    console.log(`Prediction Value: ${predictionValue}`);
    console.log(`Status: ${status}`);

    let age, height, weight;

    if (status === "Stunting") {
        age = dataModel.age - 2; 
        height = dataModel.height - 7; 
        weight = dataModel.weight - 10; 

        if (age < 0 || height < 0 || weight < 0) {
            age = dataModel.age + 1; 
            height = dataModel.height - 3;
            weight = dataModel.weight - 2; 
        }

    } else if (status === "Normal") {
        age = dataModel.age + 1; 
        height = dataModel.height + 10; 
        weight = dataModel.weight + 6;
    }

    return {
        age: age,
        weight: weight,
        height: height,
        gender: dataModel.gender,
        status: status
    };
}


export default {
    stunting,
    similiarity
}