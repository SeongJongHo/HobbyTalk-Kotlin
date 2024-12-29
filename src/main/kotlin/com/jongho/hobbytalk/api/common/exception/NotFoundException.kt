package com.jongho.hobbytalk.api.common.exception

import org.springframework.http.HttpStatus

class NotFoundException(message: String) : CustomBusinessException(message, HttpStatus.NOT_FOUND)