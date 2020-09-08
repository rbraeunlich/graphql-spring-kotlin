package dev.code_n_roll.jcon

import com.graphql.spring.boot.test.GraphQLTestTemplate
import dev.code_n_roll.jcon.application.domain.ToDoItem
import dev.code_n_roll.jcon.application.domain.ToDoItemRepository
import dev.code_n_roll.jcon.application.dto.ToDoItemDto
import dev.code_n_roll.jcon.application.input.Severity
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.within
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.test.context.junit4.SpringRunner
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ToDoMutationTest {
    @Autowired
    private lateinit var graphQLTestTemplate: GraphQLTestTemplate
    @Autowired
    private lateinit var toDoItemRepository: ToDoItemRepository


    @After
    fun tearDown() {
        toDoItemRepository.deleteAll()
    }

    @Test
    fun `should store new ToDo item`() {
        val response = graphQLTestTemplate.postMultipart(
            """
            mutation m1 {
              addToDoItem(
                toDoItem: {
                  severity: MEDIUM 
                  title: "Title" 
                  notes: "notes"
                }
              ) {
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
        val toDoItemDto = response.get("data.addToDoItem", ToDoItemDto::class.java)
        assertThat(toDoItemDto).isNotNull()

        assertThat(toDoItemRepository.findById(toDoItemDto.id)).isNotNull
    }

    @Test
    fun `should update an existing ToDo item`() {
        val toDoItem = ToDoItem(UUID.randomUUID(), Severity.LOW, "Title", null, Instant.now(), Instant.now())
        toDoItemRepository.save(toDoItem)
        val response = graphQLTestTemplate.postMultipart(
            """
            mutation m2 {
              updateToDoItem(
                id:  "${toDoItem.id}"
                toDoItemUpdate: {
                  severity: MEDIUM 
                  title: "New Title" 
                  notes: "notes"
                }
              ) {
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
        val toDoItemDto = response.get("data.updateToDoItem", ToDoItemDto::class.java)
        assertThat(toDoItemDto).isNotNull()
        assertThat(toDoItemDto.id).isEqualTo(toDoItem.id)
        assertThat(toDoItemDto.severity).isEqualTo(Severity.MEDIUM)
        assertThat(toDoItemDto.title).isEqualTo("New Title")
        assertThat(toDoItemDto.notes).isEqualTo("notes")
        assertThat(toDoItemDto.createdAt).isCloseTo(toDoItem.createdAt, within(1, ChronoUnit.MILLIS))
        assertThat(toDoItemDto.lastModifiedAt).isAfter(toDoItem.lastModifiedAt)
        assertThat(toDoItemRepository.findAll()).hasSize(1)
    }
}