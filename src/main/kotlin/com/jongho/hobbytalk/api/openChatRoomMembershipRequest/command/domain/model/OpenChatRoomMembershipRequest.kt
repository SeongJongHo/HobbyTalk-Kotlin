package com.jongho.hobbytalk.api.openChatRoomMembershipRequest.command.domain.model

class OpenChatRoomMembershipRequest (
    var id: Long,
    val requesterId: Long,
    val openChatRoomId: Long,
    val message: String,
    val status: MembershipRequestStatus
) {
    fun copy(
        id: Long = this.id,
        requesterId: Long = this.requesterId,
        openChatRoomId: Long = this.openChatRoomId,
        message: String = this.message,
        status: MembershipRequestStatus = this.status
    ): OpenChatRoomMembershipRequest {
        return OpenChatRoomMembershipRequest(id, requesterId, openChatRoomId, message, status)
    }
}

enum class MembershipRequestStatus(val value: Int) {
    PARTICIPATION_REQUEST(1),
    APPROVED(2),
    REJECTED(3);

    companion object {
        fun fromValue(value: Int): MembershipRequestStatus {
            return entries.find { it.value == value }
                ?: throw IllegalArgumentException("유효하지 않는 값입니다.: $value")
        }
    }
}