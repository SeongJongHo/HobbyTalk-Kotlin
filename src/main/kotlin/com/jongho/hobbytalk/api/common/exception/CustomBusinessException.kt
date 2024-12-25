package com.jongho.hobbytalk.api.common.exception

import org.springframework.http.HttpStatus


open class CustomBusinessException(message: String?, private val httpStatus: HttpStatus) : RuntimeException(message)