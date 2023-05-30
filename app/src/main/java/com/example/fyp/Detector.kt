package com.example.fyp

import android.graphics.Rect
import com.google.mlkit.common.model.LocalModel
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.objects.ObjectDetection
import com.google.mlkit.vision.objects.ObjectDetector
import com.google.mlkit.vision.objects.custom.CustomObjectDetectorOptions
import com.google.mlkit.vision.objects.defaults.ObjectDetectorOptions
import com.otaliastudios.cameraview.Frame

class Detector {

    lateinit var objectDetector: ObjectDetector

    public fun load_model()
    {
        val localModel = LocalModel.Builder()
            .setAssetFilePath("detect32.tflite")
            .build()

        val detector =
            CustomObjectDetectorOptions.Builder(localModel)
                .setDetectorMode(CustomObjectDetectorOptions.STREAM_MODE)
                .enableClassification()
                .setClassificationConfidenceThreshold(0.7f)
                .setMaxPerObjectLabelCount(3)
                .build()

        val detecter = ObjectDetectorOptions.Builder()
            .setDetectorMode(ObjectDetectorOptions.STREAM_MODE)
            .build()
        objectDetector = ObjectDetection.getClient(detector)
    }

    public fun detect(input: InputImage ,callback: (Rect) -> Unit) {

        var result = Rect(0,0,0,0)
        objectDetector.process(input)
            .addOnSuccessListener { detectedObjects ->
                for (detectedObject in detectedObjects) {
                    val boundingBox = detectedObject.boundingBox
                    val trackingId = detectedObject.trackingId
                    result = boundingBox
                    callback(result)
                }
            }
            .addOnFailureListener { e ->
            }
    }
}