package marcopiomendes.tasksapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.Surface
import marcopiomendes.tasksapp.presentation.TaskViewModel
import marcopiomendes.tasksapp.ui.theme.TasksAppTheme
import marcopiomendes.tasksapp.uiscreens.TaskScreen

class MainActivity : ComponentActivity() {

    private val taskViewModel: TaskViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TasksAppTheme {
                Surface {
                    TaskScreen(taskViewModel = taskViewModel)
                }
            }
        }
    }
}
