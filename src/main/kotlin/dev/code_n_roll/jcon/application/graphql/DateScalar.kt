package dev.code_n_roll.jcon.application.graphql

import graphql.language.StringValue
import graphql.schema.Coercing
import graphql.schema.CoercingParseLiteralException
import graphql.schema.CoercingSerializeException
import graphql.schema.GraphQLScalarType
import org.springframework.stereotype.Component
import java.time.Instant

@Component
class DateScalar(name: String?, description: String?, coercing: Coercing<*, *>?) :
    GraphQLScalarType("Date", "Scalar representing an ISO-8601 date",
        DateCoercing()
    ) {
}

class DateCoercing: Coercing<Instant, String> {
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
        return if( dataFetcherResult is Instant){
            dataFetcherResult.toString()
        } else {
            throw CoercingSerializeException("Cannot serialize class ${dataFetcherResult?.javaClass} to Date.")
        }
    }

}