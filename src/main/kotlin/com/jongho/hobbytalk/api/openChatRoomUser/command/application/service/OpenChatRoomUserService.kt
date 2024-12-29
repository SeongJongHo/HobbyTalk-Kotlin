package com.jongho.hobbytalk.api.openChatRoomUser.command.application.service

import com.jongho.hobbytalk.api.openChatRoomUser.command.domain.model.OpenChatRoomUser

interface OpenChatRoomUserService {
    fun save(openChatRoomUser: OpenChatRoomUser)
}