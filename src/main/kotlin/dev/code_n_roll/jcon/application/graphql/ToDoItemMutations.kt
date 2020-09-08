package dev.code_n_roll.jcon.application.graphql

import dev.code_n_roll.jcon.application.domain.ToDoItemService
import dev.code_n_roll.jcon.application.domain.toDto
import dev.code_n_roll.jcon.application.dto.ToDoItemDto
import dev.code_n_roll.jcon.application.graphql.input.ToDoItemInput
import dev.code_n_roll.jcon.application.graphql.input.toNewEntity
import graphql.kickstart.tools.GraphQLMutationResolver
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*

@Component
class ToDoItemMutations @Autowired constructor(private val toDoItemService: ToDoItemService) : GraphQLMutationResolver {

    fun addToDoItem(toDoItemInput: ToDoItemInput): ToDoItemDto {
        val entity = toDoItemInput.toNewEntity()
        return toDoItemService.addToDoItem(entity)
            .toDto()
    }

    fun updateToDoItem(id: UUID, toDoItemUpdate: ToDoItemInput): ToDoItemDto {
        return toDoItemService.updateToDoItem(id, toDoItemUpdate.severity, toDoItemUpdate.title, toDoItemUpdate.notes)
            .toDto()
    }
}