package com.jongho.hobbytalk.api.openChatRoomUser.command.domain.model

import java.time.LocalDateTime

class OpenChatRoomUser (
    val openChatRoomId: Long,
    val userId: Long,
    val lastExitTime: LocalDateTime?
) {
    fun copy(
        openChatRoomId: Long = this.openChatRoomId,
        userId: Long = this.userId,
        lastExitTime: LocalDateTime? = this.lastExitTime
    ): OpenChatRoomUser {
        return OpenChatRoomUser(
            openChatRoomId = openChatRoomId,
            userId = userId,
            lastExitTime = lastExitTime
        )
    }
}