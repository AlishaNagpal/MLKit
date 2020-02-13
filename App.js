import React from 'react';
import { NavigationContainer } from '@react-navigation/native';
import { createStackNavigator } from '@react-navigation/stack';
import TextRecognition from "./MLKitTrials/TextRecognition/TextRecognition"

const Stack = createStackNavigator();

function App() {
    return (
      <NavigationContainer>
        <Stack.Navigator>
          <Stack.Screen name="TextRecognition" component={TextRecognition} />
        </Stack.Navigator>
      </NavigationContainer>
    );
  }
  
  export default App;