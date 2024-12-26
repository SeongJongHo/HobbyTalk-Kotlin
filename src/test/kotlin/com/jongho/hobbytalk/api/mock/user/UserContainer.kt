package com.jongho.hobbytalk.api.mock.user

import com.jongho.hobbytalk.api.mock.common.CommonBeanKey
import com.jongho.hobbytalk.api.mock.common.CommonContainer
import com.jongho.hobbytalk.api.mock.common.getCommonContainer
import com.jongho.hobbytalk.api.mock.user.repository.FakeAuthUserRepositoryImpl
import com.jongho.hobbytalk.api.mock.user.repository.FakeUserRepositoryImpl
import com.jongho.hobbytalk.api.user.command.application.service.UserServiceImpl
import com.jongho.hobbytalk.api.user.command.application.usecase.SignInUseCase
import com.jongho.hobbytalk.api.user.command.application.usecase.SignUpUseCase
import com.jongho.hobbytalk.api.user.command.common.util.hash.PasswordHashUtil
import com.jongho.hobbytalk.api.user.command.common.util.token.TokenUtil

class UserContainer (){
    private val map: MutableMap<String, Any> = HashMap()
    private val commonContainer: CommonContainer = getCommonContainer()

    init {
        map[UserBeanKey.USER_REPOSITORY.getValue()] = FakeUserRepositoryImpl()
        map[UserBeanKey.USER_SERVICE.getValue()] = UserServiceImpl(this.get(UserBeanKey.USER_REPOSITORY))
        map[UserBeanKey.SIGN_UP_USE_CASE.getValue()] = SignUpUseCase(
            passwordHashUtil = commonContainer.get<PasswordHashUtil>(CommonBeanKey.PASSWORD_HASH_UTIL),
            userService = this.get(UserBeanKey.USER_SERVICE)
        )
        map[UserBeanKey.AUTH_USER_REPOSITORY.getValue()] = FakeAuthUserRepositoryImpl()
        map[UserBeanKey.SIGN_IN_USE_CASE.getValue()] = SignInUseCase(
            tokenUtil = commonContainer.get<TokenUtil>(CommonBeanKey.TOKEN_UTIL),
            hashUtil = commonContainer.get<PasswordHashUtil>(CommonBeanKey.PASSWORD_HASH_UTIL),
            userService = this.get(UserBeanKey.USER_SERVICE),
            authUserRepository = this.get(UserBeanKey.AUTH_USER_REPOSITORY),
        )
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> get(key: UserBeanKey): T {
        return map[key.getValue()] as T
    }
}

enum class UserBeanKey(private val value: String) {
    USER_REPOSITORY("UserRepository"),
    USER_SERVICE("UserService"),
    SIGN_UP_USE_CASE("SignUpUseCase"),
    AUTH_USER_REPOSITORY("AuthUserRepository"),
    SIGN_IN_USE_CASE("SignInUseCase");

    fun getValue(): String {
        return value
    }
}

fun getUserContainer(): UserContainer {
    return UserContainer()
}