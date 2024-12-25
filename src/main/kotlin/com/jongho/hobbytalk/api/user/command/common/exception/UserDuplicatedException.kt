package com.jongho.hobbytalk.api.user.command.common.exception

import com.jongho.hobbytalk.api.common.exception.CustomBusinessException
import org.springframework.http.HttpStatus


class UserDuplicatedException(message: String?) : CustomBusinessException(message, HttpStatus.CONFLICT)