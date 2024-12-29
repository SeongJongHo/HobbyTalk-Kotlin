package com.jongho.hobbytalk.api.openChatRoomUser.command.application.service

import com.jongho.hobbytalk.api.openChatRoomUser.command.application.repository.OpenChatRoomUserRepository
import com.jongho.hobbytalk.api.openChatRoomUser.command.domain.model.OpenChatRoomUser

class OpenChatRoomUserServiceImpl(private val openChatRoomUserRepository: OpenChatRoomUserRepository): OpenChatRoomUserService {
    override fun save(openChatRoomUser: OpenChatRoomUser): Long {
        return openChatRoomUserRepository.save(openChatRoomUser = openChatRoomUser)
    }
}