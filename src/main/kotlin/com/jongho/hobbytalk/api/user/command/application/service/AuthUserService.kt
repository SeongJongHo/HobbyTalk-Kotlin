package com.jongho.hobbytalk.api.user.command.application.service

import com.jongho.hobbytalk.api.user.command.domain.model.AuthUser

interface AuthUserService {
    fun save(authUser: AuthUser)
    fun findOneByUserId(userId: Long): AuthUser?
}