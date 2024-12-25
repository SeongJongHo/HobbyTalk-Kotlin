package com.jongho.hobbytalk.api.user.command.application.usecase

import com.jongho.hobbytalk.api.user.command.application.dto.SignUpUser
import com.jongho.hobbytalk.api.user.command.application.service.UserService
import com.jongho.hobbytalk.api.user.command.common.util.hash.PasswordHashUtil

class SignUpUseCase(
    private val passwordHashUtil: PasswordHashUtil,
    private val userService: UserService,
    ) {
    fun execute(signUpUser: SignUpUser): Long {
        val user = signUpUser.copy(password = passwordHashUtil.hashPassword(signUpUser.password)).toModel()

        val userId = userService.signUp(user = user)

        return userId
    }
}