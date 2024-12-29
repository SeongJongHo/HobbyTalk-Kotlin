package com.jongho.hobbytalk.api.common.redis.service

import com.jongho.hobbytalk.api.common.lock.repository.LockRepository

class RedisLockServiceImpl(private val lockRepository: LockRepository) {
    fun acquireLock(id: Long, key: RedisKey): Boolean {
        return lockRepository.acquireLock(genKey(id, key))
    }

    fun releaseLock(id: Long, key: RedisKey) {
        lockRepository.releaseLock(genKey(id, key))
    }

    private fun genKey(id: Long, key: RedisKey): String {
        return key.getValue() + id
    }
}

enum class RedisKey(val value: String) {
    OPEN_CHAT_ROOM_LIMIT("openChatRoomLimit:userId:");

    fun getValue(): String {
        return this.value
    }
}