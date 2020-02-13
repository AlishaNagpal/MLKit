package com.mlkit;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;

public class TextRecognitionModule extends ReactContextBaseJavaModule {
    private final ReactApplicationContext reactContext;

    ReadableMap options;
    protected Callback callback;
    String IMAGE_SOURCE = "imageSource";
    String resultText;

    TextRecognitionModule(ReactApplicationContext context) {
        super(context);
        reactContext = context;
    }

    @Override
    public String getName() {
        return "TextRecognitionModule";
    }

    @ReactMethod
    public void getSourceImage(final ReadableMap options, final Callback callback) {
        try {
            this.options = options;

            FirebaseVisionImage image = FirebaseVisionImage.fromFilePath(this.reactContext,
                    Uri.parse(options.getString(IMAGE_SOURCE)));
            FirebaseVisionTextRecognizer detector = FirebaseVision.getInstance().getOnDeviceTextRecognizer();

            Task<FirebaseVisionText> result = detector.processImage(image)
                    .addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
                        @Override
                        public void onSuccess(FirebaseVisionText firebaseVisionText) {
                            // Task completed successfully
                            resultText = firebaseVisionText.getText();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Task failed with an exception
                        }
                    });

            callback.invoke(resultText);

        } catch (Exception ex) {
            Log.e("ERR", ex.getMessage());
        }

    }
}