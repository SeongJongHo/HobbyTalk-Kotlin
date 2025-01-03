package com.jongho.hobbytalk.api.openChatRoomMembershipRequest.command.application.usecase

import com.jongho.hobbytalk.api.common.exception.AlreadyExistsException
import com.jongho.hobbytalk.api.common.exception.ChatRoomLimitReachedException
import com.jongho.hobbytalk.api.openChatRoom.command.application.service.OpenChatRoomService
import com.jongho.hobbytalk.api.openChatRoomMembershipRequest.command.application.service.OpenChatRoomMembershipRequestService
import com.jongho.hobbytalk.api.openChatRoomMembershipRequest.command.domain.model.MembershipRequestStatus
import com.jongho.hobbytalk.api.openChatRoomMembershipRequest.command.domain.model.OpenChatRoomMembershipRequest
import com.jongho.hobbytalk.api.openChatRoomUser.command.application.service.OpenChatRoomUserService

class JoinRequestUseCase(
    private val openChatRoomMembershipRequestService: OpenChatRoomMembershipRequestService,
    private val openChatRoomUserService: OpenChatRoomUserService,
    private val openChatRoomService: OpenChatRoomService
) {
    fun execute(requesterId: Long, roomId: Long, message: String): Long {
        val room = openChatRoomService.getOpenChatRoom(id = roomId) ?: throw RuntimeException("")
        if(room.currentAttendance >= room.maximumCapacity) {
            throw ChatRoomLimitReachedException("이미 참여중인 채팅방입니다.")
        }
        if(openChatRoomUserService.exists(userId = requesterId, roomId = roomId)) {
            throw AlreadyExistsException("이미 참여중인 채팅방입니다.")
        }

        return openChatRoomMembershipRequestService.request(
            openChatRoomMembershipRequest = OpenChatRoomMembershipRequest(
                id = 0L,
                requesterId = requesterId,
                openChatRoomId = roomId,
                message = message,
                status = MembershipRequestStatus.PARTICIPATION_REQUEST
            )
        )
    }
}