import ImagePicker from 'react-native-image-crop-picker';


const pickImage = {

    getSinglePic: (callback) => {
        ImagePicker.openPicker({
            cropping: false
        }).then((image) => {
            // console.warn(image)
            callback(image.path)
        });
    },

    getMultiplePic: (callback) => {
        let temp = [];
        ImagePicker.openPicker({
            cropping: false,
            multiple: true,
            compressImageQuality: 0.1,
        }).then((image) => {
            temp = image.map(item => item.path);
            callback(temp)
        })
    },

    getCamera: (callback) => {
        ImagePicker.openCamera({
            cropping: true
        }).then((image) => {
            callback(image.path)
        });
    },

    getVideo: (callback) => {
        ImagePicker.openPicker({
            compressVideoPreset: 'Passthrough',
            mediaType: 'video',
        }).then((video) => {
            callback(video.path)
        });
    },
}


export default pickImage;