package com.jongho.hobbytalk.api.user.command.application.service

import com.jongho.hobbytalk.api.user.command.domain.model.User

interface UserService {
    fun signUp(user: User): Long
}