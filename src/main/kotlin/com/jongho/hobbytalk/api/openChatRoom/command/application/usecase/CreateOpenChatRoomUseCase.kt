package com.jongho.hobbytalk.api.openChatRoom.command.application.usecase

import com.jongho.hobbytalk.api.category.command.application.service.CategoryService
import com.jongho.hobbytalk.api.common.exception.LockAcquisitionException
import com.jongho.hobbytalk.api.common.exception.NotFoundException
import com.jongho.hobbytalk.api.common.lock.service.LockService
import com.jongho.hobbytalk.api.common.redis.service.RedisKey
import com.jongho.hobbytalk.api.openChatRoom.command.application.dto.request.CreateOpenChatRoom
import com.jongho.hobbytalk.api.openChatRoom.command.application.service.OpenChatRoomService
import com.jongho.hobbytalk.api.openChatRoomUser.command.application.service.OpenChatRoomUserService
import com.jongho.hobbytalk.api.openChatRoomUser.command.domain.model.OpenChatRoomUser
import org.springframework.transaction.annotation.Transactional

class CreateOpenChatRoomUseCase(
    private val categoryService: CategoryService,
    private val lockService: LockService,
    private val openChatRoomService: OpenChatRoomService,
    private val openChatRoomUserService: OpenChatRoomUserService
) {
    @Transactional
    fun execute(userId: Long, createOpenChatRoom: CreateOpenChatRoom): Long {
        validateCategory(createOpenChatRoom.categoryId)
        val roomId = createRoomWithLock(userId, createOpenChatRoom)
        saveUserRoomData(userId, roomId)

        return roomId
    }

    private fun validateCategory(categoryId: Long) {
        if (categoryService.findOneById(id = categoryId) == null) {
            throw NotFoundException("존재하지 않는 카테고리입니다.")
        }
    }

    private fun createRoomWithLock(userId: Long, createOpenChatRoom: CreateOpenChatRoom): Long {
        val maxSpin = 10
        var spin = 0

        while (spin < maxSpin) {
            if (lockService.acquireLock(userId, RedisKey.OPEN_CHAT_ROOM_LIMIT)) {
                try {
                    return openChatRoomService.createOpenChatRoom(createOpenChatRoom.toModel(managerId = userId))
                } finally {
                    lockService.releaseLock(userId, RedisKey.OPEN_CHAT_ROOM_LIMIT)
                }
            }
            spin++
        }

        throw LockAcquisitionException("락 획득에 실패하였습니다 userId=$userId")
    }

    private fun saveUserRoomData(userId: Long, roomId: Long) {
        openChatRoomUserService.save(
            OpenChatRoomUser(
                openChatRoomId = roomId,
                userId = userId,
                lastExitTime = null,
            )
        )
    }
}