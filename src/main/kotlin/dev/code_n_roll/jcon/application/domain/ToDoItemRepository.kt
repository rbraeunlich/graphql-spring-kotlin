package dev.code_n_roll.jcon.application.domain

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ToDoItemRepository: MongoRepository<ToDoItem, UUID>
