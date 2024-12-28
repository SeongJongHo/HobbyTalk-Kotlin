package com.jongho.hobbytalk.api.openChatRoom.application.service

import com.jongho.hobbytalk.api.common.exception.AlreadyExistsException
import com.jongho.hobbytalk.api.mock.openChatRoom.OpenChatRoomBeanKey
import com.jongho.hobbytalk.api.mock.openChatRoom.OpenChatRoomContainer
import com.jongho.hobbytalk.api.mock.openChatRoom.getOpenChatRoomContainer
import com.jongho.hobbytalk.api.mock.openChatRoom.repository.FakeOpenChatRoomRepositoryImpl
import com.jongho.hobbytalk.api.openChatRoom.command.application.service.OpenChatRoomServiceImpl
import com.jongho.hobbytalk.api.openChatRoom.command.common.exception.MaxChatRoomsExceededException
import com.jongho.hobbytalk.api.openChatRoom.command.domain.model.OpenChatRoom
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.context.annotation.Description
import kotlin.test.assertEquals

@Description("OpenChatRoomServiceImpl 클래스")
class OpenChatRoomServiceImplTest {
    private val openChatRoomServiceImpl: OpenChatRoomServiceImpl
    private val openChatRoomContainer: OpenChatRoomContainer = getOpenChatRoomContainer()

    init {
        openChatRoomServiceImpl = openChatRoomContainer.get(OpenChatRoomBeanKey.OPEN_CHAT_ROOM_SERVICE)
    }

    @Nested
    @Description("createOpenChatRoom 메소드는")
    inner class CreateOpenChatRoomMethod{
        var openChatRoom = OpenChatRoom(
            id = 0L,
            title = "title",
            notice = "notice",
            managerId = 1L,
            categoryId = 0L,
            maximumCapacity = 100,
            currentAttendance = 0,
            password = "password"
        )

        @BeforeEach
        fun cleanUp() {
            openChatRoomContainer.get<FakeOpenChatRoomRepositoryImpl>(OpenChatRoomBeanKey.OPEN_CHAT_ROOM_REPOSITORY).cleanUp()
        }

        @Test
        @Description("생성갯수 제한인 5를 초과할 경우 MaxChatRoomsExceededException에 최대 개설 가능한 채팅방 개수를 초과하였습니다. 라는 메세지를 반환한다.")
        fun 생성갯수_제한인_5를_초과할_경우_예외와_최대_개설_가능한_채팅방_개수를_초과하였습니다_라는_메세지를_반환한다(){
            //given
            val expectedMessage = "최대 개설 가능한 채팅방 개수를 초과하였습니다."
            for(i in 0..4) {
                openChatRoomServiceImpl.createOpenChatRoom(
                    openChatRoom = openChatRoom.copy(title = openChatRoom.title + i))
            }
            //when
            val exception = assertThrows <MaxChatRoomsExceededException> {
                openChatRoomServiceImpl.createOpenChatRoom(openChatRoom = openChatRoom) }

            //then
            assertEquals(expectedMessage, exception.message)
        }

        @Test
        @Description("중복된 타이틀로 생성을 시도할 경우 AlreadyExistsException에 이미 존재하는 채팅방입니다. 라는 메세지를 반환한다.")
        fun 중복된_타이틀로_생성을_시도할_경우_AlreadyExistsException에_이미_존재하는_채팅방입니다_라는_메세지를_반환한다(){
            //given
            val expectedMessage = "이미 존재하는 채팅방입니다."
                openChatRoomServiceImpl.createOpenChatRoom(openChatRoom = openChatRoom)

            //when
            val exception = assertThrows <AlreadyExistsException> {
                openChatRoomServiceImpl.createOpenChatRoom(openChatRoom = openChatRoom) }

            //then
            assertEquals(expectedMessage, exception.message)
        }

        @Test
        @Description("채팅방 생성에 성공할 경우 저장이 되고 id값을 반환 받는다.")
        fun 채팅방_생성에_성공할_경우_저장이_되고_id값을_반환_받는다() {
            //given
            val expectedId = 1L

            //when
            val actualId = openChatRoomServiceImpl.createOpenChatRoom(openChatRoom = openChatRoom)

            //then
            assertEquals(expectedId, actualId)
        }
    }
}