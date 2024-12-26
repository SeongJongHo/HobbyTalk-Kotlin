package com.jongho.hobbytalk.api.user.application.usecase

import com.jongho.hobbytalk.api.mock.user.UserBeanKey
import com.jongho.hobbytalk.api.mock.user.UserContainer
import com.jongho.hobbytalk.api.mock.user.getUserContainer
import com.jongho.hobbytalk.api.mock.user.repository.FakeUserRepositoryImpl
import com.jongho.hobbytalk.api.user.command.application.dto.request.SignUpUser
import com.jongho.hobbytalk.api.user.command.application.usecase.SignUpUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.context.annotation.Description
import kotlin.test.assertEquals

@Description("SignUpUseCase 클래스")
class SignUpUseCaseImplTest {
    private val signUpUseCase: SignUpUseCase
    private val userContainer: UserContainer = getUserContainer()

    init {
        signUpUseCase = userContainer.get(UserBeanKey.SIGN_UP_USE_CASE)
    }

    @Nested
    @Description("execute 메소드는")
    inner class ExecuteMethod{
        private lateinit var signUpUser: SignUpUser

        @BeforeEach
        fun cleanUp() {

            userContainer.get<FakeUserRepositoryImpl>(UserBeanKey.USER_REPOSITORY).clenUp()
        }

        @Test
        @Description("회원가입에 성공할 경우 유저가 저장이 되고 id값을 반환 받는다.")
        fun 회원가입에_성공할_경우_유저가_저장이_되고_id값을_반환_받는다() {
            //given
            signUpUser = SignUpUser(
                username = "종호",
                phoneNumber = "01012341234",
                password = "1234",
                nickname = "종호",
                profileImage = null)
            val expectedId = 1L

            //when
            val actualId = signUpUseCase.execute(signUpUser)

            //then
            assertEquals(expectedId, actualId)
        }
    }
}