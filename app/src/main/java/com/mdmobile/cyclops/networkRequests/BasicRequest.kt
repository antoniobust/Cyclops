package com.mdmobile.cyclops.networkRequests


import android.accounts.AccountManager
import android.icu.lang.UCharacter.GraphemeClusterBreak.L
import android.net.Uri
import android.util.Log

import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.HttpHeaderParser
import com.mdmobile.cyclops.security.ServerNotFound
import com.mdmobile.cyclops.util.Logger
import com.mdmobile.cyclops.util.ServerUtility
import com.mdmobile.cyclops.util.UserUtility

import java.io.UnsupportedEncodingException
import java.util.HashMap

import javax.net.ssl.HttpsURLConnection

import com.mdmobile.cyclops.CyclopsApplication.Companion
import com.mdmobile.cyclops.services.AccountAuthenticator
import getMcAccount
import java.nio.charset.Charset

/**
 * Extend this class in Custom volley request.
 * This insert MobiControl authentication _token in the headers
 * And provides standard procedure for a refused request
 */

abstract class BasicRequest<T>(method: Int, url: String, errorListener: Response.ErrorListener)//        setRetryPolicy(new BasicRequestRetry(this));
    : Request<T>(method, url, errorListener) {

     private val  logTag = BasicRequest::class.java.simpleName

    val serverUrl: String?
        get() = Uri.parse(url).authority

    @Throws(AuthFailureError::class)
    override fun getHeaders(): Map<String, String> {
        val headers = HashMap<String, String>()
        val accountManager = AccountManager.get(Companion.applicationContext)

        //TODO: support multiple account
        val account = accountManager.getMcAccount()
        val tokenType = UserUtility.getUserInfo(accountManager.getMcAccount()).getString(AccountAuthenticator.AUTH_TOKEN_TYPE_KEY)

        val token = accountManager.peekAuthToken(account, tokenType)
                ?: //TODO: stop request and launch account manager for a new _token
                //Instead of sending this request out which is gonna fail because of the _token cancel this request
                //and start the new _token request procedure
                return super.getHeaders()

        headers["Authorization"] = "$tokenType $token"
        headers["Accept"] = "application/json"

        return headers
    }

    override fun parseNetworkError(volleyError: VolleyError): VolleyError {

        //TODO:support Multiple account
        val response = volleyError.networkResponse
        if (response == null) {
            Logger.log(logTag, "Network request Error: error response: NULL\n", Log.ERROR)
            return super.parseNetworkError(volleyError)
        }

        try {
            val errorResponse = String(response.data, (HttpHeaderParser.parseCharset(response.headers)).toCharArray() as Charset)
            Logger.log(logTag, errorResponse, Log.ERROR)
            //CHECK ALL ERRORS CODE
            parseErrorCode(response.statusCode)
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        } catch (e: ServerNotFound) {
            e.printStackTrace()
        }

        return super.parseNetworkError(volleyError)
    }

    @Throws(ServerNotFound::class)
    private fun parseErrorCode(errorCode: Int) {

        if (errorCode == HttpsURLConnection.HTTP_BAD_REQUEST) {
            Logger.log(logTag, "Error $errorCode HTTP BAD REQUEST", Log.ERROR)
        } else if (errorCode == HttpsURLConnection.HTTP_UNAUTHORIZED || errorCode == HttpsURLConnection.HTTP_FORBIDDEN) {
            //            Logger.log(logTag, "Attempt to request new _token\n", Log.VERBOSE);
            //
            //            AccountManager manager = AccountManager.get(applicationContext);
            //            Account[] accounts = manager.getAccountsByType(applicationContext.getString(R.string.account_type));
            //
            //            if (accounts.length == 1) {
            //                String tokenType = manager.getUserData(accounts[0], AUTH_TOKEN_TYPE_KEY);
            //                String _token = manager.peekAuthToken(accounts[0], tokenType);
            //                manager.invalidateAuthToken(applicationContext.getString(R.string.account_type), _token);
            //
            //                manager.getAuthToken(accounts[0],
            //                        manager.getUserData(accounts[0], AUTH_TOKEN_TYPE_KEY), null, true,
            //                        new OnTokenResponse(new WeakReference<BasicRequest>(this)), null);
            //            }

        } else if (errorCode == HttpsURLConnection.HTTP_NOT_FOUND) {

            val serverName = ServerUtility.getActiveServer().serverName
            ServerUtility.notifyServerStatus(serverName, ServerUtility.SERVER_OFFLINE)

        } else if (errorCode == 422) {
            //TODO: show error message in toast
        } else if (errorCode == HttpsURLConnection.HTTP_INTERNAL_ERROR) {
        }
    }
}

