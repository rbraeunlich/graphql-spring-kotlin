package dev.code_n_roll.jcon.application

data class ToDoItemInput(val severity: Severity,
    val title: String,
    val notes: String?
)

enum class Severity {
    HIGH, MEDIUM, LOW
}