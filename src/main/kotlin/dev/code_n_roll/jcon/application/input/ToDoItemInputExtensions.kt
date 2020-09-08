package dev.code_n_roll.jcon.application.input

import dev.code_n_roll.jcon.application.domain.ToDoItem
import java.time.Instant
import java.util.*

fun ToDoItemInput.toNewEntity() = ToDoItem(
    UUID.randomUUID(),
    this.severity,
    this.title,
    this.notes,
    Instant.now(),
    Instant.now()
)