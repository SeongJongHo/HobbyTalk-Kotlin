package com.jongho.hobbytalk.api.user.command.common.util.token


interface TokenUtil {
    fun createAccessToken(userId: Long): String
    fun createRefreshToken(userId: Long): String
    fun validateAccessToken(token: String): Payload
    fun validateRefreshToken(token: String): Payload
}

data class Payload(private val userId: Long, private val tokenType: TokenType)

enum class TokenType(private val value: Int) {
    ACCESS_TOKEN(1),
    REFRESH_TOKEN(2);

    fun getValue(): Int {
        return this.value
    }
}