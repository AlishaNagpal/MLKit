import React from 'react';
import { NavigationContainer } from '@react-navigation/native';
import { createStackNavigator } from '@react-navigation/stack';
import TextRecognition from "./MLKitTrials/TextRecognition/TextRecognition"
import FaceDetection from "./MLKitTrials/FaceDetection/FaceDetection";

const Stack = createStackNavigator();

function App() {
    return (
      <NavigationContainer>
        <Stack.Navigator>
          {/* <Stack.Screen name="TextRecognition" component={TextRecognition} /> */}
          <Stack.Screen name="FaceDetection" component={FaceDetection} />
        </Stack.Navigator>
      </NavigationContainer>
    );
  }
  
  export default App;