package com.jongho.hobbytalk.api.mock.common.lock

import com.jongho.hobbytalk.api.common.lock.repository.LockRepository

class FakeLockRepositoryImpl : LockRepository {
    private val lockSet: MutableSet<String> = mutableSetOf()

    override fun acquireLock(key: String): Boolean {
        return lockSet.add(key)
    }

    override fun releaseLock(key: String) {
        lockSet.remove(key)
    }

    fun cleanUp() {
        lockSet.clear()
    }
}