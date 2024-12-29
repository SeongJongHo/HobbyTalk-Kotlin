package com.jongho.hobbytalk.api.common.lock.service

interface LockService {
    fun acquireLock(id: Long, key: LockKey): Boolean
    fun releaseLock(id: Long, key: LockKey)
}

interface LockKey {
    fun getKey(): String
}