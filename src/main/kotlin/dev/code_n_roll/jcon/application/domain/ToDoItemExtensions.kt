package dev.code_n_roll.jcon.application.domain

import dev.code_n_roll.jcon.application.dto.ToDoItemDto

fun ToDoItem.toDto() = ToDoItemDto(
    this.id,
    this.severity,
    this.title,
    this.notes,
    this.createdAt,
    this.lastModifiedAt
)