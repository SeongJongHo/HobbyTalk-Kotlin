package com.jongho.hobbytalk.api.openChatRoom.command.application.dto.request

import com.jongho.hobbytalk.api.openChatRoom.command.domain.model.OpenChatRoom

data class CreateOpenChatRoom(
    val title: String,
    val notice: String,
    val categoryId: Long,
    val maximumCapacity: Int,
    val password: String
) {
    fun toModel(managerId: Long): OpenChatRoom {
        return OpenChatRoom(
            id = 0L, // ID는 보통 생성 시점에 자동으로 할당
            title = this.title,
            notice = this.notice,
            managerId = managerId,
            categoryId = this.categoryId,
            maximumCapacity = this.maximumCapacity,
            currentAttendance = 1,
            password = this.password
        )
    }
}