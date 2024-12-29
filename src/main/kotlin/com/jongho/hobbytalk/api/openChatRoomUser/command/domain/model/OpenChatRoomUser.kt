package com.jongho.hobbytalk.api.openChatRoomUser.command.domain.model

import java.time.LocalDateTime

class OpenChatRoomUser (
    val openChatRoomId: Long,
    val userId: Long,
    val lastExitTime: LocalDateTime
)