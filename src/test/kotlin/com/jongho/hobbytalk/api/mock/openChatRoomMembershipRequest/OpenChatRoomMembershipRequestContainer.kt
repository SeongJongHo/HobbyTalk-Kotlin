package com.jongho.hobbytalk.api.mock.openChatRoomMembershipRequest

import com.jongho.hobbytalk.api.mock.openChatRoom.OpenChatRoomBeanKey
import com.jongho.hobbytalk.api.mock.openChatRoom.getOpenChatRoomContainer
import com.jongho.hobbytalk.api.mock.openChatRoomMembershipRequest.repository.FakeOpenChatRoomMembershipRequestRepository
import com.jongho.hobbytalk.api.mock.openChatRoomUser.OpenChatRoomUserBeanKey
import com.jongho.hobbytalk.api.mock.openChatRoomUser.getOpenChatRoomUserContainer
import com.jongho.hobbytalk.api.openChatRoomMembershipRequest.command.application.service.OpenChatRoomMembershipRequestServiceImpl
import com.jongho.hobbytalk.api.openChatRoomMembershipRequest.command.application.usecase.JoinRequestUseCase

object OpenChatRoomMembershipRequestContainer {
    private val map: MutableMap<String, Any> = HashMap()
    private val openChatRoomContainer = getOpenChatRoomContainer()
    private val openChatRoomUserContainer = getOpenChatRoomUserContainer()

    init {
        map[OpenChatRoomMembershipRequestBeanKey.OPEN_CHAT_ROOM_MEMBERSHIP_REQUEST_REPOSITORY.getValue()] =
            FakeOpenChatRoomMembershipRequestRepository()
        map[OpenChatRoomMembershipRequestBeanKey.OPEN_CHAT_ROOM_MEMBERSHIP_REQUEST_SERVICE.getValue()] =
            OpenChatRoomMembershipRequestServiceImpl(
                this.get(OpenChatRoomMembershipRequestBeanKey.OPEN_CHAT_ROOM_MEMBERSHIP_REQUEST_REPOSITORY))
        map[OpenChatRoomMembershipRequestBeanKey.JOIN_REQUEST_USE_CASE.getValue()] =
            JoinRequestUseCase(
                openChatRoomMembershipRequestService =
                    this.get(OpenChatRoomMembershipRequestBeanKey.OPEN_CHAT_ROOM_MEMBERSHIP_REQUEST_SERVICE),
                openChatRoomUserService = openChatRoomUserContainer.get(
                    key = OpenChatRoomUserBeanKey.OPEN_CHAT_ROOM_USER_SERVICE),
                openChatRoomService = openChatRoomContainer.get(
                    key = OpenChatRoomBeanKey.OPEN_CHAT_ROOM_SERVICE)
            )
    }
    @Suppress("UNCHECKED_CAST")
    fun <T> get(key: OpenChatRoomMembershipRequestBeanKey): T {
        return map[key.getValue()] as T
    }
}

enum class OpenChatRoomMembershipRequestBeanKey(private val value: String) {
    OPEN_CHAT_ROOM_MEMBERSHIP_REQUEST_REPOSITORY("OpenChatRoomMembershipRequestRepository"),
    OPEN_CHAT_ROOM_MEMBERSHIP_REQUEST_SERVICE("OpenChatRoomMembershipRequestService"),
    JOIN_REQUEST_USE_CASE("JoinRequestUseCase");

    fun getValue(): String {
        return value
    }
}

fun getMemberShipRequestContainer(): OpenChatRoomMembershipRequestContainer {
    return OpenChatRoomMembershipRequestContainer
}