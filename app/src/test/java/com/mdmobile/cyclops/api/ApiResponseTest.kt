package com.mdmobile.cyclops.api

import org.hamcrest.core.Is.`is`
import org.junit.Assert.assertThat
import org.junit.Test
import retrofit2.Response
import java.lang.Exception

class ApiResponseTest {
    @Test
    fun success() {
        val apiResponse: ApiSuccessResponse<String> = ApiResponse.create(Response.success("Foo")) as ApiSuccessResponse<String>
        assertThat(apiResponse.body, `is`("Foo"))
    }

    @Test
    fun error() {
        val error = Exception("Not authorized")
        val apiResponse: ApiErrorResponse<String> = ApiResponse.create(error)
        assertThat<String>(apiResponse.errorMessage, `is`("Not authorized"))
    }
}