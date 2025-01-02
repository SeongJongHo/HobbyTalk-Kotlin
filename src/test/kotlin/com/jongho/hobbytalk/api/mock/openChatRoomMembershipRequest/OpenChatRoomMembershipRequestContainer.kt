package com.jongho.hobbytalk.api.mock.openChatRoomMembershipRequest

import com.jongho.hobbytalk.api.mock.openChatRoomMembershipRequest.repository.FakeOpenChatRoomMembershipRequestRepository
import com.jongho.hobbytalk.api.openChatRoomMembershipRequest.command.application.service.OpenChatRoomMembershipRequestServiceImpl

object OpenChatRoomMembershipRequestContainer {
    private val map: MutableMap<String, Any> = HashMap()

    init {
        map[OpenChatRoomMembershipRequestBeanKey.OPEN_CHAT_ROOM_MEMBERSHIP_REQUEST_REPOSITORY.getValue()] =
            FakeOpenChatRoomMembershipRequestRepository()
        map[OpenChatRoomMembershipRequestBeanKey.OPEN_CHAT_ROOM_MEMBERSHIP_REQUEST_SERVICE.getValue()] =
            OpenChatRoomMembershipRequestServiceImpl(
                this.get(OpenChatRoomMembershipRequestBeanKey.OPEN_CHAT_ROOM_MEMBERSHIP_REQUEST_REPOSITORY))
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> get(key: OpenChatRoomMembershipRequestBeanKey): T {
        return map[key.getValue()] as T
    }
}

enum class OpenChatRoomMembershipRequestBeanKey(private val value: String) {
    OPEN_CHAT_ROOM_MEMBERSHIP_REQUEST_REPOSITORY("OpenChatRoomMembershipRequestRepository"),
    OPEN_CHAT_ROOM_MEMBERSHIP_REQUEST_SERVICE("OpenChatRoomMembershipRequestService");

    fun getValue(): String {
        return value
    }
}

fun getMemberShipRequestContainer(): OpenChatRoomMembershipRequestContainer {
    return OpenChatRoomMembershipRequestContainer
}