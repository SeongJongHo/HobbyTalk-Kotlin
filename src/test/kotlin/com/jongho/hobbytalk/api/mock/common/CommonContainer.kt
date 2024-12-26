package com.jongho.hobbytalk.api.mock.common

import com.jongho.hobbytalk.api.mock.common.util.FakePasswordHashUtilImpl
import com.jongho.hobbytalk.api.mock.common.util.FakeTokenUtilImpl

class CommonContainer {
    private val map: MutableMap<String, Any> = HashMap()

    init {
        map[CommonBeanKey.PASSWORD_HASH_UTIL.getValue()] = FakePasswordHashUtilImpl()
        map[CommonBeanKey.TOKEN_UTIL.getValue()] = FakeTokenUtilImpl()
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> get(key: CommonBeanKey): T {
        return map[key.getValue()] as T
    }
}

enum class CommonBeanKey(private val value: String) {
    PASSWORD_HASH_UTIL("PasswordHashUtil"),
    TOKEN_UTIL("TokenUtil");

    fun getValue(): String {
        return value
    }
}

fun getCommonContainer(): CommonContainer {
    return CommonContainer()
}