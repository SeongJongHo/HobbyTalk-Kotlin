package com.jongho.hobbytalk.api.user.application.usecase

import com.jongho.hobbytalk.api.mock.common.util.FakeTokenUtilImpl
import com.jongho.hobbytalk.api.mock.user.UserBeanKey
import com.jongho.hobbytalk.api.mock.user.UserContainer
import com.jongho.hobbytalk.api.mock.user.getUserContainer
import com.jongho.hobbytalk.api.mock.user.repository.FakeAuthUserRepositoryImpl
import com.jongho.hobbytalk.api.mock.user.repository.FakeUserRepositoryImpl
import com.jongho.hobbytalk.api.user.command.application.dto.request.SignInUser
import com.jongho.hobbytalk.api.user.command.application.dto.response.TokenResponse
import com.jongho.hobbytalk.api.user.command.application.usecase.SignInUseCase
import com.jongho.hobbytalk.api.user.command.common.exception.UnAuthorizedException
import com.jongho.hobbytalk.api.user.command.common.exception.UserNotFoundException
import com.jongho.hobbytalk.api.user.command.domain.model.User
import org.junit.jupiter.api.*
import org.springframework.context.annotation.Description
import java.time.LocalDateTime
import kotlin.test.assertEquals

@Description("SignInUseCase 클래스")
class SignInUseCaseImplTest {
    private val signInUseCase: SignInUseCase
    private val userContainer: UserContainer = getUserContainer()

    init {
        signInUseCase = userContainer.get(UserBeanKey.SIGN_IN_USE_CASE)
    }

    @Nested
    @Description("execute 메소드는")
    inner class ExecuteMethod{
        private lateinit var signInUser: SignInUser
        private val userRepository = userContainer.get<FakeUserRepositoryImpl>(UserBeanKey.USER_REPOSITORY)
        private val authUserRepository = userContainer.get<FakeAuthUserRepositoryImpl>(UserBeanKey.AUTH_USER_REPOSITORY)
        private val tokenUtil = FakeTokenUtilImpl()
        @BeforeEach
        fun setUp() {
            userRepository.save(
                User(
                    id = 0L,
                    username = "종호",
                    phoneNumber = "01012341234",
                    password = "1234",
                    nickname = "종호",
                    profileImage = null,
                    createdTime = LocalDateTime.now(),
                    isDeleted = false,
                    deletedTime = null,
                )
            )
        }

        @AfterEach
        fun cleanUp() {
            userRepository.clenUp()
            authUserRepository.clenUp()
        }

        @Test
        @Description("로그인에 성공할 경우 유저가 저장이 되고 A-T, R-T를 가진 TokenResponse를 반환 받는다.")
        fun 로그인에_성공할_경우_유저가_저장이_되고_TokenResponse를_반환_받는다() {
            //given
            signInUser = SignInUser(
                username = "종호",
                password = "1234")
            val expectedTokenResponse = TokenResponse(
                accessToken = tokenUtil.createAccessToken(1L),
                refreshToken = tokenUtil.createRefreshToken(1L)
            )
            //when
            val actual = signInUseCase.execute(signInUser)

            //then
            assertEquals(expectedTokenResponse.accessToken, actual.accessToken)
            assertEquals(expectedTokenResponse.refreshToken, actual.refreshToken)
        }

        @Test
        @Description("존재하지 않는 유저가 로그인을 시도할 경우 UserNotFoundException과 존재하지 않는 유저입니다.메세지를 던진다.")
        fun 존재하지_않는_유저가_로그인을_시도할_경우_UserNotFoundException과_존재하지_않는_유저입니다_메세지를_던진다() {
            //given
            signInUser = SignInUser(
                username = "종호1",
                password = "1234")
            val expected = UserNotFoundException("존재하지 않는 유저입니다.")

            //when
            val actual = assertThrows<UserNotFoundException>{ signInUseCase.execute(signInUser) }

            //then
            assertEquals(expected.message, actual.message)
        }

        @Test
        @Description("로그인을 시도한 유저가 비밀번호가 틀릴경우 UnAuthorizedException과 비밀번호가 틀렸습니다.메세지를 던진다.")
        fun 로그인을_시도한_유저가_비밀번호가_틀릴경우_UnAuthorizedException과_비밀번호가_틀렸습니다_메세지를_던진다() {
            //given
            signInUser = SignInUser(
                username = "종호",
                password = "12345")
            val expected = UnAuthorizedException("비밀번호가 틀렸습니다.")

            //when
            val actual = assertThrows<UnAuthorizedException>{ signInUseCase.execute(signInUser) }

            //then
            assertEquals(expected.message, actual.message)
        }
    }
}