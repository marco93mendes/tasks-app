package marcopiomendes.cleantodoapp.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import marcopiomendes.cleantodoapp.data.TodoDataRepository
import marcopiomendes.cleantodoapp.data.TodoDatabase
import marcopiomendes.cleantodoapp.domain.InsertTodoUseCase
import marcopiomendes.cleantodoapp.domain.Todo

open class TodoViewModel(application: Application): AndroidViewModel(application) {

    private val db = Room.databaseBuilder(
        application,
        TodoDatabase::class.java,
        "my_todos.db"
    ).build()

    private val todoRepository = TodoDataRepository(db.todoDao())
    private val insertTodoUseCase = InsertTodoUseCase(todoRepository)

    open val todos = todoRepository.getTodos().stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        emptyList()
    )

    fun insertTodo(title: String) {
        viewModelScope.launch {
            insertTodoUseCase.execute(title)
        }
    }

    fun toggleTodoDone(todo: Todo) {
        viewModelScope.launch {
            todoRepository.updateTodo(todo.copy(isDone = !todo.isDone))
        }
    }

    fun editTodo(todo: Todo, newTitle: String) {
        viewModelScope.launch {
            if (newTitle.isNotBlank()) {
                todoRepository.updateTodo(todo.copy(title   = newTitle))
            }
        }
    }

    fun deleteTodo(todo: Todo) {
        viewModelScope.launch {
            todoRepository.deleteTodo(todo)
        }
    }
}