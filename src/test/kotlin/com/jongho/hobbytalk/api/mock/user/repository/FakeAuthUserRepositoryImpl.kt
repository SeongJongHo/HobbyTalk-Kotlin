package com.jongho.hobbytalk.api.mock.user.repository

import com.jongho.hobbytalk.api.user.command.application.repository.AuthUserRepository
import com.jongho.hobbytalk.api.user.command.domain.model.AuthUser
import java.util.concurrent.atomic.AtomicLong

class FakeAuthUserRepositoryImpl: AuthUserRepository {
    private var authUserList: MutableList<AuthUser> = mutableListOf()

    override fun save(authUser: AuthUser){
        val i = authUserList.indexOfFirst { it -> it.userId == authUser.userId }

        if(i == -1) {
            authUserList.add(authUser)
        }
        else {
            authUserList.add(i, authUser)
        }
    }

    fun clenUp() {
        authUserList = mutableListOf()
    }
}