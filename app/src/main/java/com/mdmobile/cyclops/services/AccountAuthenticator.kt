package com.mdmobile.cyclops.services


import android.accounts.AbstractAccountAuthenticator
import android.accounts.Account
import android.accounts.AccountAuthenticatorResponse
import android.accounts.AccountManager
import android.content.Context
import android.content.Intent
import android.os.Bundle

import android.util.Log

import com.android.volley.VolleyError
import com.mdmobile.cyclops.api.ApiRequestManager
import com.mdmobile.cyclops.dataModel.Instance
import com.mdmobile.cyclops.dataModel.api.Token
import com.mdmobile.cyclops.interfaces.NetworkCallBack
import com.mdmobile.cyclops.security.ServerNotFound
import com.mdmobile.cyclops.ui.logIn.LoginActivity
import com.mdmobile.cyclops.util.Logger
import com.mdmobile.cyclops.util.ServerUtility
import com.mdmobile.cyclops.util.UserUtility

import javax.net.ssl.HttpsURLConnection

class AccountAuthenticator internal constructor(context: Context) : AbstractAccountAuthenticator(context) {
    private val mContext: Context = context.applicationContext
    private val logTag = AccountAuthenticator::class.java.simpleName


    override fun editProperties(accountAuthenticatorResponse: AccountAuthenticatorResponse, s: String): Bundle {
        throw UnsupportedOperationException("Confirm credentials is not a supported operation")

    }

    override fun addAccount(response: AccountAuthenticatorResponse, accountType: String, authTokenType: String,
                            requiredFeatures: Array<String>, options: Bundle): Bundle {

        return promptError(response, accountType, authTokenType, true)
    }

    override fun confirmCredentials(accountAuthenticatorResponse: AccountAuthenticatorResponse,
                                    account: Account, bundle: Bundle): Bundle {
        throw UnsupportedOperationException("Confirm credentials is not a supported operation")
    }

    override fun getAuthToken(authenticatorResponse: AccountAuthenticatorResponse, account: Account,
                              authTokenType: String, bundle: Bundle): Bundle? {


        val accountManager = AccountManager.get(mContext)
        val userInfo = UserUtility.getUserInfo(account)
        val instanceInfo: Instance
        try {
            instanceInfo = ServerUtility.getActiveServer()
        } catch (e: ServerNotFound) {
            e.printStackTrace()
            return null
        }

        if (userInfo == null) {
            return promptError(authenticatorResponse, account.type, authTokenType, null)
        }

        Logger.log(logTag, "Requesting new _token...", Log.VERBOSE)

        ApiRequestManager.getInstance()
                .getToken(instanceInfo, account.name, accountManager.getPassword(account),
                        object : NetworkCallBack {
                            override fun tokenReceived(userInfo: Bundle, JsonToken: Token) {
                                val result = Bundle()
                                result.putString(AccountManager.KEY_ACCOUNT_NAME, account.name)
                                result.putString(AccountManager.KEY_ACCOUNT_TYPE, account.type)
                                result.putString(AUTH_TOKEN_TYPE_KEY, JsonToken.token_type)
                                result.putString(AccountManager.KEY_AUTHTOKEN, JsonToken.access_token)
                                authenticatorResponse.onResult(result)
                            }

                            override fun errorReceivingToken(errorResponse: VolleyError) {
                                //If we are here with error 400 it only means credentials have changed
                                //Return the error to authenticatorResponse
                                if (errorResponse.networkResponse.statusCode == HttpsURLConnection.HTTP_UNAUTHORIZED || errorResponse.networkResponse.statusCode == HttpsURLConnection.HTTP_BAD_REQUEST) {
                                    val result = promptError(authenticatorResponse, account.type, authTokenType, null)
                                    authenticatorResponse.onResult(result)
                                }
                            }
                        })
        return null
    }

    override fun getAuthTokenLabel(s: String): String? {
        return null
    }

    override fun updateCredentials(accountAuthenticatorResponse: AccountAuthenticatorResponse,
                                   account: Account, s: String, bundle: Bundle): Bundle? {
        return null
    }

    override fun hasFeatures(accountAuthenticatorResponse: AccountAuthenticatorResponse,
                             account: Account, strings: Array<String>): Bundle? {
        return null
    }


    //Utility method to return a bundle with KEY_INTENT key and the intent to show login activity
    private fun promptError(response: AccountAuthenticatorResponse, accountType: String,
                            authTokenType: String, addingNewAccount: Boolean?): Bundle {
        val intent = Intent(mContext, LoginActivity::class.java)

        //Required for login activity to create a new account
        intent.putExtra(ACCOUNT_TYPE_KEY, accountType)
        intent.putExtra(AUTH_TOKEN_TYPE_KEY, authTokenType)

        if (addingNewAccount != null) {
            intent.putExtra(ADDING_NEW_ACCOUNT_KEY, addingNewAccount)
        }
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response)
        val bundle = Bundle()
        bundle.putParcelable(AccountManager.KEY_INTENT, intent)
        return bundle
    }

    companion object {
        const val REFRESH_AUTH_TOKEN_KEY = "RefreshAuthTokenKey"
        const val ADDING_NEW_ACCOUNT_KEY = "AddingNewAccountIntentKey"
        const val ACCOUNT_TYPE_KEY = "AccountTypeKey"
        const val AUTH_TOKEN_TYPE_KEY = "AuthTokenTypeKey"
        const val AUTH_TOKEN_EXPIRATION_KEY = "AuthTokenExpirationKey"
    }
}
