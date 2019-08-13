package mobicontrol.mcApiService.api


import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

/**
 * Authenticator class for okHttp.
 * If 401 is returned for a bearer request then try to get a new token with stored credentials.
 * Request limit is 2.
 * If requesting a new token with same credential returns 401 then current credentials are no longer valid
 * Authenticator only checks "Bearer" requests as "basic" ones are token requests
 */


class TokenAuthenticator : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

//    private val retryHeaderName = "RetryCountHeader"
//    override fun authenticate(route: Route?, response: Response): Request? {
//        (TokenAuthenticator::class.java.simpleName,
//                "Authentication error: ${response.code()} request: ${response.request().url()}", Log.VERBOSE)
//        when (isBearerRequest(response)) {
//            false -> {
//                Logger.log(TokenAuthenticator::class.java.simpleName,
//                "Token request couldn't be retrieved, current credentials are no longer valid, update credentials ", Log.ERROR)
//                return null
//            }
//            true -> {
//                val count = retryCount(response)
//                val tokenResponse = authenticateReq(response.request(), count + 1)
//                tokenResponse.let {
//                    return null
//                    if (it is ApiSuccessResponse) {
//                        Logger.log(TokenAuthenticator::class.java.simpleName,
//                                "Got new token -> re-attempting request: (${response.request().url()})", Log.VERBOSE)
//                        tokenRepository
//                        rewriteRequest(response.request(), count, it.body.token)
//                        null
//                    } else {
//                        Logger.log(TokenAuthenticator::class.java.simpleName,
//                                "Token request couldn't be retrieved, current credentials are no longer valid, update credentials ", Log.ERROR)
//                        null
//                    }
//                }
//            }
//        }
//    }
//
//    private fun isBearerRequest(response: Response): Boolean {
//        val authorization = response.request().header("Authorization")
//        if (authorization != null) {
//            return authorization.startsWith("Bearer: ", true)
//        }
//        return false
//    }
//
//    private fun retryCount(response: Response): Int = response.header(retryHeaderName)?.toInt() ?: 0
//
//     Synced method so if multiple request fail dont refresh the same token
//    @Synchronized
//    private fun authenticateReq(oldReq: Request, retryCount: Int)
//            : ApiResponse<Token>?
//    {
//        if (retryCount > 1) {
//            Logger.log(TokenAuthenticator::class.java.simpleName,
//                    "Failed to execute  ${oldReq.url()} - retry:($retryCount) - user doesn't have access to the" +
//                            "specified resource", Log.ERROR)
//            return null
//        }
//
//        Logger.log(TokenAuthenticator::class.java.simpleName,
//                "Attempting a token refresh with current credentials - retry:($retryCount)", Log.VERBOSE)
//        return tokenRepository.refreshToken()
//    }
//
//    private fun rewriteRequest(oldRequest: Request, retryCount: Int, authToken: String?): Request? {
//        return oldRequest.newBuilder().header("Authorization", "bearer: $authToken")
//                .header(retryHeaderName, "$retryCount")
//                .build()
//    }

}
