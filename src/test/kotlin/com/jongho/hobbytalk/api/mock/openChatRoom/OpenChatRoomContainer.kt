package com.jongho.hobbytalk.api.mock.openChatRoom

import com.jongho.hobbytalk.api.mock.category.CategoryBeanKey
import com.jongho.hobbytalk.api.mock.category.getCategoryContainer
import com.jongho.hobbytalk.api.mock.common.CommonBeanKey
import com.jongho.hobbytalk.api.mock.common.getCommonContainer
import com.jongho.hobbytalk.api.mock.openChatRoom.repository.FakeOpenChatRoomRepositoryImpl
import com.jongho.hobbytalk.api.mock.openChatRoomUser.OpenChatRoomUserBeanKey
import com.jongho.hobbytalk.api.mock.openChatRoomUser.getOpenChatRoomUserContainer
import com.jongho.hobbytalk.api.openChatRoom.command.application.service.OpenChatRoomServiceImpl
import com.jongho.hobbytalk.api.openChatRoom.command.application.usecase.CreateOpenChatRoomUseCase

object OpenChatRoomContainer {
    private val map: MutableMap<String, Any> = HashMap()
    private val commonContainer = getCommonContainer()
    private val openChatRoomUserContainer = getOpenChatRoomUserContainer()
    private val categoryContainer = getCategoryContainer()

    init {
        map[OpenChatRoomBeanKey.OPEN_CHAT_ROOM_REPOSITORY.getValue()] = FakeOpenChatRoomRepositoryImpl()
        map[OpenChatRoomBeanKey.OPEN_CHAT_ROOM_SERVICE.getValue()] = OpenChatRoomServiceImpl(
            this.get(key = OpenChatRoomBeanKey.OPEN_CHAT_ROOM_REPOSITORY)
        )
        map[OpenChatRoomBeanKey.CREATE_OPEN_CHAT_ROOM_USE_CASE.getValue()] = CreateOpenChatRoomUseCase(
            categoryService = categoryContainer.get(key = CategoryBeanKey.CATEGORY_SERVICE),
            lockService = commonContainer.get(key = CommonBeanKey.LOCK_SERVICE),
            openChatRoomService = this.get(key = OpenChatRoomBeanKey.OPEN_CHAT_ROOM_SERVICE),
            openChatRoomUserService = openChatRoomUserContainer.get(key = OpenChatRoomUserBeanKey.OPEN_CHAT_ROOM_USER_SERVICE)
        )
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> get(key: OpenChatRoomBeanKey): T {
        return map[key.getValue()] as T
    }
}

enum class OpenChatRoomBeanKey(private val value: String) {
    OPEN_CHAT_ROOM_REPOSITORY("OpenChatRoomRepository"),
    OPEN_CHAT_ROOM_SERVICE("OpenChatRoomService"),
    CREATE_OPEN_CHAT_ROOM_USE_CASE("CreateOpenChatRoomUseCase");

    fun getValue(): String {
        return value
    }
}

fun getOpenChatRoomContainer(): OpenChatRoomContainer {
    return OpenChatRoomContainer
}