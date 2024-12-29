package com.jongho.hobbytalk.api.category.command.application.repository

import com.jongho.hobbytalk.api.category.command.domain.model.Category

interface CategoryRepository {
    fun findOneById(id: Long): Category?
}