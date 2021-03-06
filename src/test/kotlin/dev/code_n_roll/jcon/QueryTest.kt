package dev.code_n_roll.jcon

import com.graphql.spring.boot.test.GraphQLTest
import com.graphql.spring.boot.test.GraphQLTestTemplate
import dev.code_n_roll.jcon.application.ToDoConfig
import dev.code_n_roll.jcon.application.domain.ToDoItemService
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.http.HttpStatus
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@GraphQLTest
@Import(ToDoConfig::class)
class QueryTest{
    @Autowired
    private lateinit var graphQLTestTemplate: GraphQLTestTemplate
    @MockBean
    private lateinit var toDoItemService: ToDoItemService

    @Test
    fun `query over HTTP POST multipart with variables returns data requires multipartconfig`() {
        val response = graphQLTestTemplate.postMultipart("""
            query q1{
              hello
            }
        """.trimIndent(), "{}")
        assertThat(response).isNotNull()
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response["data.hello"]).isEqualTo("foo")
    }
}