package com.liberty.dojontkotpreceiverandnotifier.core

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


class OtpBus {
    // Acts like a tiny in-app event bus:
    // Foreground path: SmsReceiver calls OtpBus.updateOtp(code).
    // ViewModel is collecting otpFlow > instantly shows the code.
    private val _otpFlow = MutableStateFlow("")
    val otpFlow = _otpFlow.asStateFlow()
    fun updateOtp(code: String){_otpFlow.value = code}
}

