package com.jongho.hobbytalk.api.user.command.application.dto.request

import com.jongho.hobbytalk.api.user.command.domain.model.User
import java.time.LocalDateTime

data class SignUpUser(
    val username: String,
    val phoneNumber: String,
    val password: String,
    val nickname: String,
    val profileImage: String? = null,
) {
    fun toModel(): User {
        return User(
            id = 0L,
            username = this.username,
            phoneNumber = this.phoneNumber,
            password = this.password,
            nickname = this.nickname,
            profileImage = this.profileImage,
            createdTime = LocalDateTime.now(),
            isDeleted = false,
            deletedTime = null
        )
    }
}
