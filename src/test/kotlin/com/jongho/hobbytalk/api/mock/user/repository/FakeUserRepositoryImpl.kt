package com.jongho.hobbytalk.api.mock.user.repository

import com.jongho.hobbytalk.api.user.command.application.repository.UserRepository
import com.jongho.hobbytalk.api.user.command.domain.model.User
import org.springframework.dao.DataIntegrityViolationException
import java.util.concurrent.atomic.AtomicLong

class FakeUserRepositoryImpl: UserRepository {
    private var userList: MutableList<User> = mutableListOf()
    private var id: AtomicLong = AtomicLong(0)

    override fun findOneByUsername(username: String): User? {
        return userList.firstOrNull{user -> user.username == username}
    }
    override fun findOneByPhoneNumber(phoneNumber: String): User? {
        return userList.firstOrNull{user -> user.phoneNumber == phoneNumber }
    }
    override fun findOneById(id: Long): User? {
        return userList.firstOrNull{user -> user.id == id}
    }
    override fun save(user: User): Long {
        if(user.nickname == "duplicated") throw DataIntegrityViolationException("")

        val userId: Long
        if(user.id != 0L) {
            userList[userList.indexOfFirst { it -> it.id == user.id }] = user

            userId = user.id
        }
        else {
            userList.add(user.copy(id = id.incrementAndGet()))

            userId = id.get()
        }

        return userId
    }

    fun cleanUp() {
        userList = mutableListOf()
        id = AtomicLong(0)
    }
}