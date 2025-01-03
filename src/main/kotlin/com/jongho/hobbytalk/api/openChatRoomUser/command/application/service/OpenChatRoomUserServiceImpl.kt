package com.jongho.hobbytalk.api.openChatRoomUser.command.application.service

import com.jongho.hobbytalk.api.openChatRoomUser.command.application.repository.OpenChatRoomUserRepository
import com.jongho.hobbytalk.api.openChatRoomUser.command.domain.model.OpenChatRoomUser

class OpenChatRoomUserServiceImpl(private val openChatRoomUserRepository: OpenChatRoomUserRepository): OpenChatRoomUserService {
    override fun save(openChatRoomUser: OpenChatRoomUser){
        return openChatRoomUserRepository.save(openChatRoomUser = openChatRoomUser)
    }

    override fun exists(userId: Long, roomId: Long): Boolean {
        return openChatRoomUserRepository.findOneByUserIdAndRoomId(userId = userId, roomId = roomId) != null
    }
}