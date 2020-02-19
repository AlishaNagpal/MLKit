package com.mlkit;

import android.graphics.Rect;
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
import com.google.firebase.ml.vision.face.FirebaseVisionFace;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetector;

import java.util.*;


public class FaceDetectionModule extends ReactContextBaseJavaModule {
    private final ReactApplicationContext reactContext;

    ReadableMap options;
    protected Callback callback;
    String IMAGE_SOURCE = "imageSource";
    String resultText;

    FaceDetectionModule(ReactApplicationContext context) {
        super(context);
        reactContext = context;
    }

    @Override
    public String getName() {
        return "FaceDetectionModule";
    }

    @ReactMethod
    public void getSourceImage(final ReadableMap options, final Callback callback) {
        try {
            this.options = options;
            FirebaseVisionFaceDetectorOptions highAccuracyOpts = new FirebaseVisionFaceDetectorOptions.Builder()
                .setPerformanceMode(FirebaseVisionFaceDetectorOptions.ACCURATE)
                .setLandmarkMode(FirebaseVisionFaceDetectorOptions.ALL_LANDMARKS)
                .setClassificationMode(FirebaseVisionFaceDetectorOptions.ALL_CLASSIFICATIONS)
                .build();


            FirebaseVisionImage image = FirebaseVisionImage.fromFilePath(this.reactContext,
                    Uri.parse(options.getString(IMAGE_SOURCE)));
            FirebaseVisionFaceDetector detector = FirebaseVision.getInstance().getVisionFaceDetector(highAccuracyOpts);

            Task<List<FirebaseVisionFace>> result = detector.detectInImage(image)
                    .addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionFace>>() {
                            @Override
                            public void onSuccess(List<FirebaseVisionFace> faces) {
                                // Task completed successfully
                                // resultText = FirebaseVisionFace.getText();
                                for (FirebaseVisionFace face : faces) {
                                    Rect bounds = face.getBoundingBox();
                                    Log.d("Bounds", String.valueOf(bounds));
                                }
                            }
                        })
                    .addOnFailureListener(new OnFailureListener() {
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