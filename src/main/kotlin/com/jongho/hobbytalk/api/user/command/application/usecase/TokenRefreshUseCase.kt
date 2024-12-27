package com.jongho.hobbytalk.api.user.command.application.usecase

import com.jongho.hobbytalk.api.user.command.application.dto.response.TokenResponse
import com.jongho.hobbytalk.api.user.command.application.service.AuthUserService
import com.jongho.hobbytalk.api.user.command.common.exception.UnAuthorizedException
import com.jongho.hobbytalk.api.user.command.common.exception.UserNotFoundException
import com.jongho.hobbytalk.api.user.command.common.util.token.TokenUtil
import org.springframework.transaction.annotation.Transactional

class TokenRefreshUseCase(
    private val authUserService: AuthUserService,
    private val tokenUtil: TokenUtil ) {

    @Transactional
    fun execute(userId: Long, refreshToken: String): TokenResponse {
        val authUser = authUserService.findOneByUserId(userId) ?: throw UserNotFoundException("존재하지 않는 유저입니다.")

        if(authUser.refreshToken == null || authUser.refreshToken != refreshToken) {
            throw UnAuthorizedException("유효하지 않는 토큰입니다.")
        }

        val aToken = tokenUtil.createAccessToken(userId = userId)
        val rToken = tokenUtil.createRefreshToken(userId = userId)

        authUserService.save(authUser = authUser.copy(refreshToken = rToken))

        return TokenResponse(accessToken = aToken, refreshToken = rToken)
    }
}