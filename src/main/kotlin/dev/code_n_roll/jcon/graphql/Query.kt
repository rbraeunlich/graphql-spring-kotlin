package dev.code_n_roll.jcon.graphql

import graphql.kickstart.tools.GraphQLQueryResolver
import org.springframework.stereotype.Component

@Component
class Query : GraphQLQueryResolver {

    fun hello(): String = "foo"
}