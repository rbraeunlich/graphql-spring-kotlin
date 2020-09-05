package dev.code_n_roll.jcon.application

import graphql.kickstart.tools.GraphQLMutationResolver
import org.springframework.stereotype.Component
import java.time.Instant
import java.util.*

@Component
class ToDoItemMutations: GraphQLMutationResolver {

    fun addToDoItem(toDoItemInput: ToDoItemInput): ToDoItemDto = ToDoItemDto(
        UUID.randomUUID(), toDoItemInput.severity, toDoItemInput.title, toDoItemInput.notes, Instant.now(), Instant.now()
    )
}