package com.jongho.hobbytalk.api.common.exception

import org.springframework.http.HttpStatus

class LockAcquisitionException(message: String) : CustomBusinessException(message, HttpStatus.CONFLICT)