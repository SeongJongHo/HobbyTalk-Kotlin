package com.jongho.hobbytalk.api.common.exception

import org.springframework.http.HttpStatus

class ChatRoomLimitReachedException(message: String) : CustomBusinessException(message, HttpStatus.FORBIDDEN)