import React, { Component } from 'react';
import { View, Text, TouchableOpacity, NativeModules } from 'react-native';
import styles from './styles'
import { pickImage } from "../Component";
const {TextRecognitionModule} = NativeModules

export default class TextRecognition extends Component {
    state = { text: '' }

    recogniseText = () => {
        pickImage.getSinglePic((response) => {
            let path = Platform.OS === 'ios' ? "file:///" + response : response
            this.getText(path)
        })
    }

    getText = async (path) => {
        let result = await new Promise((resolve, reject) => {
            Platform.OS === 'ios'
            ? NativeModules.TextRecognition.getSourceImage({
                imageSource: path,
            }, (source) => {
                resolve(source)
            })
            : TextRecognitionModule.getSourceImage({
                imageSource: path,
            }, (source) => {
                resolve(source)
            })
        })
        this.setState({
            text: result
        })
    }

    render() {
        return (
            <View style={styles.mainView}>
                <TouchableOpacity onPress={this.recogniseText}>
                    <Text style={styles.textStyle}> Process an Image for Text Recognition! </Text>
                </TouchableOpacity>
                <Text style={styles.recognisedText}> {this.state.text} </Text>
            </View>
        );
    }
}
