package com.jongho.hobbytalk.api.openChatRoomMembershipRequest.command.application.service

import com.jongho.hobbytalk.api.openChatRoom.command.application.repository.OpenChatRoomRepository
import com.jongho.hobbytalk.api.openChatRoomMembershipRequest.command.application.repository.OpenChatRoomMembershipRequestRepository
import com.jongho.hobbytalk.api.openChatRoomMembershipRequest.command.common.exception.DuplicateRequestException
import com.jongho.hobbytalk.api.openChatRoomMembershipRequest.command.common.exception.RequestLimitExceededException
import com.jongho.hobbytalk.api.openChatRoomMembershipRequest.command.domain.model.MembershipRequestStatus
import com.jongho.hobbytalk.api.openChatRoomMembershipRequest.command.domain.model.OpenChatRoomMembershipRequest

class OpenChatRoomMembershipRequestServiceImpl(
    private val openChatRoomMembershipRequestRepository: OpenChatRoomMembershipRequestRepository
): OpenChatRoomMembershipRequestService {

    override fun request(openChatRoomMembershipRequest: OpenChatRoomMembershipRequest): Long {
        if(openChatRoomMembershipRequestRepository.exists(
                requesterId = openChatRoomMembershipRequest.requesterId,
                openChatRoomId = openChatRoomMembershipRequest.openChatRoomId,
                status = MembershipRequestStatus.PARTICIPATION_REQUEST)) {
            throw DuplicateRequestException("이미 요청한 채팅방 입니다.: ${openChatRoomMembershipRequest.openChatRoomId}")
        }
        if(openChatRoomMembershipRequestRepository.count(
                requesterId = openChatRoomMembershipRequest.requesterId,
                status = MembershipRequestStatus.PARTICIPATION_REQUEST) >= 5) {

            throw RequestLimitExceededException("5회 이상 참가요청 할 수 없습니다.")
        }

        return openChatRoomMembershipRequestRepository.save(openChatRoomMembershipRequest = openChatRoomMembershipRequest)
    }
}