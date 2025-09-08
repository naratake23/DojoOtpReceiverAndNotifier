package com.liberty.dojontkotpreceiverandnotifier.ui.otp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.liberty.dojontkotpreceiverandnotifier.ui.RequestNotificationPermissionOnce
import com.liberty.dojontkotpreceiverandnotifier.ui.RequestSmsPermissionOnce
import com.liberty.dojontkotpreceiverandnotifier.ui.RequestStartupPermissions

@Composable
fun OtpScreen() {

    val vm = hiltViewModel<OtpViewModel>()
    val uiState by vm.state.collectAsStateWithLifecycle()

//    RequestSmsPermissionOnce()
//    RequestNotificationPermissionOnce()

    Column(
    modifier = Modifier
    .fillMaxSize()
    .padding(16.dp),
    verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "DojoNtk - OTP Reader",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(Modifier.height(30.dp))

        OutlinedTextField(
            value = uiState.digits,
            onValueChange = { vm.onDigitsChanged(it) },
            label = { Text("Enter code digits") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            textStyle = LocalTextStyle.current.copy(color = Color.Black)


        )
        Spacer(Modifier.height(30.dp))

        Button(
            onClick = { vm.onClearDigits() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Clean all digits")
        }
        Spacer(Modifier.height(30.dp))

        if (uiState.lastCode.isNotEmpty()) {
            Text(
                text = "This is your last code: ${uiState.lastCode}",
                style = MaterialTheme.typography.bodyLarge
            )
        }
        Spacer(Modifier.height(90.dp))

        Button(
            onClick = { vm.onSimulateEnqueue()},
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Simulate OTP (enqueue)")
        }



        Spacer(Modifier.height(60.dp))
        RequestStartupPermissions()

    }
}
