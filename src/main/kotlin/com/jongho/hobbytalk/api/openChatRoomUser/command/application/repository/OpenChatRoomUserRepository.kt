package com.jongho.hobbytalk.api.openChatRoomUser.command.application.repository

import com.jongho.hobbytalk.api.openChatRoomUser.command.domain.model.OpenChatRoomUser

interface OpenChatRoomUserRepository {
    fun save(openChatRoomUser: OpenChatRoomUser): Long
}