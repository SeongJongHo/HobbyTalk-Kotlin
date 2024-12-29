package com.jongho.hobbytalk.api.common.lock.service

interface LockService {
    fun acquireLock(id: Long): Boolean
    fun releaseLock(id: Long)
}