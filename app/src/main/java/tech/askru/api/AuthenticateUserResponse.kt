package tech.askru.api

data class AuthenticateUserResponse(
    val authenticated: Boolean?,
    val userName: String?,
    val userId: String?,
    val token: String?,
    val error: String?
)