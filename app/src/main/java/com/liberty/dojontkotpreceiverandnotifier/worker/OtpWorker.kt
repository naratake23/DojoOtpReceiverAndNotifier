package com.liberty.dojontkotpreceiverandnotifier.worker

import android.Manifest
import android.content.Context
import android.util.Log
import androidx.annotation.RequiresPermission
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.liberty.dojontkotpreceiverandnotifier.core.WorkerKeys
import com.liberty.dojontkotpreceiverandnotifier.core.createOtpNotification
import com.liberty.dojontkotpreceiverandnotifier.data.SmsCodeRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class OtpWorker @AssistedInject constructor(
    @Assisted context: Context,               // Provided by WorkManager at runtime
    @Assisted params: WorkerParameters,       // Provided by WorkManager at runtime
    private val repo: SmsCodeRepository       // Injected by Hilt
) : CoroutineWorker(context, params) {        // Coroutine-friendly Worker base class

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override suspend fun doWork(): Result {
        Log.d("xxt", "OtpWorker- 1")

        // Read input sent by the scheduler (OtpWorkScheduler.enqueue).
        val code = inputData.getString(WorkerKeys.KEY_CODE) ?: return Result.failure()

        // 1) Persist the code to DataStore (suspending call).
        repo.saveCode(code)

        // 2) Show a user notification containing the code.
        createOtpNotification(applicationContext, code)

        Log.d("xxt", "OtpWorker- 2")

        // Indicate the work finished successfully.
        return Result.success()
    }
}