package dev.code_n_roll.jcon.application

import dev.code_n_roll.jcon.application.domain.ToDoItemService
import dev.code_n_roll.jcon.application.domain.toDto
import dev.code_n_roll.jcon.application.dto.ToDoItemDto
import dev.code_n_roll.jcon.application.input.ToDoItemInput
import dev.code_n_roll.jcon.application.input.toEntity
import graphql.kickstart.tools.GraphQLMutationResolver
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.time.Instant
import java.util.*

@Component
class ToDoItemMutations @Autowired constructor(private val toDoItemService: ToDoItemService) : GraphQLMutationResolver {

    fun addToDoItem(toDoItemInput: ToDoItemInput): ToDoItemDto {
        val entity = toDoItemInput.toEntity()
        return toDoItemService.addToDoItem(entity)
            .toDto()
    }
}