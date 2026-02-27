package marcopiomendes.cleantodoapp.domain

class InsertTodoUseCase(private val todoRepository: TodoRepository) {
    suspend fun execute(title: String) {
        val newTodo = Todo(title = title, isDone = false)
        todoRepository.insertTodo(newTodo)
    }
}