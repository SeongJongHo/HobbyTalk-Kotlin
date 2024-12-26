package com.jongho.hobbytalk.api.mock.common.util

import com.fasterxml.jackson.databind.ObjectMapper
import com.jongho.hobbytalk.api.user.command.common.util.token.Payload
import com.jongho.hobbytalk.api.user.command.common.util.token.TokenType
import com.jongho.hobbytalk.api.user.command.common.util.token.TokenUtil

class FakeTokenUtilImpl: TokenUtil {
    private val objectMapper = ObjectMapper()

    override fun createAccessToken(userId: Long): String {

        return createToken(userId, TokenType.ACCESS_TOKEN)
    }

    override fun createRefreshToken(userId: Long): String {
        return createToken(userId, TokenType.REFRESH_TOKEN)
    }

    private fun createToken(userId: Long, tokenType: TokenType): String {
        val tokenMap : MutableMap<String, String> = HashMap()
        tokenMap["userId"] = userId.toString()
        tokenMap["tokenType"] = tokenType.getValue().toString()

        return objectMapper.writeValueAsString(tokenMap)
    }

    override fun validateAccessToken(token: String): Payload {
        return validateToken(token)
    }

    override fun validateRefreshToken(token: String): Payload {
        return validateToken(token)
    }

    private fun validateToken(token: String): Payload {
        val map = objectMapper.readValue(token, Map::class.java)

        return Payload(
            userId = (map["userId"] as String).toLong(),
            tokenType = TokenType.fromValue((map["tokenType"] as String).toInt())
        )
    }
}