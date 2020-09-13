package dev.code_n_roll.jcon.application

import graphql.Scalars
import graphql.language.StringValue
import graphql.schema.Coercing
import graphql.schema.CoercingParseLiteralException
import graphql.schema.CoercingSerializeException
import graphql.schema.GraphQLScalarType
import graphql.schema.idl.SchemaParser
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Instant

@Configuration
class ToDoConfig {

    @Bean
    fun dateScalar(): GraphQLScalarType = GraphQLScalarType.newScalar()
        .name("Date")
        .description("Scalar representing an ISO-8601 date")
        //defines the de-/serialization logic
        .coercing(DateCoercing())
        .build()

    class DateCoercing : Coercing<Instant, String> {
        override fun parseValue(input: Any?): Instant =
            Instant.parse(input.toString())

        override fun parseLiteral(input: Any?): Instant {
            return if (input is StringValue) {
                Instant.parse(input.value)
            } else {
                throw CoercingParseLiteralException("Expected AST type 'StringValue'.")
            }
        }

        override fun serialize(dataFetcherResult: Any?): String {
            return if (dataFetcherResult is Instant) {
                dataFetcherResult.toString()
            } else {
                throw CoercingSerializeException("Cannot serialize class ${dataFetcherResult?.javaClass} to Date.")
            }
        }

    }
}