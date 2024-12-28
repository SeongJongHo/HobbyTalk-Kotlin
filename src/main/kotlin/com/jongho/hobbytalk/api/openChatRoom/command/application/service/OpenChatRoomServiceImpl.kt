package com.jongho.hobbytalk.api.openChatRoom.command.application.service

import com.jongho.hobbytalk.api.common.exception.AlreadyExistsException
import com.jongho.hobbytalk.api.openChatRoom.command.application.repository.OpenChatRoomRepository
import com.jongho.hobbytalk.api.openChatRoom.command.common.exception.MaxChatRoomsExceededException
import com.jongho.hobbytalk.api.openChatRoom.command.domain.model.OpenChatRoom

class OpenChatRoomServiceImpl(private val openChatRoomRepository: OpenChatRoomRepository): OpenChatRoomService {
    private val MAXIMUM_OPEN_CHAT_ROOM_COUNT = 5

    override fun createOpenChatRoom(openChatRoom: OpenChatRoom): Long {
        if(openChatRoomRepository.countByManagerId(
                managerId = openChatRoom.managerId) >= MAXIMUM_OPEN_CHAT_ROOM_COUNT) {
            throw MaxChatRoomsExceededException("최대 개설 가능한 채팅방 개수를 초과하였습니다.")
        }
        if(openChatRoomRepository.findOneByManagerIdAndTitle(
                managerId = openChatRoom.managerId,
                title = openChatRoom.title) != null) {
            throw AlreadyExistsException("이미 존재하는 채팅방입니다.")
        }

        return openChatRoomRepository.save(openChatRoom)
    }
}