package com.mdmobile.cyclops.interfaces

import androidx.annotation.IntDef
import java.lang.annotation.ElementType.METHOD

class InstanceVersion {

    @IntDef(VERSION_13, VERSION_14)
    @Retention(AnnotationRetention.RUNTIME)
    @Target(AnnotationTarget.FUNCTION)
    annotation class InstanceVersion(val version:Int)

    companion object {
        const val VERSION_14 = 14
        const val VERSION_13 = 13
    }
}
