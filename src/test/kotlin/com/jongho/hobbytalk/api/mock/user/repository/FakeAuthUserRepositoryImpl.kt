package com.jongho.hobbytalk.api.mock.user.repository

import com.jongho.hobbytalk.api.user.command.application.repository.AuthUserRepository
import com.jongho.hobbytalk.api.user.command.domain.model.AuthUser

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

    override fun findOneByUserId(userId: Long): AuthUser? {
        return authUserList.firstOrNull{authUser -> authUser.userId == userId }
    }

    fun clenUp() {
        authUserList = mutableListOf()
    }
}