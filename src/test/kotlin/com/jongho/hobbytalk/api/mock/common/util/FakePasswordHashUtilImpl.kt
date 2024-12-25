package com.jongho.hobbytalk.api.mock.common.util

import com.jongho.hobbytalk.api.user.command.common.util.hash.PasswordHashUtil

class FakePasswordHashUtilImpl: PasswordHashUtil {
    override fun hashPassword(password: String): String {
        return password
    }

    override fun checkPassword(password: String, hashedPassword: String): Boolean {
        return  password == hashedPassword
    }
}