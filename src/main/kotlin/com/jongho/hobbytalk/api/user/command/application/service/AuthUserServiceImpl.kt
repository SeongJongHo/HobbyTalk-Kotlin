package com.jongho.hobbytalk.api.user.command.application.service

import com.jongho.hobbytalk.api.user.command.application.repository.AuthUserRepository
import com.jongho.hobbytalk.api.user.command.domain.model.AuthUser

class AuthUserServiceImpl(private val authUserRepository: AuthUserRepository): AuthUserService {
    override fun save(authUser: AuthUser) {
        authUserRepository.save(authUser = authUser)
    }

    override fun findOneByUserId(userId: Long): AuthUser? {
        return authUserRepository.findOneByUserId(userId = userId)
    }
}