package com.jongho.hobbytalk.api.user.command.domain.model

class AuthUser (
    val userId: Long,
    val refreshToken: String?
) {
    fun copy(
    userId: Long = this.userId,
    refreshToken: String? = this.refreshToken
    ): AuthUser {
        return AuthUser(userId, refreshToken)
    }
}