package com.jongho.hobbytalk.api.user.command.application.usecase

import com.jongho.hobbytalk.api.user.command.application.dto.request.SignInUser
import com.jongho.hobbytalk.api.user.command.application.dto.response.TokenResponse
import com.jongho.hobbytalk.api.user.command.application.repository.AuthUserRepository
import com.jongho.hobbytalk.api.user.command.application.service.UserService
import com.jongho.hobbytalk.api.user.command.common.exception.UnAuthorizedException
import com.jongho.hobbytalk.api.user.command.common.exception.UserNotFoundException
import com.jongho.hobbytalk.api.user.command.common.util.hash.PasswordHashUtil
import com.jongho.hobbytalk.api.user.command.common.util.token.TokenUtil
import com.jongho.hobbytalk.api.user.command.domain.model.AuthUser
import org.springframework.transaction.annotation.Transactional

class SignInUseCase(
    private val hashUtil: PasswordHashUtil,
    private val tokenUtil: TokenUtil,
    private val userService: UserService,
    private val authUserRepository: AuthUserRepository) {

    @Transactional
    fun execute(signInUser: SignInUser): TokenResponse {
        val user = userService.findOneByUsername(username = signInUser.username) ?: throw UserNotFoundException("존재하지 않는 유저입니다.")

        if(!hashUtil.checkPassword(password = signInUser.password, hashedPassword = user.password)) {
            throw UnAuthorizedException("비밀번호가 틀렸습니다.")
        }

        val accessToken = tokenUtil.createAccessToken(userId = user.id)
        val refreshToken = tokenUtil.createRefreshToken(userId = user.id)

        authUserRepository.save(AuthUser(userId = user.id, refreshToken = refreshToken))

        return TokenResponse(accessToken = accessToken, refreshToken = refreshToken)
    }
}