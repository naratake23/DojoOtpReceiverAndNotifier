package com.liberty.dojontkotpreceiverandnotifier.core

import android.util.Log
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.liberty.dojontkotpreceiverandnotifier.worker.OtpWorker

class OtpWorkScheduler (
    private val workManager: WorkManager
) {
    fun enqueue(code: String) {

        // Build a one-time WorkManager request to run OtpWorker.
        val req = OneTimeWorkRequestBuilder<OtpWorker>()
            // Pass OTP code as input data (key-value pairs).
            // Inside OtpWorker, it's possible to retrieve it with inputData.getString(KEY_CODE).
            .setInputData(workDataOf(WorkerKeys.KEY_CODE to code))
            // Optional tag "otp" to later query/cancel jobs by this tag.
            .addTag("otp")
            .build()

        Log.d("xxt", "OtpWorkScheduler 1")

        workManager.enqueue(req)
        // Actually hands the request to WorkManager.
        // WorkManager schedules it immediately (or when constraints allow).

        Log.d("xxt", "OtpWorkScheduler 2")

    }
}