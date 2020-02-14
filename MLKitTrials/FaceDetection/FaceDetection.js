import React, { Component } from 'react';
import { View, Text, TouchableOpacity, NativeModules, FlatList, Image } from 'react-native';
import styles from './styles'
import { pickImage } from "../Component";

export default class FaceDetection extends Component {
    state = { data: [], showImages: false }

    recogniseText = () => {
        pickImage.getSinglePic((response) => {
            let path = Platform.OS === 'ios' ? "file:///" + response : response
            this.getText(path)
        })
    }

    getText = async (path) => {
        this.setState({data: [] })
        let result = await new Promise((resolve, reject) => {
            NativeModules.FaceDetection.getSourceImage({
                imageSource: path,
            }, (source) => {
                resolve(source)
            })
        })
        this.setState({
            data: result,
            showImages: true
        })
    }

    renderDATA = (rowDATA) => {
        const { item } = rowDATA
        return (
            <View>
                <Image
                    source={{uri:item }}
                    style={styles.imageStyle}
                />
            </View>
        )
    }

    render() {
        return (
            <View style={styles.mainView}>
                <TouchableOpacity
                    onPress={this.recogniseText} >
                    <Text style={styles.textStyle}> Process an Image for Face Detection! </Text>
                </TouchableOpacity>
                {this.state.showImages &&
                    <FlatList
                        keyExtractor={(item, index) => (item + index).toString()}
                        data={this.state.data}
                        renderItem={this.renderDATA}
                        horizontal
                    />
                }
            </View>
        );
    }
}
