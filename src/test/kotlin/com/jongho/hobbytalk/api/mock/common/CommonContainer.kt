package com.jongho.hobbytalk.api.mock.common

import com.jongho.hobbytalk.api.mock.common.util.FakePasswordHashUtilImpl

class CommonContainer {
    private val map: MutableMap<String, Any> = HashMap()

    init {
        map[CommonBeanKey.PASSWORD_HASH_UTIL.getValue()] = FakePasswordHashUtilImpl()
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> get(key: CommonBeanKey): T {
        return map[key.getValue()] as T
    }
}

enum class CommonBeanKey(private val value: String) {
    PASSWORD_HASH_UTIL("PasswordHashUtil");

    fun getValue(): String {
        return value
    }
}

fun getCommonContainer(): CommonContainer {
    return CommonContainer()
}