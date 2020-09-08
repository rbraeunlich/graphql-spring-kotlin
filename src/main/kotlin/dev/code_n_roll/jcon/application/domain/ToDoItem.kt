package dev.code_n_roll.jcon.application.domain

import dev.code_n_roll.jcon.application.graphql.input.Severity
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant
import java.util.*

@Document
class ToDoItem(@Id val id: UUID,
               val severity: Severity,
               val title: String,
               val notes: String?,
               val createdAt: Instant,
               val lastModifiedAt: Instant
) {
    fun update(newSeverity: Severity, newTitle: String, newNotes: String?): ToDoItem =
        ToDoItem(this.id, newSeverity, newTitle, newNotes, this.createdAt, Instant.now())
}
