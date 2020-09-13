package dev.code_n_roll.jcon.application.domain

import dev.code_n_roll.jcon.application.graphql.input.Severity
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.time.Instant
import java.util.*

@Repository
interface ToDoItemRepository: MongoRepository<ToDoItem, UUID> {
    fun findAllByTitle(title: String): List<ToDoItem>
    fun findAllByCreatedAtAfter(after: Instant): List<ToDoItem>
    fun findAllByLastModifiedAtBetween(start: Instant, end: Instant): List<ToDoItem>
    fun findAllBySeverity(severity: Severity): List<ToDoItem>
}
