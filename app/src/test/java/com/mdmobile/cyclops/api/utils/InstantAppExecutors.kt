package com.mdmobile.cyclops.api.utils

import com.mdmobile.cyclops.ApplicationExecutors
import java.util.concurrent.Executor

class InstantAppExecutors : ApplicationExecutors(instant, instant, instant) {

    companion object {
        private val instant = Executor {
            it.run()
        }
    }
}