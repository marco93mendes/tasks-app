package marcopiomendes.cleantodoapp.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import marcopiomendes.cleantodoapp.domain.Todo
import marcopiomendes.cleantodoapp.domain.TodoRepository

class TodoDataRepository(private val dao: TodoDao): TodoRepository {
    override fun getTodos(): Flow<List<Todo>> {
        return dao.getAllTodos().map { list ->
            list.map { entity ->
                Todo(id = entity.id, title = entity.title, isDone = entity.isDone)
            }
        }
    }

    override suspend fun insertTodo(todo: Todo) {
        dao.insertTodo(TodoEntity(title = todo.title, isDone = todo.isDone))
    }

    override suspend fun updateTodo(todo: Todo) {
        dao.updateTodo(TodoEntity(id = todo.id, title = todo.title, isDone = todo.isDone))
    }

    override suspend fun deleteTodo(todo: Todo) {
        dao.deleteTodo(TodoEntity(id = todo.id, title = todo.title, isDone = todo.isDone))
    }

}