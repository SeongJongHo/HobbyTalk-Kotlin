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
) {
    fun copy(
        id: Long = this.id,
        title: String = this.title,
        notice: String = this.notice,
        managerId: Long = this.managerId,
        categoryId: Long = this.categoryId,
        maximumCapacity: Int = this.maximumCapacity,
        currentAttendance: Int = this.currentAttendance,
        password: String? = this.password
    ): OpenChatRoom {
        return OpenChatRoom(
            id,
            title,
            notice,
            managerId,
            categoryId,
            maximumCapacity,
            currentAttendance,
            password
        )
    }
}