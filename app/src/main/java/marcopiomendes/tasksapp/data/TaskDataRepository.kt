package marcopiomendes.tasksapp.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import marcopiomendes.tasksapp.domain.Task
import marcopiomendes.tasksapp.domain.TaskRepository

class TaskDataRepository(private val dao: TaskDao): TaskRepository {
    override fun getTasks(): Flow<List<Task>> {
        return dao.getAllTasks().map { list ->
            list.map { entity ->
                Task(id = entity.id, title = entity.title, isDone = entity.isDone)
            }
        }
    }

    override suspend fun insertTask(task: Task) {
        dao.insertTask(TaskEntity(title = task.title, isDone = task.isDone))
    }

    override suspend fun updateTask(task: Task) {
        dao.updateTask(TaskEntity(id = task.id, title = task.title, isDone = task.isDone))
    }

    override suspend fun deleteTask(task: Task) {
        dao.deleteTask(TaskEntity(id = task.id, title = task.title, isDone = task.isDone))
    }
}