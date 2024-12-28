package com.jongho.hobbytalk.api.openChatRoom.command.domain.model

class OpenChatRoom(
    val id: Long,
    val title: String,
    val notice: String,
    val managerId: Long,
    val categoryId: Long,
    val maximumCapacity: Int,
    val currentAttendance: Int = 0,
    val password: String?
)