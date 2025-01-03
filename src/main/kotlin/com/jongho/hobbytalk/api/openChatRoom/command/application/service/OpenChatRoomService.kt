package com.jongho.hobbytalk.api.openChatRoom.command.application.service

import com.jongho.hobbytalk.api.openChatRoom.command.domain.model.OpenChatRoom

interface OpenChatRoomService {
    fun createOpenChatRoom(openChatRoom: OpenChatRoom): Long
    fun updateNotice(managerId: Long, id: Long, notice: String)
    fun getOpenChatRoom(id: Long): OpenChatRoom?
}