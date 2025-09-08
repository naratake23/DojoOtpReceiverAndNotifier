# OTP Receiver & Notifier

A compact OTP (one-time password) receiver and notifier built with **Jetpack Compose**, **Hilt**, **WorkManager**, and **DataStore**.

The app listens for incoming SMS, extracts the OTP, and then either:

- **Foreground:** updates the UI immediately  
- **Background:** schedules background work that saves the code and pushes a notification

---

## Core Flow

```
Incoming SMS
   ↓ (Android system)
BroadcastReceiver (SmsReceiver)
   ↓ checks if app is visible (ProcessLifecycleOwner via AppVisibility)
   ├─ If FOREGROUND: OtpBus.updateOtp(code) ─→ ViewModel observes → UI shows code
   └─ If BACKGROUND: OtpWorkScheduler.enqueue(code)
                         ↓
                    WorkManager job (OtpWorker, Hilt-injected)
                         ↓
                 SmsCodeRepository.save(code) [DataStore]
                         ↓
                createOtpNotification(...) → tap opens MainActivity
                         ↓
                    UI reads lastCode from DataStore & displays it
```

---

## What It Covers

- **BroadcastReceiver** for `SMS_RECEIVED` (manifest-registered)  
- **ProcessLifecycleOwner wrapper** (`AppVisibility`) to know foreground/background state  
- **WorkManager** with `HiltWorkerFactory` (via `Configuration.Provider` in `App`)  
- **Dependency Injection (Hilt):**  
  - Provides `DataStore<Preferences>`  
  - Provides `WorkManager`  
  - Provides a simple `OtpBus` event bridge  
  - Binds `SmsCodeRepository` → `SmsCodeRepositoryImpl`  
- **Data persistence** with DataStore (`PreferenceKeys.LAST_CODE`)  
- **Notifications:**  
  - Channel created at app start  
  - High-priority OTP alert  
  - `PendingIntent` launches `MainActivity`  
- **Runtime permissions:**  
  - `RECEIVE_SMS`  
  - `POST_NOTIFICATIONS` (Android 13+)  
- **Jetpack Compose UI** with a single `OtpScreen` + `OtpViewModel` (using `StateFlow`)  
