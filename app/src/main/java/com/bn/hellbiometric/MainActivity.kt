package com.bn.hellbiometric

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.*
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity(),BiometricAuthListener  {

    private lateinit var buttonBiometricsLogin:AppCompatButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buttonBiometricsLogin = findViewById<AppCompatButton>(R.id.btnLogin)
        buttonBiometricsLogin.setOnClickListener {
            when(hasBiometricCapability(this)){
                BIOMETRIC_SUCCESS->{
                    //The hardware is available and the user has enrolled their biometric data, so the device is ready to use a biometric prompt.
                    BiometricUtil.showBiometricPrompt(
                        activity = this,
                        listener = this,
                        cryptoObject = null,
                        allowDeviceCredential = true
                    )
                }
                BIOMETRIC_ERROR_NONE_ENROLLED->{
                    //The device has biometric capabilities, but the user has yet to enroll their fingerprints or face.
                    Toast.makeText(this@MainActivity,"user has yet to enroll their fingerprints or face.",Toast.LENGTH_LONG).show()

                }
                BIOMETRIC_ERROR_NO_HARDWARE->{
                    //The device’s hardware does not support biometric authentication.
                    Toast.makeText(this@MainActivity,"Device Havn't Biometric",Toast.LENGTH_LONG).show()
                }
            }

        }
    }

    fun hasBiometricCapability(context: Context): Int {
        val biometricManager = BiometricManager.from(context)
        return biometricManager.canAuthenticate()
    }


    override fun onBiometricAuthenticationSuccess(result: BiometricPrompt.AuthenticationResult) {
        val intent = Intent(this,SecuredActivity::class.java)
        startActivity(intent)
    }

    override fun onBiometricAuthenticationError(errorCode: Int, errorMessage: String) {
        Toast.makeText(this, "Biometric login. Error: $errorMessage", Toast.LENGTH_SHORT)
            .show()
    }

}