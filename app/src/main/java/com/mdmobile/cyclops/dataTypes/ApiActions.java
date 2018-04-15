package com.mdmobile.cyclops.dataTypes;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.mdmobile.cyclops.dataTypes.ApiActions.CHECKIN;
import static com.mdmobile.cyclops.dataTypes.ApiActions.LOCATE;
import static com.mdmobile.cyclops.dataTypes.ApiActions.LOCK;
import static com.mdmobile.cyclops.dataTypes.ApiActions.SEND_MESSAGE;
import static com.mdmobile.cyclops.dataTypes.ApiActions.SEND_SCRIPT;
import static com.mdmobile.cyclops.dataTypes.ApiActions.UNENROL;
import static com.mdmobile.cyclops.dataTypes.ApiActions.WIPE;

/**
 * Constants to define possible api actions
 */
@SuppressWarnings("unused")
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
    String ALLOW_EXCHANGE_ACCESS = "AllowExchangeAccess";
    String ALLOW_SOTI_SURF = "AllowSotiSurf";
    String APPLE_SOFTWARE_UPDATE_REFRESH_STATUS = "AppleSoftwareUpdateRefreshStatus";
    String APPLE_SOFTWARE_UPDATE_SCAN = "AppleSoftwareUpdateScan";
    String APPLE_SOFTWARE_UPDATE_SCHEDULE = "AppleSoftwareUpdateSchedule";
    String BLOCK_EXCHANGE_ACCESS = "BlockExchangeAccess";
    String BLOCK_SOTI_HUB = "BlockSotiHub";
    String BLOCK_SOTI_SURF = "BlockSotiSurf";
    String BYPASS_ACTIVATION_LOCK = "BypassActivationLock";
    String CLEAR_RESTRICTION = "ClearRestrictions";
    String CLEAR_SOTI_SURF_CACHE = "ClearSotiSurfCache";
    String DISABLE_AGENT_UPGRADE = "DisableAgentUpgrade";
    String DISABLE_LOST_MODE = "DisableLostMode";
    String DISABLE_PASSLOCK_CODE = "DisablePasscodeLock";
    String ENABLE_AGENT_UPGRADE = "EnableAgentUpgrade";
    String ENABLE_LOST_MODE = "EnableLostMode";
    String FACTORY_RESET = "FactoryReset";
    String MIMiGRATE_TO_ELM_AGENT = "grateToELMAgent";
    String RESET_PASSCODE = "ResetPasscode";
    String REMOTE_RING = "RemoteRing";
    String SCAN_FOR_VIRUS = "ScanForViruses";
    String FILE_SYNC_FILE_NOW = "SyncFilesNow";
    String SOFT_RESET = "SoftReset";
    String SEND_SCRIPT_VIA_SMS = "SendScriptViaSms";
    String SEND_TEST_PAGE = "SendTestPage";
    String TURN_OFF_SUSPEND = "TurnOffSuspend";
    String UPDATE_VIRUS_DEFINITION = "UpdateVirusDefinition";
    String UPGRADE_AGENT_NOW = "UpgradeAgentNow";
    String ENROLL_IN_EFOTA = "EnrollInEFOTA";
    String UPGRADE_FIRMWARE = "UpgradeFirmware";

}

