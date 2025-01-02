package com.jongho.hobbytalk.api.mock.openChatRoomMembershipRequest.repository

import com.jongho.hobbytalk.api.openChatRoomMembershipRequest.command.application.repository.OpenChatRoomMembershipRequestRepository
import com.jongho.hobbytalk.api.openChatRoomMembershipRequest.command.domain.model.MembershipRequestStatus
import com.jongho.hobbytalk.api.openChatRoomMembershipRequest.command.domain.model.OpenChatRoomMembershipRequest
import java.util.concurrent.atomic.AtomicLong

class FakeOpenChatRoomMembershipRequestRepository : OpenChatRoomMembershipRequestRepository {
    private var requestList = mutableListOf<OpenChatRoomMembershipRequest>()
    private var id = AtomicLong(0)

    override fun count(requesterId: Long, status: MembershipRequestStatus): Int {
        return requestList.count { it.requesterId == requesterId && it.status == status }
    }

    override fun exists(requesterId: Long, openChatRoomId: Long, status: MembershipRequestStatus): Boolean {
        return requestList.any {
            it.requesterId == requesterId &&
                    it.openChatRoomId == openChatRoomId &&
                    it.status == status
        }
    }

    override fun save(openChatRoomMembershipRequest: OpenChatRoomMembershipRequest): Long {
        val requestId: Long

        if (openChatRoomMembershipRequest.id != 0L) {
            val index = requestList.indexOfFirst { it.id == openChatRoomMembershipRequest.id }
            if (index != -1) {
                requestList[index] = openChatRoomMembershipRequest
            }

            requestId = openChatRoomMembershipRequest.id
        } else {
            val newId = id.incrementAndGet()
            requestList.add(openChatRoomMembershipRequest.copy(id = newId))

            requestId = newId
        }

        return requestId
    }

    fun cleanUp() {
        requestList = mutableListOf()
        id = AtomicLong(0)
    }
}
