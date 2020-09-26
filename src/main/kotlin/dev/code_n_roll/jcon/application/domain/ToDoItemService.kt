package dev.code_n_roll.jcon.application.domain

import dev.code_n_roll.jcon.application.graphql.error.NoSuchToDoItemException
import dev.code_n_roll.jcon.application.graphql.input.Severity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.*

@Service
class ToDoItemService @Autowired constructor(private val repository: ToDoItemRepository) {

    fun addToDoItem(toDoItem: ToDoItem): ToDoItem = repository.save(toDoItem)

    fun updateToDoItem(id: UUID, newSeverity: Severity, newTitle: String, newNotes: String?): ToDoItem {
        val existingToDoItem = repository.findById(id)
        return existingToDoItem.map { it.update(newSeverity, newTitle, newNotes) }
            .map { repository.save(it) }
            .orElseThrow{NoSuchToDoItemException(id)}
    }

    fun findById(id: UUID): Optional<ToDoItem> = repository.findById(id)
    fun findByTitle(title: String): List<ToDoItem> = repository.findAllByTitle(title)
    fun findByCreatedAfter(createdAfter: Instant): List<ToDoItem> = repository.findAllByCreatedAtAfter(createdAfter)
    fun findByModificationBetween(lastModifiedAfter: Instant, lastModifiedBefore: Instant): List<ToDoItem> =
        repository.findAllByLastModifiedAtBetween(lastModifiedAfter, lastModifiedBefore)
    fun findBySeverity(severity: Severity): List<ToDoItem> = repository.findAllBySeverity(severity)


}