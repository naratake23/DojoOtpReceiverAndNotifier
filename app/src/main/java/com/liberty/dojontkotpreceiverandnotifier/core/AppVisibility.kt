package com.liberty.dojontkotpreceiverandnotifier.core

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import java.util.concurrent.atomic.AtomicBoolean

// DefaultLifecycleObserver watches lifecycle events.
object AppVisibility: DefaultLifecycleObserver {

    // AtomicBoolean: thread-safe boolean. Reads/writes are atomic (no race conditions).
    // Used here so multiple threads (like main thread vs. background receiver/worker)
    // can safely check/update whether the app is in foreground.
    // AtomicBoolean = the operation (set(true) or get()) is guaranteed to be done in one indivisible step
private val _isForeground = AtomicBoolean(false)
    val isForeground: Boolean get() = _isForeground.get()

    override fun onStart(owner: LifecycleOwner) {
        // Called when the app (via ProcessLifecycleOwner) moves to foreground.
        _isForeground.set(true)
    }

    override fun onStop(owner: LifecycleOwner) {
        // Called when the app goes into background.
        _isForeground.set(false)
    }

    fun init() {
        // Hook this observer into the ProcessLifecycleOwner (the lifecycle
        // of the whole app process). After calling init(), onStart/onStop
        // will be triggered whenever the app moves fg/bg.
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

}