package dev.code_n_roll.jcon

import com.graphql.spring.boot.test.GraphQLTestTemplate
import dev.code_n_roll.jcon.application.domain.ToDoItemRepository
import dev.code_n_roll.jcon.application.dto.ToDoItemDto
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ToDoMutationTest {
    @Autowired
    private lateinit var graphQLTestTemplate: GraphQLTestTemplate
    @Autowired
    private lateinit var toDoItemRepository: ToDoItemRepository

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
}