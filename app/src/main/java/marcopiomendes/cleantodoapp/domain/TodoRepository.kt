package marcopiomendes.cleantodoapp.domain

import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    fun getTodos(): Flow<List<Todo>>
    suspend fun insertTodo(todo: Todo)
    suspend fun updateTodo(todo: Todo)
    suspend fun deleteTodo(todo: Todo)
}
