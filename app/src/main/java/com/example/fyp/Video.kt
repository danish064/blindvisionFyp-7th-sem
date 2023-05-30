package com.example.fyp

import androidx.lifecycle.LifecycleOwner
import com.google.mlkit.vision.common.InputImage
import com.otaliastudios.cameraview.CameraView
import com.otaliastudios.cameraview.Frame

class Video
{
    lateinit var frameProcessor: FrameProcessor

    public fun video_capture(view: CameraView,owner: LifecycleOwner)
    {
        view.setLifecycleOwner(owner)
    }


}