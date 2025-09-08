package com.liberty.dojontkotpreceiverandnotifier.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.liberty.dojontkotpreceiverandnotifier.ui.otp.OtpScreen
import com.liberty.dojontkotpreceiverandnotifier.ui.theme.DojoNtkOtpReceiverAndNotifierTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Jetpack Compose helper function (from androidx.activity 1.7.0+).
        // It configures the window so the UI can draw *edge-to-edge*:
        //   - makes status bar + navigation bar transparent
        //   - handles insets (to draw behind system bars)
        //   - allows create immersive, modern layouts
        // Basically it removes the old "black bars" look and lets the content flow under the system bars
        enableEdgeToEdge()
        setContent {
            DojoNtkOtpReceiverAndNotifierTheme {
                OtpScreen()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DojoNtkOtpReceiverAndNotifierTheme {
        OtpScreen()
    }
}