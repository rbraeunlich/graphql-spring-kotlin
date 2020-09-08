package dev.code_n_roll.jcon.application.domain

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ToDoItemService @Autowired constructor(private val repository: ToDoItemRepository) {

    fun addToDoItem(toDoItem: ToDoItem): ToDoItem = repository.save(toDoItem)

}