package com.liberty.dojontkotpreceiverandnotifier.ui.otp

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.liberty.dojontkotpreceiverandnotifier.core.OtpBus
import com.liberty.dojontkotpreceiverandnotifier.core.OtpWorkScheduler
import com.liberty.dojontkotpreceiverandnotifier.data.SmsCodeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OtpViewModel @Inject constructor(
    private val otpBus: OtpBus,           // live events when app is foreground
    private val repo: SmsCodeRepository,   // persisted last code from DataStore

    private val scheduler: OtpWorkScheduler
) : ViewModel() {

    private val _state = MutableStateFlow(OtpUiState())
    val state = _state.asStateFlow()

    init {
        // 1) Live SMS > fill the digits box
        viewModelScope.launch {
            otpBus.otpFlow
                .filter { it.isNotBlank() }
                .distinctUntilChanged() //suppress consecutive duplicates from a Flow. A,A,B,B,B,C, the collector sees A,B,C
                .collect { code ->
                    _state.update { it.copy(digits = code) }
                }
        }

        // 2) Persisted value > show under the field
        viewModelScope.launch {
            repo.lastCodeFlow
                .distinctUntilChanged()
                .collect { last ->
                    _state.update { it.copy(lastCode = last) }
                }
        }
    }

    fun onDigitsChanged(newDigits: String) {
        _state.value = _state.value.copy(digits = newDigits)
    }

    fun onClearDigits() {
        _state.value = _state.value.copy(digits = "")
    }


    fun onSimulateEnqueue() {
        Log.d("xxt", "VM")
        val code = state.value.digits.ifBlank { (100000..999999).random().toString() }
        scheduler.enqueue(code) // enqueue WorkManager from UI
    }
}