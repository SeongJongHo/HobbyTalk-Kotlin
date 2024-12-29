package com.jongho.hobbytalk.api.mock.openChatRoomUser

import com.jongho.hobbytalk.api.mock.openChatRoomUser.repository.FakeOpenChatRoomUserRepositoryImpl
import com.jongho.hobbytalk.api.openChatRoomUser.command.application.service.OpenChatRoomUserServiceImpl

object OpenChatRoomUserContainer{
    private val map: MutableMap<String, Any> = HashMap()

    init {
        map[OpenChatRoomUserBeanKey.OPEN_CHAT_ROOM_USER_REPOSITORY.getValue()] = FakeOpenChatRoomUserRepositoryImpl()
        map[OpenChatRoomUserBeanKey.OPEN_CHAT_ROOM_USER_SERVICE.getValue()] = OpenChatRoomUserServiceImpl(
            openChatRoomUserRepository = this.get(key = OpenChatRoomUserBeanKey.OPEN_CHAT_ROOM_USER_REPOSITORY)
        )
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> get(key: OpenChatRoomUserBeanKey): T {
        return map[key.getValue()] as T
    }
}

enum class OpenChatRoomUserBeanKey(private val value: String) {
    OPEN_CHAT_ROOM_USER_REPOSITORY("OpenChatRoomUserRepository"),
    OPEN_CHAT_ROOM_USER_SERVICE("OpenChatRoomUserService");

    fun getValue(): String {
        return value
    }
}

fun getOpenChatRoomUserContainer(): OpenChatRoomUserContainer {
    return OpenChatRoomUserContainer
}