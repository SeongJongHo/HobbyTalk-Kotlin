package com.jongho.hobbytalk.api.category.command.application.service

import com.jongho.hobbytalk.api.category.command.domain.model.Category

interface CategoryService {
    fun findOneById(id: Long): Category?
}