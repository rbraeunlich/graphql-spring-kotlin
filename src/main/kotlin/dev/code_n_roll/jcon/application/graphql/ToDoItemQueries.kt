package dev.code_n_roll.jcon.application.graphql

import dev.code_n_roll.jcon.application.domain.ToDoItem
import dev.code_n_roll.jcon.application.domain.ToDoItemService
import dev.code_n_roll.jcon.application.domain.toDto
import dev.code_n_roll.jcon.application.dto.ToDoItemDto
import graphql.kickstart.tools.GraphQLQueryResolver
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.time.Instant
import java.util.*

@Component
class ToDoItemQueries @Autowired constructor(private val toDoItemService: ToDoItemService) : GraphQLQueryResolver {

    fun toDoItemById(id: UUID): ToDoItemDto? = toDoItemService.findById(id)
        .map { it.toDto() }
        .orElse(null)

    fun toDoItemByTitle(title: String): List<ToDoItemDto> = toDoItemService.findByTitle(title).map { it.toDto() }

    fun toDoItemByCreation(createdAfter: Instant): List<ToDoItemDto> = toDoItemService.findByCreatedAfter(createdAfter)
        .map { it.toDto() }

    fun toDoItemByModification(lastModifiedAfter: Instant, lastModifiedBefore: Instant): List<ToDoItemDto> =
        toDoItemService.findByModificationBetween(lastModifiedAfter, lastModifiedBefore).map { it.toDto() }
}