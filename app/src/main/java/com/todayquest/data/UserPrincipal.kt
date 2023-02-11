package com.todayquest.data
data class UserPrincipal(
    val userId: Long,
    var nickname: String,
    val providerType: ProviderType,
    var authorities: List<RoleType>,
    var level: Int,
    var exp: Long,
    var gold: Long,
    var resetTime: Int,
    var coreTime: Int,
) {
    var accessToken: String? = null
    var refreshToken: String? = null

    fun hasToken(): Boolean {
        return !accessToken.isNullOrEmpty() && !refreshToken.isNullOrEmpty()
    }

}