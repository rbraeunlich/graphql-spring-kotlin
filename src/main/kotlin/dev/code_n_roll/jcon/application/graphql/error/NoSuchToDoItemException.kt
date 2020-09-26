package dev.code_n_roll.jcon.application.graphql.error

import java.util.*

class NoSuchToDoItemException(id: UUID): RuntimeException(
    "No ToDo item with id $id could be found."
)
