package com.jongho.hobbytalk.api.openChatRoom.command.common.exception

import com.jongho.hobbytalk.api.common.exception.CustomBusinessException
import org.springframework.http.HttpStatus

class MaxChatRoomsExceededException(message: String?) : CustomBusinessException(message, HttpStatus.BAD_REQUEST)