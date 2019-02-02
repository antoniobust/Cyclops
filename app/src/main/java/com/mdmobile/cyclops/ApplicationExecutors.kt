package com.mdmobile.cyclops

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Inject

/**
 * Application executors pool.
 * Defines executors for Network, disk and UI activity
 */

open class ApplicationExecutors(
        val applicationTread: Executor,
        val networkIO: Executor,
        val diskIO: Executor) {

    private val singleton by lazy {
        ApplicationExecutors()
    }

    @Inject
    constructor() : this(
            Executors.newSingleThreadExecutor(),
            Executors.newFixedThreadPool(3),
            MainThreadExecutor()
    )

    private class MainThreadExecutor : Executor {
        private val mainThreadHandler = Handler(Looper.getMainLooper())

        override fun execute(command: Runnable?) {
            mainThreadHandler.post(command)
        }

    }
}