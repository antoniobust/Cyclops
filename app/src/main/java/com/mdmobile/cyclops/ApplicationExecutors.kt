package com.mdmobile.cyclops

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * Application executors pool.
 * Defines executors for Network, disk and UI activity
 */

class ApplicationExecutors private constructor(
        private val applicationTread: Executor,
        private val networkIO: Executor,
        private val diskIO: Executor) {

    private val singleton by lazy{
        ApplicationExecutors()
    }

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