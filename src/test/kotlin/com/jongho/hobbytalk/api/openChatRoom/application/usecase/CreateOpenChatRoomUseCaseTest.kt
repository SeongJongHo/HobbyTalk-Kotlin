package com.jongho.hobbytalk.api.openChatRoom.application.usecase

import com.jongho.hobbytalk.api.category.command.domain.model.Category
import com.jongho.hobbytalk.api.common.exception.LockAcquisitionException
import com.jongho.hobbytalk.api.common.exception.NotFoundException
import com.jongho.hobbytalk.api.mock.category.CategoryBeanKey
import com.jongho.hobbytalk.api.mock.category.getCategoryContainer
import com.jongho.hobbytalk.api.mock.category.repository.FakeCategoryRepositoryImpl
import com.jongho.hobbytalk.api.mock.common.CommonBeanKey
import com.jongho.hobbytalk.api.mock.common.getCommonContainer
import com.jongho.hobbytalk.api.mock.common.lock.FakeLockRepositoryImpl
import com.jongho.hobbytalk.api.mock.openChatRoom.OpenChatRoomBeanKey
import com.jongho.hobbytalk.api.mock.openChatRoom.getOpenChatRoomContainer
import com.jongho.hobbytalk.api.mock.openChatRoom.repository.FakeOpenChatRoomRepositoryImpl
import com.jongho.hobbytalk.api.mock.openChatRoomUser.OpenChatRoomUserBeanKey
import com.jongho.hobbytalk.api.mock.openChatRoomUser.getOpenChatRoomUserContainer
import com.jongho.hobbytalk.api.mock.openChatRoomUser.repository.FakeOpenChatRoomUserRepositoryImpl
import com.jongho.hobbytalk.api.openChatRoom.command.application.dto.request.CreateOpenChatRoom
import com.jongho.hobbytalk.api.openChatRoom.command.application.usecase.CreateOpenChatRoomUseCase
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.assertThrows
import org.springframework.context.annotation.Description
import kotlin.test.Test

@Description("CreateOpenChatRoomUseCase 클래스")
class CreateOpenChatRoomUseCaseTest {
    private val createOpenChatRoomUseCase: CreateOpenChatRoomUseCase
    private val openChatRoomContainer = getOpenChatRoomContainer()
    private val openChatRoomUserContainer = getOpenChatRoomUserContainer()
    private val commonContainer = getCommonContainer()
    private val categoryContainer = getCategoryContainer()

    init {
        createOpenChatRoomUseCase = openChatRoomContainer.get(OpenChatRoomBeanKey.CREATE_OPEN_CHAT_ROOM_USE_CASE)
    }

    @Nested
    @Description("execute 메소드는")
    inner class ExecuteMethod {
        private lateinit var createOpenChatRoom: CreateOpenChatRoom
        private val categoryRepository = categoryContainer.get<FakeCategoryRepositoryImpl>(CategoryBeanKey.CATEGORY_REPOSITORY)
        private val lockRepository = commonContainer.get<FakeLockRepositoryImpl>(CommonBeanKey.LOCK_REPOSITORY)
        private val openChatRoomRepository = openChatRoomContainer.get<FakeOpenChatRoomRepositoryImpl>(OpenChatRoomBeanKey.OPEN_CHAT_ROOM_REPOSITORY)
        private val openChatRoomUserRepository = openChatRoomUserContainer.get<FakeOpenChatRoomUserRepositoryImpl>(
            OpenChatRoomUserBeanKey.OPEN_CHAT_ROOM_USER_REPOSITORY)

        @BeforeEach
        fun setUp() {
            categoryRepository.save(Category(id = 0L, name = "개발", parentId = 0L))
        }

        @AfterEach
        fun cleanUp() {
            categoryRepository.cleanUp()
            lockRepository.cleanUp()
            openChatRoomRepository.cleanUp()
            openChatRoomUserRepository.cleanUp()
        }

        @Test
        @Description("카테고리가 유효하고 락을 성공적으로 획득하면 방을 생성하고 방 ID를 반환한다.")
        fun 카테고리가_유효하고_락을_성공적으로_획득하면_방을_생성하고_방_ID를_반환한다() {
            // given
            val expectedId = 1L
            createOpenChatRoom = CreateOpenChatRoom(
                title = "타이틀",
                notice = "공지",
                categoryId = 1L,
                maximumCapacity = 10,
                password = ""
            )

            // when
            val roomId = createOpenChatRoomUseCase.execute(userId = 1L, createOpenChatRoom = createOpenChatRoom)

            // then
            assertEquals(expectedId, roomId)
        }

        @Test
        @Description("존재하지 않는 카테고리로 방을 생성하려 하면 NotFoundException을 던진다.")
        fun 존재하지_않는_카테고리로_방을_생성하려_하면_NotFoundException을_던진다() {
            // given
            val expectedMessage = "존재하지 않는 카테고리입니다."
            createOpenChatRoom = CreateOpenChatRoom(
                title = "타이틀",
                notice = "공지",
                categoryId = 2L,
                maximumCapacity = 10,
                password = ""
            )

            // when
            val exception = assertThrows<NotFoundException> {
                createOpenChatRoomUseCase.execute(userId = 1L, createOpenChatRoom = createOpenChatRoom)
            }

            // then
            assertEquals(expectedMessage, exception.message)
        }

        @Test
        @Description("락 획득에 실패하면 LockAcquisitionException을 던진다.")
        fun 락_획득에_실패하면_LockAcquisitionException을_던진다() {
            // given
            val userId = 1L
            val expectedMessage = "락 획득에 실패하였습니다 userId=$userId"
            createOpenChatRoom = CreateOpenChatRoom(
                title = "타이틀",
                notice = "공지",
                categoryId = 1L,
                maximumCapacity = 10,
                password = ""
            )
            lockRepository.acquireLock(key = "openChatRoomLimit:userId:$userId")

            // when
            val exception = assertThrows<LockAcquisitionException> {
                createOpenChatRoomUseCase.execute(userId = userId, createOpenChatRoom = createOpenChatRoom)
            }

            // then
            assertEquals(expectedMessage, exception.message)
        }
    }
}
