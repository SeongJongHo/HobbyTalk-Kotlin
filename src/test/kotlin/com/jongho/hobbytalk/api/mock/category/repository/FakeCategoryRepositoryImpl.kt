package com.jongho.hobbytalk.api.mock.category.repository

import com.jongho.hobbytalk.api.category.command.application.repository.CategoryRepository
import com.jongho.hobbytalk.api.category.command.domain.model.Category
import java.util.concurrent.atomic.AtomicLong

class FakeCategoryRepositoryImpl : CategoryRepository {
    private val categoryList: MutableList<Category> = mutableListOf()
    private val id = AtomicLong(0)

    override fun findOneById(id: Long): Category? {
        return categoryList.firstOrNull { it.id == id }
    }

    fun save(category: Category): Long {
        val existingIndex = categoryList.indexOfFirst { it.id == category.id }

        if (existingIndex != -1) {
            categoryList[existingIndex] = category
        } else {
            val newCategory = category.copy(id = id.incrementAndGet())
            categoryList.add(newCategory)
        }

        return category.id
    }

    fun cleanUp() {
        categoryList.clear()
        id.set(0)
    }
}