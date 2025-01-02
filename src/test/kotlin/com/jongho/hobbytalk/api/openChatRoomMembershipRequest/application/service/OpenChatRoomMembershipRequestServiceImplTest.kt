package com.jongho.hobbytalk.api.openChatRoomMembershipRequest.application.service

import com.jongho.hobbytalk.api.mock.openChatRoomMembershipRequest.OpenChatRoomMembershipRequestBeanKey
import com.jongho.hobbytalk.api.mock.openChatRoomMembershipRequest.OpenChatRoomMembershipRequestContainer
import com.jongho.hobbytalk.api.mock.openChatRoomMembershipRequest.getMemberShipRequestContainer
import com.jongho.hobbytalk.api.mock.openChatRoomMembershipRequest.repository.FakeOpenChatRoomMembershipRequestRepository
import com.jongho.hobbytalk.api.openChatRoomMembershipRequest.command.application.service.OpenChatRoomMembershipRequestServiceImpl
import com.jongho.hobbytalk.api.openChatRoomMembershipRequest.command.common.exception.DuplicateRequestException
import com.jongho.hobbytalk.api.openChatRoomMembershipRequest.command.common.exception.RequestLimitExceededException
import com.jongho.hobbytalk.api.openChatRoomMembershipRequest.command.domain.model.MembershipRequestStatus
import com.jongho.hobbytalk.api.openChatRoomMembershipRequest.command.domain.model.OpenChatRoomMembershipRequest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.context.annotation.Description
import kotlin.test.assertEquals

@Description("OpenChatRoomMembershipRequestServiceImpl 클래스")
class OpenChatRoomMembershipRequestServiceImplTest {
    private val openChatRoomMembershipRequestServiceImpl: OpenChatRoomMembershipRequestServiceImpl
    private val openChatRoomMembershipRequestRepository: FakeOpenChatRoomMembershipRequestRepository
    private val membershipRequestContainer: OpenChatRoomMembershipRequestContainer =
        getMemberShipRequestContainer()

    init {
        openChatRoomMembershipRequestServiceImpl = membershipRequestContainer.get(
            key = OpenChatRoomMembershipRequestBeanKey.OPEN_CHAT_ROOM_MEMBERSHIP_REQUEST_SERVICE)
        openChatRoomMembershipRequestRepository = membershipRequestContainer.get(
            key = OpenChatRoomMembershipRequestBeanKey.OPEN_CHAT_ROOM_MEMBERSHIP_REQUEST_REPOSITORY)
    }

    @Nested
    @Description("request 메소드는")
    inner class RequestMethod {
        @BeforeEach
        fun cleanUp() {
            openChatRoomMembershipRequestRepository.cleanUp()
        }

        @Test
        @Description("중복 신청을 할 경우 이미 요청한 채팅방 입니다. 라는 메세지와 함께 예외를 던진다.")
        fun 중복_신청을_할_경우_이미_요청한_채팅방_입니다_라는_메세지와_함께_예외를_던진다(){
            //given
            val request = OpenChatRoomMembershipRequest(
                id = 0L,
                requesterId = 1L,
                openChatRoomId = 1L,
                message = "방에 참가하고 싶습니다.",
                status = MembershipRequestStatus.PARTICIPATION_REQUEST
            )
            val expectedMessage = "이미 요청한 채팅방 입니다.: ${request.openChatRoomId}"
            openChatRoomMembershipRequestRepository.save(openChatRoomMembershipRequest = request)

            //when
            val actual = assertThrows<DuplicateRequestException> { openChatRoomMembershipRequestServiceImpl
                .request(openChatRoomMembershipRequest = request) }

            //then
            assertEquals(expectedMessage, actual.message)
        }

        @Test
        @Description("신청서를 5회이상 요청을 보낼 경우 5회 이상 참가요청 할 수 없습니다 라는 메세지와 함께 예외를 던진다.")
        fun 신청서를_5회이상_요청을_보낼_경우_5회_이상_참가요청_할_수_없습니다_라는_메세지와_함께_예외를_던진다(){
            //given
            val request = OpenChatRoomMembershipRequest(
                id = 0L,
                requesterId = 1L,
                openChatRoomId = 10L,
                message = "방에 참가하고 싶습니다.",
                status = MembershipRequestStatus.PARTICIPATION_REQUEST
            )
            val expectedMessage = "5회 이상 참가요청 할 수 없습니다."
            for(i in 0..4) {
                openChatRoomMembershipRequestRepository.save(
                    openChatRoomMembershipRequest = request.copy(openChatRoomId = i+1L ))
            }

            //when
            val actual = assertThrows<RequestLimitExceededException> { openChatRoomMembershipRequestServiceImpl
                .request(openChatRoomMembershipRequest = request) }

            //then
            assertEquals(expectedMessage, actual.message)
        }

        @Test
        @Description("정상적인 요청인 경우 신청서를 저장하고 id를 반환 받는다.")
        fun 정상적인_요청인_경우_신청서를_저장하고_id를_반환_받는다(){
            //given
            val request = OpenChatRoomMembershipRequest(
                id = 0L,
                requesterId = 1L,
                openChatRoomId = 10L,
                message = "방에 참가하고 싶습니다.",
                status = MembershipRequestStatus.PARTICIPATION_REQUEST
            )
            val expectedId = 1L

            //when
            val actual = openChatRoomMembershipRequestServiceImpl.request(openChatRoomMembershipRequest = request)

            //then
            assertEquals(expectedId, actual)
        }
    }
}