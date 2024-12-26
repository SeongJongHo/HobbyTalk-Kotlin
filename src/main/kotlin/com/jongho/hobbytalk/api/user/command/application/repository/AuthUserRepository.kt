package com.jongho.hobbytalk.api.user.command.application.repository

import com.jongho.hobbytalk.api.user.command.domain.model.AuthUser

interface AuthUserRepository {
    fun save(authUser: AuthUser)
}