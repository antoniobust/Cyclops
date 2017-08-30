package com.mdmobile.pocketconsole.dataTypes;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.mdmobile.pocketconsole.dataTypes.ApiActions.CHECKIN;
import static com.mdmobile.pocketconsole.dataTypes.ApiActions.LOCATE;
import static com.mdmobile.pocketconsole.dataTypes.ApiActions.LOCK;
import static com.mdmobile.pocketconsole.dataTypes.ApiActions.SEND_MESSAGE;
import static com.mdmobile.pocketconsole.dataTypes.ApiActions.SEND_SCRIPT;
import static com.mdmobile.pocketconsole.dataTypes.ApiActions.UNENROL;
import static com.mdmobile.pocketconsole.dataTypes.ApiActions.WIPE;

/**
 * Constants to define possible api actions
 */


@StringDef({CHECKIN, WIPE, LOCK, UNENROL, SEND_MESSAGE, LOCATE, SEND_SCRIPT})
@Retention(RetentionPolicy.SOURCE)

public @interface ApiActions {
    String CHECKIN = "Checkin";
    String WIPE = "Wipe";
    String LOCK = "Lock";
    String UNENROL = "Unenroll";
    String SEND_MESSAGE = "SendMessage";
    String LOCATE = "Locate";
    String SEND_SCRIPT = "SendScript";
}

