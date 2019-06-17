import android.accounts.Account
import android.accounts.AccountManager
import android.util.Log
import com.mdmobile.cyclops.CyclopsApplication.Companion.applicationContext
import com.mdmobile.cyclops.R
import com.mdmobile.cyclops.util.Logger

private val LOG_TAG = AccountManager::class.java.simpleName

fun AccountManager.getMcAccount(accountManager: AccountManager = AccountManager
        .get(applicationContext)): Account {
    //You are only allowed to 1 account per MDM for now
    return accountManager
            .getAccountsByType(applicationContext.getString(R.string.MC_account_type))[0]
}

fun AccountManager.checkUserNameExists(accountManager: AccountManager = AccountManager
        .get(applicationContext), userName: String): Boolean {
    Logger.log(LOG_TAG, "Checking any users exists", Log.VERBOSE)
    val accounts = accountManager
            .getAccountsByType(applicationContext.getString(R.string.MC_account_type))

    if (accounts.isEmpty()) {
        Logger.log(LOG_TAG, "User: '$userName' does not exists, the account list is empty! \n", Log.INFO)
        return false
    }
    accounts.iterator().forEach {
        if (it.name == userName) {
            Logger.log(LOG_TAG, "User Found\nUser Name:" + it.name + " Account type: " + it.type, Log.INFO)
            return true
        }
    }
    Logger.log(LOG_TAG, "User: '$userName' does not exists \n", Log.INFO)
    return false
}