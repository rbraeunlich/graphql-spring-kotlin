package dev.code_n_roll.jcon

import com.graphql.spring.boot.test.GraphQLTestTemplate
import dev.code_n_roll.jcon.application.domain.ToDoItem
import dev.code_n_roll.jcon.application.domain.ToDoItemRepository
import dev.code_n_roll.jcon.application.dto.ToDoItemDto
import dev.code_n_roll.jcon.application.graphql.input.Severity
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.test.context.junit4.SpringRunner
import java.time.Instant
import java.util.*

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ToDoQueryTest {
    @Autowired
    private lateinit var graphQLTestTemplate: GraphQLTestTemplate
    @Autowired
    private lateinit var toDoItemRepository: ToDoItemRepository


    @After
    fun tearDown() {
        toDoItemRepository.deleteAll()
    }

    @Test
    fun `should find ToDo item by id`() {
        val toDoItem = ToDoItem(UUID.randomUUID(), Severity.LOW, "Title", null, Instant.now(), Instant.now())
        val toDoItem2 = ToDoItem(UUID.randomUUID(), Severity.LOW, "Title", null, Instant.now(), Instant.now())
        toDoItemRepository.saveAll(listOf(toDoItem, toDoItem2))
        val response = graphQLTestTemplate.postMultipart(
            """
            query q1 {
              toDoItemById(id:  "${toDoItem.id}") {
                id
                severity
                title
                notes
                createdAt
                lastModifiedAt
              }
            }
        """.trimIndent(), "{}"
        )
        assertThat(response).isNotNull()
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        val toDoItemDto = response.get("data.toDoItemById", ToDoItemDto::class.java)
        assertThat(toDoItemDto).isNotNull()
        assertThat(toDoItemDto.id).isEqualTo(toDoItem.id)
    }

    @Test
    fun `should find ToDo items by title`() {
        val toDoItem = ToDoItem(UUID.randomUUID(), Severity.LOW, "Title", null, Instant.now(), Instant.now())
        val toDoItem2 = ToDoItem(UUID.randomUUID(), Severity.LOW, "Title", null, Instant.now(), Instant.now())
        toDoItemRepository.saveAll(listOf(toDoItem, toDoItem2))
        val response = graphQLTestTemplate.postMultipart(
            """
            query q1 {
              toDoItemByTitle(title:  "${toDoItem.title}") {
                id
                severity
                title
                notes
                createdAt
                lastModifiedAt
              }
            }
        """.trimIndent(), "{}"
        )
        assertThat(response).isNotNull()
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        val toDoItemDtoList: List<ToDoItemDto> = response.getList("data.toDoItemByTitle", ToDoItemDto::class.java)
        assertThat(toDoItemDtoList).hasSize(2)
        assertThat(toDoItemDtoList)
            .allSatisfy { assertThat(it.id).isIn(listOf(toDoItem.id, toDoItem2.id)) }
    }

    @Test
    fun `should find ToDo items by creation date`() {
        val createdAt = Instant.now()
        val toDoItem = ToDoItem(UUID.randomUUID(), Severity.LOW, "Title", null, createdAt, Instant.now())
        val toDoItem2 = ToDoItem(UUID.randomUUID(), Severity.LOW, "Title", null, createdAt, Instant.now())
        toDoItemRepository.saveAll(listOf(toDoItem, toDoItem2))
        val response = graphQLTestTemplate.postMultipart(
            """
            query q1 {
              toDoItemByCreation(createdAfter:  "${createdAt.minusMillis(1000L)}") {
                id
                severity
                title
                notes
                createdAt
                lastModifiedAt
              }
            }
        """.trimIndent(), "{}"
        )
        assertThat(response).isNotNull()
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        val toDoItemDtoList: List<ToDoItemDto> = response.getList("data.toDoItemByCreation", ToDoItemDto::class.java)
        assertThat(toDoItemDtoList).hasSize(2)
        assertThat(toDoItemDtoList)
            .allSatisfy { assertThat(it.id).isIn(listOf(toDoItem.id, toDoItem2.id)) }
    }

    @Test
    fun `should find ToDo items by modification date`() {
        val createdAt = Instant.now()
        val lastModifiedAt = Instant.now()
        val toDoItem = ToDoItem(UUID.randomUUID(), Severity.LOW, "Title", null, createdAt, lastModifiedAt.plusMillis(2000L))
        val toDoItem2 = ToDoItem(UUID.randomUUID(), Severity.LOW, "Title", null, createdAt, lastModifiedAt.plusMillis(3000L))
        toDoItemRepository.saveAll(listOf(toDoItem, toDoItem2))
        val response = graphQLTestTemplate.postMultipart(
            """
            query q1 {
              toDoItemByModification(lastModifiedAfter: "${createdAt}" lastModifiedBefore:  "${toDoItem2.lastModifiedAt}") {
                id
                severity
                title
                notes
                createdAt
                lastModifiedAt
              }
            }
        """.trimIndent(), "{}"
        )
        assertThat(response).isNotNull()
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        val toDoItemDtoList: List<ToDoItemDto> = response.getList("data.toDoItemByModification", ToDoItemDto::class.java)
        assertThat(toDoItemDtoList).hasSize(1)
        assertThat(toDoItemDtoList)
            .allSatisfy { assertThat(it.id).isEqualTo(toDoItem.id) }
    }

    @Test
    fun `should find ToDo items by severity`() {
        val toDoItem = ToDoItem(UUID.randomUUID(), Severity.LOW, "Title", null, Instant.now(), Instant.now())
        val toDoItem2 = ToDoItem(UUID.randomUUID(), Severity.HIGH, "Title", null, Instant.now(), Instant.now())
        toDoItemRepository.saveAll(listOf(toDoItem, toDoItem2))
        val response = graphQLTestTemplate.postMultipart(
            """
            query q1 {
              toDoItemBySeverity(severity:  ${toDoItem2.severity}) {
                id
                severity
                title
                notes
                createdAt
                lastModifiedAt
              }
            }
        """.trimIndent(), "{}"
        )
        assertThat(response).isNotNull()
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        val toDoItemDtoList: List<ToDoItemDto> = response.getList("data.toDoItemBySeverity", ToDoItemDto::class.java)
        assertThat(toDoItemDtoList).hasSize(1)
            .first()
            .extracting { it.id }.isEqualTo(toDoItem2.id)
    }
}