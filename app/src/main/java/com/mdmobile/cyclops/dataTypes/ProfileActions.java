package com.mdmobile.cyclops.dataTypes;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.StringDef;

import static com.mdmobile.cyclops.dataTypes.ProfileActions.INSTALL;
import static com.mdmobile.cyclops.dataTypes.ProfileActions.REVOKE;

/**
 * Constants to define possible api actions
 */
@SuppressWarnings("unused")
@StringDef({INSTALL, REVOKE})
@Retention(RetentionPolicy.SOURCE)
public @interface ProfileActions {
    String INSTALL = "install";
    String REVOKE = "revoke";
}

