package dev.code_n_roll.jcon.application.dto

import dev.code_n_roll.jcon.application.input.Severity
import java.time.Instant
import java.util.*

data class ToDoItemDto(
    val id: UUID,
    val severity: Severity,
    val title: String,
    val notes: String?,
    val createdAt: Instant,
    val lastModifiedAt: Instant
)
