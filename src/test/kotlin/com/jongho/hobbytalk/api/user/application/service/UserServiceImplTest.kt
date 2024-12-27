package com.jongho.hobbytalk.api.user.application.service

import com.jongho.hobbytalk.api.mock.user.UserBeanKey
import com.jongho.hobbytalk.api.mock.user.UserContainer
import com.jongho.hobbytalk.api.mock.user.getUserContainer
import com.jongho.hobbytalk.api.mock.user.repository.FakeUserRepositoryImpl
import com.jongho.hobbytalk.api.user.command.application.service.UserServiceImpl
import com.jongho.hobbytalk.api.user.command.common.exception.UserDuplicatedException
import com.jongho.hobbytalk.api.user.command.domain.model.User
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.context.annotation.Description
import java.time.LocalDateTime
import kotlin.test.assertEquals

@Description("UserService 클래스")
class UserServiceImplTest {
    private val userServiceImpl: UserServiceImpl
    private val userContainer: UserContainer  = getUserContainer()

    init {
        userServiceImpl = userContainer.get(UserBeanKey.USER_SERVICE)
    }

    @Nested
    @Description("signUp 메소드는")
    inner class SignUpMethod{
        private lateinit var user: User

        @BeforeEach
        fun cleanUp() {
            user = User(
                id = 0L, username = "종호",
                phoneNumber = "01012341234",
                password = "1234",
                nickname = "종호",
                createdTime = LocalDateTime.now())
            userContainer.get<FakeUserRepositoryImpl>(UserBeanKey.USER_REPOSITORY).cleanUp()
        }

        @Test
        @Description("이미 존재하는 아이디를 회원가입하려고 할 경우 UserDuplicatedException에 이미 존재하는 아이디입니다.라는 메세지를 함께 던진다")
        fun 이미_존재하는_아이디를_회원가입하려고_할_경우_UserDuplicatedException에_이미_존재하는_아이디입니다_라는_메세지를_함께_던진다(){
            //given
            val newUser = user.copy()
            val expectedMessage = "이미 존재하는 아이디입니다."
            userContainer.get<FakeUserRepositoryImpl>(UserBeanKey.USER_REPOSITORY).save(user)

            //when
            val exception = assertThrows <UserDuplicatedException> { userServiceImpl.signUp(newUser) }

            //then
            assertEquals(expectedMessage, exception.message)
        }

        @Test
        @Description("이미 가입된 전화번호로 회원가입하려고 할 경우 UserDuplicatedException에 이미 가입된 전화번호입니다.라는 메세지를 함께 던진다")
        fun 이미_가입된_전화번호로_회원가입하려고_할_경우_UserDuplicatedException에_이미_가입된_전화번호입니다_라는_메세지를_함께_던진다() {
            //given
            val newUser = user.copy(username = "다른 아이디")
            val expectedMessage = "이미 가입된 전화번호입니다."
            userContainer.get<FakeUserRepositoryImpl>(UserBeanKey.USER_REPOSITORY).save(user)

            //when
            val exception = assertThrows<UserDuplicatedException> { userServiceImpl.signUp(newUser) }

            //then
            assertEquals(expectedMessage, exception.message)
        }

        @Test
        @Description("DataIntegrityViolationException 예외가 발생할 경우 UserDuplicatedException예외와 이미 가입된 아이디거나 전화번호입니다.라는 메세지를 함께 던진다")
        fun DataIntegrityViolationException_예외가_발생할_경우_UserDuplicatedException예외와_이미_가입된_아이디거나_전화번호입니다_라는_메세지를_함께_던진다() {
            //given
            val newUser = user.copy(username = "다른 아이디", phoneNumber = "01043214321", nickname = "duplicated")
            val expectedMessage = "이미 가입된 아이디거나 전화번호입니다."
            userContainer.get<FakeUserRepositoryImpl>(UserBeanKey.USER_REPOSITORY).save(user)

            //when
            val exception = assertThrows<UserDuplicatedException> { userServiceImpl.signUp(newUser) }

            //then
            assertEquals(expectedMessage, exception.message)
        }

        @Test
        @Description("회원가입에 성공할 경우 유저가 저장이 되고 id값을 반환 받는다.")
        fun 회원가입에_성공할_경우_유저가_저장이_되고_id값을_반환_받는다() {
            //given
            val newUser = user.copy(username = "다른 아이디", phoneNumber = "01043214321")
            val expectedId = 2L
            userContainer.get<FakeUserRepositoryImpl>(UserBeanKey.USER_REPOSITORY).save(user)

            //when
            val actualId = userServiceImpl.signUp(newUser)

            //then
            assertEquals(expectedId, actualId)
        }
    }
}