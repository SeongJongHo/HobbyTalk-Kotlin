package com.jongho.hobbytalk.api.mock.openChatRoom.repository

import com.jongho.hobbytalk.api.openChatRoom.command.application.repository.OpenChatRoomRepository
import com.jongho.hobbytalk.api.openChatRoom.command.domain.model.OpenChatRoom
import java.util.concurrent.atomic.AtomicLong

class FakeOpenChatRoomRepositoryImpl: OpenChatRoomRepository {
    private var openChatRoomList: MutableList<OpenChatRoom> = mutableListOf()
    private var id: AtomicLong = AtomicLong(0)

    override fun countByManagerId(managerId: Long): Int {
        return openChatRoomList.count { openChatRoom -> openChatRoom.managerId == managerId }
    }

    override fun findOneByManagerIdAndTitle(managerId: Long, title: String): OpenChatRoom? {
        return openChatRoomList.firstOrNull{
            openChatRoom -> (openChatRoom.managerId == managerId && openChatRoom.title == title ) }
    }

    override fun findOneById(id: Long): OpenChatRoom? {
        return openChatRoomList.firstOrNull{ openChatRoom -> openChatRoom.id == id }
    }

    override fun save(openChatRoom: OpenChatRoom): Long {
        val openChatRoomId: Long
        if(openChatRoom.id != 0L) {
            openChatRoomList[openChatRoomList.indexOfFirst { it -> it.id == openChatRoom.id }] = openChatRoom

            openChatRoomId = openChatRoom.id
        }
        else {
            openChatRoomList.add(openChatRoom.copy(id = id.incrementAndGet()))

            openChatRoomId = id.get()
        }

        return openChatRoomId
    }

    fun cleanUp() {
        openChatRoomList = mutableListOf()
        id = AtomicLong(0)
    }
}