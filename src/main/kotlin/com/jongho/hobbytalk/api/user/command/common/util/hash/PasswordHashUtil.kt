package com.jongho.hobbytalk.api.user.command.common.util.hash

interface PasswordHashUtil {
    fun hashPassword(password: String): String
    fun checkPassword(password: String, hashedPassword: String): Boolean
}