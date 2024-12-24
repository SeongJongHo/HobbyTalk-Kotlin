package com.jongho.hobbytalk.api.user.command.domain.model

import java.time.LocalDateTime

class User(
    val id: Long,
    val username: String,
    val phoneNumber: String,
    val password: String,
    val nickname: String,
    val profileImage: String? = null,
    val createdTime: LocalDateTime,
    val isDeleted: Boolean = false,
    val deletedTime: LocalDateTime? = null
) {
    fun copy(
        id: Long = this.id,
        username: String = this.username,
        phoneNumber: String = this.phoneNumber,
        password: String = this.password,
        nickname: String = this.nickname,
        profileImage: String? = this.profileImage,
        createdTime: LocalDateTime = this.createdTime,
        isDeleted: Boolean = this.isDeleted,
        deletedTime: LocalDateTime? = this.deletedTime
    ): User {
        return User(
            id = id,
            username = username,
            phoneNumber = phoneNumber,
            password = password,
            nickname = nickname,
            profileImage = profileImage,
            createdTime = createdTime,
            isDeleted = isDeleted,
            deletedTime = deletedTime
        )
    }
}