package com.mdmobile.cyclops.dataTypes

import androidx.annotation.StringDef
import com.mdmobile.cyclops.dataTypes.ProfileActions.Companion.INSTALL
import com.mdmobile.cyclops.dataTypes.ProfileActions.Companion.REVOKE

/**
 * Constants to define possible api actions
 */
@StringDef(INSTALL, REVOKE)
@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
annotation class ProfileActions {
    companion object {
        const val INSTALL = "install"
        const val REVOKE = "revoke"
    }
}

