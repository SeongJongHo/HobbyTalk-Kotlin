package com.jongho.hobbytalk.api.mock.openChatRoomUser.repository

import com.jongho.hobbytalk.api.openChatRoomUser.command.application.repository.OpenChatRoomUserRepository
import com.jongho.hobbytalk.api.openChatRoomUser.command.domain.model.OpenChatRoomUser

class FakeOpenChatRoomUserRepositoryImpl : OpenChatRoomUserRepository {
    private var roomUserList: MutableList<OpenChatRoomUser> = mutableListOf()

    override fun save(openChatRoomUser: OpenChatRoomUser) {
        roomUserList.add(openChatRoomUser)
    }

    fun cleanUp() {
        roomUserList = mutableListOf()
    }
}