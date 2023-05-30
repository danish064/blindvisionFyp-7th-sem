package com.example.fyp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.TextView;

import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import java.lang.Math;


import java.text.DecimalFormat;

public class DnA_Activity{

    public double setX(double x, double y,double  z) {
        double norm = Math.sqrt(x * x + y * y + z * z);
        x = x / norm;
        return x;
    }

    public double setY(double x, double y,double  z) {
        double norm = Math.sqrt(x * x + y * y + z * z);
        y = y / norm;
        return y;
    }

    public double setZ(double x, double y,double  z) {
        double norm = Math.sqrt(x * x + y * y + z * z);
        z = z / norm;
        return z;
    }

    public double getDistance(double y,double z,double phoneHeight){
        double inclination = Math.toDegrees(Math.acos(z));
        double angle;
        if (inclination > 90)
        {
            angle = 90.0;
        }
        else if (inclination < 0 || y < 0)
        {
            angle = 0.0;
        }
        else
        {
            angle = Math.round(inclination * 10.0) / 10.0;
        }
        double distanceAngle = angle;
        double tanAngle = Math.tan(Math.toRadians(angle));
        return Math.round((phoneHeight * tanAngle - 0.1) * 100.0) / 100.0;
    }


}