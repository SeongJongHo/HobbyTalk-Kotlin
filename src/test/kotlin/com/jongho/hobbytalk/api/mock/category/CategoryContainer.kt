package com.jongho.hobbytalk.api.mock.category

import com.jongho.hobbytalk.api.category.command.application.service.CategoryServiceImpl
import com.jongho.hobbytalk.api.mock.category.repository.FakeCategoryRepositoryImpl

object CategoryContainer {
    private val map: MutableMap<String, Any> = HashMap()

    init {
        map[CategoryBeanKey.CATEGORY_REPOSITORY.getValue()] = FakeCategoryRepositoryImpl()
        map[CategoryBeanKey.CATEGORY_SERVICE.getValue()] = CategoryServiceImpl(
            categoryRepository = this.get(key = CategoryBeanKey.CATEGORY_REPOSITORY)
        )
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> get(key: CategoryBeanKey): T {
        return map[key.getValue()] as T
    }
}

enum class CategoryBeanKey(private val value: String) {
    CATEGORY_REPOSITORY("CategoryRepository"),
    CATEGORY_SERVICE("CategoryService");

    fun getValue(): String {
        return value
    }
}

fun getCategoryContainer(): CategoryContainer {
    return CategoryContainer
}