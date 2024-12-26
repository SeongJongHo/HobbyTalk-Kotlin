package com.jongho.hobbytalk.api.user.command.application.dto.response

data class TokenResponse(
    val accessToken: String,
    val refreshToken: String
)
