package com.todayquest.data

data class TokenRequest(
    var idToken: String,
    var providerType: ProviderType,
    var accessToken: String? = null,
    var refreshToken: String? = null,
) {
}