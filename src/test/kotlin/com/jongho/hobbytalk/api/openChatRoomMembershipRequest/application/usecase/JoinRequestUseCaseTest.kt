package com.jongho.hobbytalk.api.openChatRoomMembershipRequest.application.usecase

import com.jongho.hobbytalk.api.common.exception.AlreadyExistsException
import com.jongho.hobbytalk.api.common.exception.ChatRoomLimitReachedException
import com.jongho.hobbytalk.api.common.exception.NotFoundException
import com.jongho.hobbytalk.api.mock.openChatRoom.OpenChatRoomBeanKey
import com.jongho.hobbytalk.api.mock.openChatRoom.OpenChatRoomContainer
import com.jongho.hobbytalk.api.mock.openChatRoom.getOpenChatRoomContainer
import com.jongho.hobbytalk.api.mock.openChatRoom.repository.FakeOpenChatRoomRepositoryImpl
import com.jongho.hobbytalk.api.mock.openChatRoomMembershipRequest.OpenChatRoomMembershipRequestBeanKey
import com.jongho.hobbytalk.api.mock.openChatRoomMembershipRequest.OpenChatRoomMembershipRequestContainer
import com.jongho.hobbytalk.api.mock.openChatRoomMembershipRequest.getMemberShipRequestContainer
import com.jongho.hobbytalk.api.mock.openChatRoomMembershipRequest.repository.FakeOpenChatRoomMembershipRequestRepository
import com.jongho.hobbytalk.api.mock.openChatRoomUser.OpenChatRoomUserBeanKey
import com.jongho.hobbytalk.api.mock.openChatRoomUser.getOpenChatRoomUserContainer
import com.jongho.hobbytalk.api.mock.openChatRoomUser.repository.FakeOpenChatRoomUserRepositoryImpl
import com.jongho.hobbytalk.api.openChatRoom.command.domain.model.OpenChatRoom
import com.jongho.hobbytalk.api.openChatRoomMembershipRequest.command.application.service.OpenChatRoomMembershipRequestServiceImpl
import com.jongho.hobbytalk.api.openChatRoomMembershipRequest.command.application.usecase.JoinRequestUseCase
import com.jongho.hobbytalk.api.openChatRoomMembershipRequest.command.common.exception.DuplicateRequestException
import com.jongho.hobbytalk.api.openChatRoomMembershipRequest.command.common.exception.RequestLimitExceededException
import com.jongho.hobbytalk.api.openChatRoomMembershipRequest.command.domain.model.MembershipRequestStatus
import com.jongho.hobbytalk.api.openChatRoomMembershipRequest.command.domain.model.OpenChatRoomMembershipRequest
import com.jongho.hobbytalk.api.openChatRoomUser.command.domain.model.OpenChatRoomUser
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.context.annotation.Description
import kotlin.test.assertEquals

class JoinRequestUseCaseTest {
    private val openChatRoomUserContainer = getOpenChatRoomUserContainer()
    private val openChatRoomContainer = getOpenChatRoomContainer()
    private val membershipRequestContainer: OpenChatRoomMembershipRequestContainer =
        getMemberShipRequestContainer()
    private val joinRequestUseCase: JoinRequestUseCase =
        membershipRequestContainer.get(
            key = OpenChatRoomMembershipRequestBeanKey.JOIN_REQUEST_USE_CASE)
    private val openChatRoomMembershipRequestRepository: FakeOpenChatRoomMembershipRequestRepository =
        membershipRequestContainer.get(
            key = OpenChatRoomMembershipRequestBeanKey.OPEN_CHAT_ROOM_MEMBERSHIP_REQUEST_REPOSITORY
        )
    private val openChatRoomRepository: FakeOpenChatRoomRepositoryImpl = openChatRoomContainer.get(
        key = OpenChatRoomBeanKey.OPEN_CHAT_ROOM_REPOSITORY)
    private val openChatRoomUserRepository: FakeOpenChatRoomUserRepositoryImpl = openChatRoomUserContainer.get(
        key = OpenChatRoomUserBeanKey.OPEN_CHAT_ROOM_USER_REPOSITORY)

    @Nested
    @Description("execute 메소드는")
    inner class ExecuteMethod {
        lateinit var request: OpenChatRoomMembershipRequest
        lateinit var room: OpenChatRoom
        lateinit var roomUser: OpenChatRoomUser

        @BeforeEach
        fun cleanUp() {
            openChatRoomMembershipRequestRepository.cleanUp()
            openChatRoomRepository.cleanUp()
            openChatRoomUserRepository.cleanUp()
            request = OpenChatRoomMembershipRequest(
                id = 0L,
                requesterId = 1L,
                openChatRoomId = 1L,
                message = "방에 참가하고 싶습니다.",
                status = MembershipRequestStatus.PARTICIPATION_REQUEST
            )
            room = OpenChatRoom(
                id = 0L,
                title = "타이틀",
                notice = "공지",
                managerId = 1L,
                categoryId = 1L,
                maximumCapacity = 5,
                currentAttendance = 4,
                password = ""
            )
            roomUser = OpenChatRoomUser(
                openChatRoomId = 1L,
                userId = 1L,
                lastExitTime = null
            )
        }

        @Test
        @Description("존재하지 않는 채팅방에 신청할 경우 NotFoundException 예외를 던진다.")
        fun 존재하지_않는_채팅방에_신청할_경우_NotFoundException_예외를_던진다(){
            //given
            val expectedMessage = "존재하지 않는 채팅방입니다.: ${request.openChatRoomId}"

            //when
            val actual = assertThrows<NotFoundException> { joinRequestUseCase.execute(
                requesterId = request.requesterId,
                roomId = request.openChatRoomId,
                message = request.message
            ) }

            //then
            assertEquals(expectedMessage, actual.message)
        }

        @Test
        @Description("채팅방이 가득 차서 더이상 신청을 못하면 ChatRoomLimitReachedException 예외를 던진다.")
        fun 채팅방이_가득_차서_더이상_신청을_못하면_ChatRoomLimitReachedException_예외를_던진다(){
            //given
            val expectedMessage = "더이상 참여할 수 없는 채팅방입니다.: ${request.openChatRoomId}"
            openChatRoomRepository.save(openChatRoom = room.copy(currentAttendance = 5))

            //when
            val actual = assertThrows<ChatRoomLimitReachedException> { joinRequestUseCase.execute(
                requesterId = request.requesterId,
                roomId = request.openChatRoomId,
                message = request.message
            ) }

            //then
            assertEquals(expectedMessage, actual.message)
        }

        @Test
        @Description("참여중인 채팅방에 신청할 경우 AlreadyExistsException 예외를 던진다.")
        fun 참여중인_채팅방에_신청할_경우_AlreadyExistsException_예외를_던진다(){
            //given
            val expectedMessage = "이미 참여중인 채팅방입니다."
            openChatRoomRepository.save(openChatRoom = room)
            openChatRoomUserRepository.save(openChatRoomUser = roomUser)

            //when
            val actual = assertThrows<AlreadyExistsException> { joinRequestUseCase.execute(
                requesterId = request.requesterId,
                roomId = request.openChatRoomId,
                message = request.message
            ) }

            //then
            assertEquals(expectedMessage, actual.message)
        }

        @Test
        @Description("정상적인 요청인 경우 신청서를 저장하고 id를 반환 받는다.")
        fun 정상적인_요청인_경우_신청서를_저장하고_id를_반환_받는다(){
            //given
            val expectedId = 1L
            openChatRoomRepository.save(openChatRoom = room)

            //when
            val actual = joinRequestUseCase.execute(
                requesterId = request.requesterId,
                roomId = request.openChatRoomId,
                message = request.message
            )

            //then
            assertEquals(expectedId, actual)
        }
    }
}