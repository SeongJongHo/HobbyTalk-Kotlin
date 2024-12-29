package com.jongho.hobbytalk.api.common.redis.service

import com.jongho.hobbytalk.api.common.lock.repository.LockRepository
import com.jongho.hobbytalk.api.common.lock.service.LockKey
import com.jongho.hobbytalk.api.common.lock.service.LockService

class RedisLockServiceImpl(private val lockRepository: LockRepository): LockService {
    override fun acquireLock(id: Long, key: LockKey): Boolean {
        return lockRepository.acquireLock(genKey(id, key))
    }

    override fun releaseLock(id: Long, key: LockKey) {
        lockRepository.releaseLock(genKey(id, key))
    }

    private fun genKey(id: Long, key: LockKey): String {
        return key.getKey() + id
    }
}

enum class RedisKey(private val key: String): LockKey {
    OPEN_CHAT_ROOM_LIMIT("openChatRoomLimit:userId:");

    override fun getKey(): String {
        return this.key
    }
}