package com.mdmobile.cyclops.ui

import java.util.*

class FragmentObserver : Observable() {
    override fun notifyObservers(arg: Any?) {
        setChanged()
        super.notifyObservers(arg)
    }
}