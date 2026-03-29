package marcopiomendes.tasksapp.domain

data class Task(
    val id: Int = 0,
    val title: String,
    val isDone: Boolean = false
)
