package dev.code_n_roll.jcon.graphql

import dev.code_n_roll.jcon.example.Argument
import graphql.kickstart.tools.GraphQLMutationResolver
import org.springframework.stereotype.Component

@Component
class Mutation : GraphQLMutationResolver {

    fun update(argument: Argument) = Response(argument.id, null)
}