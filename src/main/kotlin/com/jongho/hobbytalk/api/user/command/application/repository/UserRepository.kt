package com.jongho.hobbytalk.api.user.command.application.repository

import com.jongho.hobbytalk.api.user.command.domain.model.User

interface UserRepository {
    fun findOneByUsername(username: String): User?
    fun findOneByPhoneNumber(phoneNumber: String): User?
    fun findOneById(id: Long): User?
    fun save(user: User): Long
}