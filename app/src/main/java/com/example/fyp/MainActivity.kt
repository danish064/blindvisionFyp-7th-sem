package com.example.fyp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.biometric.BiometricPrompt.AuthenticationCallback
import androidx.core.content.ContextCompat
import com.blindvision.fyp.R
import java.util.concurrent.Executor

class MainActivity : AppCompatActivity() {

    lateinit var executor: Executor;
    lateinit var biometricPrompt: androidx.biometric.BiometricPrompt;
    lateinit var promptInfo: androidx.biometric.BiometricPrompt.PromptInfo;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val button = findViewById<ImageButton>(R.id.start)
        val button = findViewById<Button>(R.id.continueButton)
        val sp = Audio(this,"en","GB")
        sp.generate_audio("Welcome to BlindVision.Tap on the screen to authenticate.")

        executor=ContextCompat.getMainExecutor(this);
        biometricPrompt= androidx.biometric.BiometricPrompt(this@MainActivity, executor,  object : AuthenticationCallback() {
            override fun onAuthenticationError(
                errorCode: Int,
                errString: CharSequence
            ) {
                super.onAuthenticationError(errorCode, errString)
                Toast.makeText(applicationContext,
                    "Authentication error: $errString", Toast.LENGTH_SHORT)
                    .show();
            }

            override fun onAuthenticationSucceeded(result: androidx.biometric.BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                Toast.makeText(applicationContext, "auth successfull", Toast.LENGTH_SHORT).show()
                sp.generate_audio("Authentication Succeeded, Welcome to App")
                changeScreen();
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                Toast.makeText(applicationContext, "Authentication failed",
                    Toast.LENGTH_SHORT)
                    .show();
            }
        })

        promptInfo = androidx.biometric.BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric login for my app")
            .setSubtitle("Log in using your biometric credential")
            .setNegativeButtonText("Use account password")
            .build();

        button.setOnClickListener {
            biometricPrompt.authenticate(promptInfo);

        }
//        val button2 = findViewById<ImageButton>(R.id.start2)
//        button2.setOnClickListener {
//            val intent = Intent(this, MapsActivity::class.java)
//            startActivity(intent)
//        }
    }


    fun changeScreen(){
        val intent = Intent(this, DetectionActivity::class.java)
        startActivity(intent)
    }
}