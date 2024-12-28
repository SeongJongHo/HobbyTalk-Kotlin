package com.jongho.hobbytalk.api.openChatRoom.command.application.service

import com.jongho.hobbytalk.api.openChatRoom.command.domain.model.OpenChatRoom

interface OpenChatRoomService {
    fun createOpenChatRoom(openChatRoom: OpenChatRoom): Long
}