package com.example.fyp

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.blindvision.fyp.R
import com.otaliastudios.cameraview.CameraView
import kotlinx.android.synthetic.main.activity_detection.*


class DetectionActivity : AppCompatActivity() {

    var x: kotlin.Double = 0.0
    var y: kotlin.Double = 0.0
    var z: kotlin.Double = 0.0
    var dist: kotlin.Double = 0.0
    var phoneHeight = 1.22
    val dna = DnA_Activity()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sm = getSystemService(SENSOR_SERVICE) as SensorManager
        val accelerometer: Sensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        sm.registerListener(object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                x = dna.setX(
                    event.values[0].toDouble(),
                    event.values[1].toDouble(),
                    event.values[2].toDouble()
                )
                y = dna.setY(
                    event.values[0].toDouble(),
                    event.values[1].toDouble(),
                    event.values[2].toDouble()
                )
                z = dna.setZ(
                    event.values[0].toDouble(),
                    event.values[1].toDouble(),
                    event.values[2].toDouble()
                )
                dist = dna.getDistance(y, z, phoneHeight)
            }

            override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
        }, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)


        setContentView(R.layout.activity_detection)
        val audio = Audio(this, "en", "GB")
        val video = Video()
        val detector = Detector()
        val classifier = Classifier()
        val frameProcessor = FrameProcessor()
        val view = findViewById<CameraView>(R.id.cameraView)
        var classified: String = "";

        detector.load_model()
        classifier.load_model(applicationContext)
        video.video_capture(view, this)

        view.addFrameProcessor {

//            Added a null check because CameraView returns a null frame every now and then
            if (it != null && it.data != null) {

//            Convert the frame to byte Array
                val image = frameProcessor.prepare_frame(it)
                detector.detect(image) {
                    classifier.classify(image) { result ->
                        if (result == "") {
                            tvDetectedObject.text = ""
                            classified = "";
                        } else {
                            if (result == classified) {
//                                tvDetectedObject.text = result + " , " + dist.toString();
                            } else {
                                if (dist < 2) {
                                    tvDetectedObject.text = result + " , " + dist.toString();
                                    audio.generate_audio(result.toString() + " is too near")
                                } else {
                                    tvDetectedObject.text = result;
                                    audio.generate_audio(result.toString())
                                }
                            }
                        }
                        classified = result;
//                        if (result != classified && dist < 2) {
//
//                            if (result == "") {
//                                tvDetectedObject.text = ""
//                                classified = "";
////                            tvDetectedObject.text = "No detection";
////                            audio.generate_audio("Nothing detected")
//                            } else {
//                                tvDetectedObject.text = result + " , " + dist.toString();
//                                audio.generate_audio(result.toString() + " is too near")
//                            }
//
//                        } else if (result != classified) {
//                            tvDetectedObject.text = classified;
//                            audio.generate_audio(result.toString())
//                        }

                    }
                }
            }
        }
    }

}