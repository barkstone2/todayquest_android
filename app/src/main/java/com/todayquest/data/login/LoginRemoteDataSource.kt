package com.todayquest.data.login

import com.todayquest.common.CommonService
import com.todayquest.data.TokenRequest
import com.todayquest.data.UserPrincipal
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginRemoteDataSource: CommonService {
    @POST("/auth/issue")
    suspend fun login(@Body tokenRequest: TokenRequest): Response<UserPrincipal>
}