package com.jongho.hobbytalk.api.category.command.domain.model

class Category(
    val id: Long,
    val name: String,
    val parentId: Long
) {
    fun copy(
        id: Long = this.id,
        name: String = this.name,
        parentId: Long = this.parentId
    ): Category {
        return Category(
            id = id,
            name = name,
            parentId = parentId
        )
    }
}