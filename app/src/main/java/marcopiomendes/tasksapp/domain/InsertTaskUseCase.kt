package marcopiomendes.tasksapp.domain

class InsertTaskUseCase(private val taskRepository: TaskRepository) {
    suspend fun execute(title: String) {
        val newTask = Task(title = title, isDone = false)
        taskRepository.insertTask(newTask)
    }
}