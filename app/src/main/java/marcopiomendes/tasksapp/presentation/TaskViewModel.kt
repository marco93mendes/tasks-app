package marcopiomendes.tasksapp.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import marcopiomendes.tasksapp.data.TaskDataRepository
import marcopiomendes.tasksapp.data.TaskDatabase
import marcopiomendes.tasksapp.domain.InsertTaskUseCase
import marcopiomendes.tasksapp.domain.Task

open class TaskViewModel(application: Application): AndroidViewModel(application) {

    private val db = Room.databaseBuilder(
        application,
        TaskDatabase::class.java,
        "tasks.db"
    ).build()

    private val taskRepository = TaskDataRepository(db.taskDao())
    private val insertTaskUseCase = InsertTaskUseCase(taskRepository)

    open val tasks = taskRepository.getTasks().stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        emptyList()
    )

    fun insertTask(title: String) {
        viewModelScope.launch {
            insertTaskUseCase.execute(title)
        }
    }

    fun toggleTaskDone(task: Task) {
        viewModelScope.launch {
            taskRepository.updateTask(task.copy(isDone = !task.isDone))
        }
    }

    fun editTask(task: Task, newTitle: String) {
        viewModelScope.launch {
            if (newTitle.isNotBlank()) {
                taskRepository.updateTask(task.copy(title = newTitle))
            }
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            taskRepository.deleteTask(task)
        }
    }
}