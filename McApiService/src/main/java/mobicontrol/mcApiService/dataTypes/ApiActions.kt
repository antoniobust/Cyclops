package mobicontrol.mcApiService.dataTypes

/**
 * Constants to define possible api actions
 */
enum class ApiActions(action: String) {
    CHECKIN("Checkin"), WIPE("Wipe"), LOCK("Lock"), UNENROL("Unenroll"), SEND_MESSAGE("SendMessage"),
    LOCATE("Locate"), SEND_SCRIPT("SendScript"), ALLOW_EXCHANGE_ACCESS("AllowExchangeAccess"),
    ALLOW_SOTI_SURF("AllowSotiSurf"), APPLE_SOFTWARE_UPDATE_REFRESH_STATUS("AppleSoftwareUpdateRefreshStatus"),
    APPLE_SOFTWARE_UPDATE_SCAN("AppleSoftwareUpdateScan"), APPLE_SOFTWARE_UPDATE_SCHEDULE("AppleSoftwareUpdateSchedule"),
    BLOCK_EXCHANGE_ACCESS("BlockExchangeAccess")
}


//        const val BLOCK_SOTI_HUB = "BlockSotiHub"
//        const val BLOCK_SOTI_SURF = "BlockSotiSurf"
//        const val BYPASS_ACTIVATION_LOCK = "BypassActivationLock"
//        const val CLEAR_RESTRICTION = "ClearRestrictions"
//        const val CLEAR_SOTI_SURF_CACHE = "ClearSotiSurfCache"
//        const val DISABLE_AGENT_UPGRADE = "DisableAgentUpgrade"
//        const val DISABLE_LOST_MODE = "DisableLostMode"
//        const val DISABLE_PASSLOCK_CODE = "DisablePasscodeLock"
//        const val ENABLE_AGENT_UPGRADE = "EnableAgentUpgrade"
//        const val ENABLE_LOST_MODE = "EnableLostMode"
//        const val FACTORY_RESET = "FactoryReset"
//        const val MIMiGRATE_TO_ELM_AGENT = "grateToELMAgent"
//        const val RESET_PASSCODE = "ResetPasscode"
//        const val REMOTE_RING = "RemoteRing"
//        const val SCAN_FOR_VIRUS = "ScanForViruses"
//        const val FILE_SYNC_FILE_NOW = "SyncFilesNow"
//        const val SOFT_RESET = "SoftReset"
//        const val SEND_SCRIPT_VIA_SMS = "SendScriptViaSms"
//        const val SEND_TEST_PAGE = "SendTestPage"
//        const val TURN_OFF_SUSPEND = "TurnOffSuspend"
//        const val UPDATE_VIRUS_DEFINITION = "UpdateVirusDefinition"
//        const val UPGRADE_AGENT_NOW = "UpgradeAgentNow"
//        const val ENROLL_IN_EFOTA = "EnrollInEFOTA"
//        const val UPGRADE_FIRMWARE = "UpgradeFirmware"
//    }
//}


