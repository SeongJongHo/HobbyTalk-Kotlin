package com.jongho.hobbytalk.api.openChatRoomMembershipRequest.command.common.exception

import com.jongho.hobbytalk.api.common.exception.CustomBusinessException
import org.springframework.http.HttpStatus

class DuplicateRequestException(message: String) : CustomBusinessException(message, HttpStatus.CONFLICT)