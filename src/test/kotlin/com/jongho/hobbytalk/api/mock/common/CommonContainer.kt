package com.jongho.hobbytalk.api.mock.common

import com.jongho.hobbytalk.api.common.redis.service.RedisLockServiceImpl
import com.jongho.hobbytalk.api.mock.common.lock.FakeLockRepositoryImpl
import com.jongho.hobbytalk.api.mock.common.util.FakePasswordHashUtilImpl
import com.jongho.hobbytalk.api.mock.common.util.FakeTokenUtilImpl

object CommonContainer {
    private val map: MutableMap<String, Any> = HashMap()

    init {
        map[CommonBeanKey.PASSWORD_HASH_UTIL.getValue()] = FakePasswordHashUtilImpl()
        map[CommonBeanKey.TOKEN_UTIL.getValue()] = FakeTokenUtilImpl()
        map[CommonBeanKey.LOCK_REPOSITORY.getValue()] = FakeLockRepositoryImpl()
        map[CommonBeanKey.LOCK_SERVICE.getValue()] = RedisLockServiceImpl(
            lockRepository = this.get(key = CommonBeanKey.LOCK_REPOSITORY)
        )
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> get(key: CommonBeanKey): T {
        return map[key.getValue()] as T
    }
}

enum class CommonBeanKey(private val value: String) {
    PASSWORD_HASH_UTIL("PasswordHashUtil"),
    TOKEN_UTIL("TokenUtil"),
    LOCK_REPOSITORY("LockRepository"),
    LOCK_SERVICE("LockService");

    fun getValue(): String {
        return value
    }
}

fun getCommonContainer(): CommonContainer {
    return CommonContainer
}