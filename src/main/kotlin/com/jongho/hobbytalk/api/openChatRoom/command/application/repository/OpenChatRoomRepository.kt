package com.jongho.hobbytalk.api.openChatRoom.command.application.repository

import com.jongho.hobbytalk.api.openChatRoom.command.domain.model.OpenChatRoom

interface OpenChatRoomRepository {
    fun countByManagerId(managerId: Long): Int
    fun save(openChatRoom: OpenChatRoom): Long
    fun findOneByManagerIdAndTitle(managerId: Long, title: String): OpenChatRoom?
    fun findOneById(id: Long): OpenChatRoom?
}