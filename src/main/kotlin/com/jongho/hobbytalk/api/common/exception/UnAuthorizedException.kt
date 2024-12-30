package com.jongho.hobbytalk.api.common.exception

import org.springframework.http.HttpStatus


class UnAuthorizedException(message: String?) : CustomBusinessException(message, HttpStatus.UNAUTHORIZED)