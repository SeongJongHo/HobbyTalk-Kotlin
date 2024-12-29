package com.jongho.hobbytalk.api.common.lock.repository

interface LockRepository {
    fun acquireLock(key: String): Boolean
    fun releaseLock(key: String)
}