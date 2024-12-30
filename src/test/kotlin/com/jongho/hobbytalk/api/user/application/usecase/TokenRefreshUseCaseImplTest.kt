package com.jongho.hobbytalk.api.user.application.usecase

import com.jongho.hobbytalk.api.mock.common.util.FakeTokenUtilImpl
import com.jongho.hobbytalk.api.mock.user.UserBeanKey
import com.jongho.hobbytalk.api.mock.user.UserContainer
import com.jongho.hobbytalk.api.mock.user.getUserContainer
import com.jongho.hobbytalk.api.mock.user.repository.FakeAuthUserRepositoryImpl
import com.jongho.hobbytalk.api.user.command.application.dto.response.TokenResponse
import com.jongho.hobbytalk.api.user.command.application.usecase.TokenRefreshUseCase
import com.jongho.hobbytalk.api.common.exception.UnAuthorizedException
import com.jongho.hobbytalk.api.user.command.common.exception.UserNotFoundException
import com.jongho.hobbytalk.api.user.command.domain.model.AuthUser
import org.junit.jupiter.api.*
import org.springframework.context.annotation.Description
import kotlin.test.assertEquals

@Description("TokenRefreshUseCase 클래스")
class TokenRefreshUseCaseImplTest {
    private val tokenRefreshUseCase: TokenRefreshUseCase;
    private val userContainer: UserContainer = getUserContainer()

    init {
        tokenRefreshUseCase = userContainer.get(UserBeanKey.TOKEN_REFRESH_USE_CASE)
    }

    @Nested
    @Description("execute 메소드는")
    inner class ExecuteMethod{
        private val authUserRepository = userContainer.get<FakeAuthUserRepositoryImpl>(UserBeanKey.AUTH_USER_REPOSITORY)
        private val tokenUtil = FakeTokenUtilImpl()
        private var authUser = AuthUser(userId = 1L, refreshToken = tokenUtil.createRefreshToken(1L))
        @BeforeEach
        fun setUp() {
            authUser = AuthUser(userId = 1L, refreshToken = tokenUtil.createRefreshToken(1L))
            authUserRepository.save(
                authUser = authUser
            )
        }

        @AfterEach
        fun cleanUp() {
            authUserRepository.cleanUp()
        }

        @Test
        @Description("토큰 재발급에 성공할 경우 인증유저가 업데이트 되고 A-T, R-T를 가진 TokenResponse를 반환 받는다.")
        fun 토큰_재발급에_성공할_경우_인증유저가_업데이트_되고_TokenResponse를_반환_받는다() {
            //given
            val expectedTokenResponse = TokenResponse(
                accessToken = tokenUtil.createAccessToken(1L),
                refreshToken = tokenUtil.createRefreshToken(1L)
            )
            //when
            val actual = tokenRefreshUseCase.execute(userId = authUser.userId, refreshToken = authUser.refreshToken!!)

            //then
            assertEquals(expectedTokenResponse.accessToken, actual.accessToken)
            assertEquals(expectedTokenResponse.refreshToken, actual.refreshToken)
        }

        @Test
        @Description("존재하지 않는 유저가 토큰 갱신을 시도할 경우 UserNotFoundException과 존재하지 않는 유저입니다.메세지를 던진다.")
        fun 존재하지_않는_유저가_토큰_갱신을_시도할_경우_UserNotFoundException과_존재하지_않는_유저입니다_메세지를_던진다() {
            //given
            authUser = AuthUser(
                userId = 2L,
                refreshToken = null
            )
            val expected = UserNotFoundException("존재하지 않는 유저입니다.")

            //when
            val actual = assertThrows<UserNotFoundException>{ tokenRefreshUseCase.execute(authUser.userId, refreshToken = "") }

            //then
            assertEquals(expected.message, actual.message)
        }

        @Test
        @Description("리프레시 토큰이 유효하지 않을 경우 UnAuthorizedException과 유효하지 않는 토큰입니다.메세지를 던진다.")
        fun 리프레시_토큰이_유효하지_않을_경우_UserNotFoundException과_유효하지_않는_토큰입니다_메세지를_던진다() {
            //given
            authUser = AuthUser(
                userId = 1L,
                refreshToken = "null"
            )
            val expected = UnAuthorizedException("유효하지 않는 토큰입니다.")

            //when
            val actual = assertThrows<UnAuthorizedException>{ tokenRefreshUseCase.execute(authUser.userId, refreshToken = authUser.refreshToken!!) }

            //then
            assertEquals(expected.message, actual.message)
        }
    }
}