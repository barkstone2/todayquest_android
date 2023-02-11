package com.todayquest.data.login

import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.todayquest.common.CommonService
import com.todayquest.data.AsyncResult
import com.todayquest.data.TokenRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val loginLocalDataSource: LoginLocalDataSource,
    private val googleSignInClient: GoogleSignInClient,
) {
    private val loginRemoteDataSource: LoginRemoteDataSource = CommonService.getInstance()

    suspend fun logout(): AsyncResult<Nothing> {
        return withContext(Dispatchers.IO) {
            googleSignInClient.signOut()
            loginLocalDataSource.deletePrincipal()
            AsyncResult.Success()
        }
    }

    suspend fun requestLogin(tokenRequest: TokenRequest): AsyncResult<Nothing> {

        return try {
                val execute = loginRemoteDataSource.login(tokenRequest)
                execute.isSuccessful
                if (execute.code() == 200) {
                    val body = execute.body()
                    loginLocalDataSource.savePrincipal(body!!)
                    AsyncResult.Success()
                } else {
                    AsyncResult.Error(execute.errorBody()?.string() ?: "")
                }
            } catch (e: Exception) {
                AsyncResult.Error("로그인에 실패했습니다.")
            }
    }

    suspend fun silentSignIn(): AsyncResult<Nothing> {
        return withContext(Dispatchers.IO) {
            if (googleSignInClient.silentSignIn().isSuccessful && loginLocalDataSource.existPrincipal()) {
                AsyncResult.Success()
            } else {
                AsyncResult.Error("")
            }
        }
    }

}
