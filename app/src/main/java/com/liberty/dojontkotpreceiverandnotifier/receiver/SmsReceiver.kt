package com.liberty.dojontkotpreceiverandnotifier.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsMessage
import android.util.Log
import com.liberty.dojontkotpreceiverandnotifier.core.AppVisibility
import com.liberty.dojontkotpreceiverandnotifier.core.OtpBus
import com.liberty.dojontkotpreceiverandnotifier.core.OtpWorkScheduler
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

//manifest-registered receivers are instantiated by the system using a no-arg constructor.
// Hilt cannot do constructor injection here, so we use field injection via @AndroidEntryPoint.
@AndroidEntryPoint
class SmsReceiver : BroadcastReceiver() {

    @Inject lateinit var otpBus: OtpBus // Foreground UI event bus (in-memory)
    @Inject lateinit var scheduler: OtpWorkScheduler // Background path via WorkManager

    override fun onReceive(context: Context, intent: Intent) {
        Log.d("xxt", "SmsReceiver-")

        // Guard: only handle the SMS_RECEIVED broadcast.
        if (intent.action != "android.provider.Telephony.SMS_RECEIVED") return
        // (Alternatively: if (intent.action != Telephony.Sms.Intents.SMS_RECEIVED_ACTION) return)

        // Pull PDUs (protocol data units) + format from the broadcast extras.
        val bundle = intent.extras ?: return
        val protocolDataUnits = bundle["pdus"] as? Array<*> ?: return
        val format = bundle.getString("format")

        // Loop over each PDU (each PDU is one message segment in a multipart SMS).
        for (pdu in protocolDataUnits) {
            // On API 23+, use the 2-arg createFromPdu with format.
            val msg = SmsMessage.createFromPdu(pdu as ByteArray, format)
            val body = msg.messageBody.orEmpty()
            Log.d("xxt", "SmsReceiver- SMS BODY: $body")

            // Only handle OTP messages from your service / Filter by the pattern:
            //if (!body.contains("DojoNtk - OtpReader")) continue

            // Regex(...) → creates a Kotlin regular expression object.
            // \\b → word boundary (Only match if this looks like a standalone number chunk).
            // \\d → digit (0–9).
            // {4,6} → quantifier: match between 4 and 6 digits in a row.
            // () → capture group, so you can extract the digits later with groupValues[1].
            val regex = Regex("\\b(\\d{4,6})\\b")
            // Try to find the first substring in the SMS body that matches the regex
            val match = regex.find(body)
            // If match is not null, take the first captured group (the digits);
            // if no match, skip this iteration (continue to next SMS segment)
            val code = match?.groupValues?.get(1) ?: continue
            Log.d("xxt", "SmsReceiver- Extracted OTP: $code")

            if (AppVisibility.isForeground) {
                Log.d("xxt", "SmsReceiver- FOREGROUND")
                // App visible > push the code to the UI immediately.
                otpBus.updateOtp(code)
            } else {
                Log.d("xxt", "SmsReceiver- BACKGROUND")
                // App not visible > schedule background work to persist + notify.
                scheduler.enqueue(code)
            }

        }
    }
}
