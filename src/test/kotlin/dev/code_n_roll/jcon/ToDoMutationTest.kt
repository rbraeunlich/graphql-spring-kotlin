package dev.code_n_roll.jcon

import com.graphql.spring.boot.test.GraphQLTest
import com.graphql.spring.boot.test.GraphQLTestTemplate
import dev.code_n_roll.jcon.application.ToDoItemDto
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.test.context.junit4.SpringRunner
import org.junit.runner.RunWith

@RunWith(SpringRunner::class)
@GraphQLTest
class ToDoMutationTest {
    @Autowired
    private lateinit var graphQLTestTemplate: GraphQLTestTemplate

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
        assertThat(response.get("data.addToDoItem", ToDoItemDto::class.java)).isNotNull()
    }
}