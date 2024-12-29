package com.jongho.hobbytalk.api.category.command.application.service

import com.jongho.hobbytalk.api.category.command.application.repository.CategoryRepository
import com.jongho.hobbytalk.api.category.command.domain.model.Category

class CategoryServiceImpl(private val categoryRepository: CategoryRepository): CategoryService {
    override fun findOneById(id: Long): Category? {
        return categoryRepository.findOneById(id = id)
    }
}