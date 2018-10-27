package com.mdmobile.cyclops.dataTypes

import androidx.annotation.StringDef

/**
 * Constants to define possible api actions
 */
class ApiActions {
    @StringDef(CHECKIN, WIPE, LOCK, UNENROL, SEND_MESSAGE, LOCATE, SEND_SCRIPT, ALLOW_EXCHANGE_ACCESS, ALLOW_SOTI_SURF,
            APPLE_SOFTWARE_UPDATE_REFRESH_STATUS, APPLE_SOFTWARE_UPDATE_SCAN, APPLE_SOFTWARE_UPDATE_SCHEDULE, BLOCK_EXCHANGE_ACCESS)
    @kotlin.annotation.Retention(kotlin.annotation.AnnotationRetention.SOURCE)
    annotation class ApiActions

    companion object {
        const val CHECKIN = "Checkin"
        const val WIPE = "Wipe"
        const val LOCK = "Lock"
        const val UNENROL = "Unenroll"
        const val SEND_MESSAGE = "SendMessage"
        const val LOCATE = "Locate"
        const val SEND_SCRIPT = "SendScript"
        const val ALLOW_EXCHANGE_ACCESS = "AllowExchangeAccess"
        const val ALLOW_SOTI_SURF = "AllowSotiSurf"
        const val APPLE_SOFTWARE_UPDATE_REFRESH_STATUS = "AppleSoftwareUpdateRefreshStatus"
        const val APPLE_SOFTWARE_UPDATE_SCAN = "AppleSoftwareUpdateScan"
        const val APPLE_SOFTWARE_UPDATE_SCHEDULE = "AppleSoftwareUpdateSchedule"
        const val BLOCK_EXCHANGE_ACCESS = "BlockExchangeAccess"
        const val BLOCK_SOTI_HUB = "BlockSotiHub"
        const val BLOCK_SOTI_SURF = "BlockSotiSurf"
        const val BYPASS_ACTIVATION_LOCK = "BypassActivationLock"
        const val CLEAR_RESTRICTION = "ClearRestrictions"
        const val CLEAR_SOTI_SURF_CACHE = "ClearSotiSurfCache"
        const val DISABLE_AGENT_UPGRADE = "DisableAgentUpgrade"
        const val DISABLE_LOST_MODE = "DisableLostMode"
        const val DISABLE_PASSLOCK_CODE = "DisablePasscodeLock"
        const val ENABLE_AGENT_UPGRADE = "EnableAgentUpgrade"
        const val ENABLE_LOST_MODE = "EnableLostMode"
        const val FACTORY_RESET = "FactoryReset"
        const val MIMiGRATE_TO_ELM_AGENT = "grateToELMAgent"
        const val RESET_PASSCODE = "ResetPasscode"
        const val REMOTE_RING = "RemoteRing"
        const val SCAN_FOR_VIRUS = "ScanForViruses"
        const val FILE_SYNC_FILE_NOW = "SyncFilesNow"
        const val SOFT_RESET = "SoftReset"
        const val SEND_SCRIPT_VIA_SMS = "SendScriptViaSms"
        const val SEND_TEST_PAGE = "SendTestPage"
        const val TURN_OFF_SUSPEND = "TurnOffSuspend"
        const val UPDATE_VIRUS_DEFINITION = "UpdateVirusDefinition"
        const val UPGRADE_AGENT_NOW = "UpgradeAgentNow"
        const val ENROLL_IN_EFOTA = "EnrollInEFOTA"
        const val UPGRADE_FIRMWARE = "UpgradeFirmware"
    }
}


