package dev.code_n_roll.jcon

import com.graphql.spring.boot.test.GraphQLTest
import com.graphql.spring.boot.test.GraphQLTestTemplate
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.test.context.junit4.SpringRunner
import org.junit.runner.RunWith

@RunWith(SpringRunner::class)
@GraphQLTest
class QueryTest{
    @Autowired
    private lateinit var graphQLTestTemplate: GraphQLTestTemplate

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