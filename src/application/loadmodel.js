import tf from '@tensorflow/tfjs-node';

async function loadModelStunting() {
    const murl = "file://models/model1/model.json";
    return tf.loadGraphModel(murl);
}

async function loadModelSimiliarity() {
    const murl = "file://models/model2/model.json";
    return tf.loadGraphModel(murl);
}

export {
    loadModelStunting,
    loadModelSimiliarity
}