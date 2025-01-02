package com.jongho.hobbytalk.api.openChatRoomMembershipRequest.command.application.repository

import com.jongho.hobbytalk.api.openChatRoomMembershipRequest.command.domain.model.MembershipRequestStatus
import com.jongho.hobbytalk.api.openChatRoomMembershipRequest.command.domain.model.OpenChatRoomMembershipRequest



interface OpenChatRoomMembershipRequestRepository {
    fun count(requesterId: Long, status: MembershipRequestStatus): Int
    fun exists(requesterId: Long, openChatRoomId: Long, status: MembershipRequestStatus): Boolean
    fun save(openChatRoomMembershipRequest: OpenChatRoomMembershipRequest): Long
}