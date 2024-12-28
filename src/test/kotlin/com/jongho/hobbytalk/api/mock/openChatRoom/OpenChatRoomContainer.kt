package com.jongho.hobbytalk.api.mock.openChatRoom

import com.jongho.hobbytalk.api.mock.openChatRoom.repository.FakeOpenChatRoomRepositoryImpl
import com.jongho.hobbytalk.api.openChatRoom.command.application.service.OpenChatRoomServiceImpl

class OpenChatRoomContainer (){
    private val map: MutableMap<String, Any> = HashMap()

    init {
        map[OpenChatRoomBeanKey.OPEN_CHAT_ROOM_REPOSITORY.getValue()] = FakeOpenChatRoomRepositoryImpl()
        map[OpenChatRoomBeanKey.OPEN_CHAT_ROOM_SERVICE.getValue()] = OpenChatRoomServiceImpl(
            this.get(key = OpenChatRoomBeanKey.OPEN_CHAT_ROOM_REPOSITORY)
        )
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> get(key: OpenChatRoomBeanKey): T {
        return map[key.getValue()] as T
    }
}

enum class OpenChatRoomBeanKey(private val value: String) {
    OPEN_CHAT_ROOM_REPOSITORY("OpenChatRoomRepository"),
    OPEN_CHAT_ROOM_SERVICE("OpenChatRoomService");

    fun getValue(): String {
        return value
    }
}

fun getOpenChatRoomContainer(): OpenChatRoomContainer {
    return OpenChatRoomContainer()
}