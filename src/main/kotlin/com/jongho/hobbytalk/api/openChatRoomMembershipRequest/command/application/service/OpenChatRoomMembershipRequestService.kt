package com.jongho.hobbytalk.api.openChatRoomMembershipRequest.command.application.service

import com.jongho.hobbytalk.api.openChatRoomMembershipRequest.command.domain.model.OpenChatRoomMembershipRequest

interface OpenChatRoomMembershipRequestService {
    fun request(openChatRoomMembershipRequest: OpenChatRoomMembershipRequest): Long
}