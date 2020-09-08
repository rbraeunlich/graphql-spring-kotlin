package dev.code_n_roll.jcon.application.domain

import dev.code_n_roll.jcon.application.input.Severity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class ToDoItemService @Autowired constructor(private val repository: ToDoItemRepository) {

    fun addToDoItem(toDoItem: ToDoItem): ToDoItem = repository.save(toDoItem)

    fun updateToDoItem(id: UUID, newSeverity: Severity, newTitle: String, newNotes: String?): ToDoItem {
        val existingToDoItem = repository.findById(id)
        return existingToDoItem.map { it.update(newSeverity, newTitle, newNotes) }
            .map { repository.save(it) }
            .orElseThrow()

    }

}