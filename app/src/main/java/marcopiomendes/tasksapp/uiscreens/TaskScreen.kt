package marcopiomendes.tasksapp.uiscreens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import marcopiomendes.tasksapp.presentation.TaskViewModel
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

// Cores Fixas Definidas
private val ColorPrimary = Color(0xFF4A5C84)
private val ColorPrimaryContainer = Color(0xFFC9D2EA)
private val ColorOnPrimaryContainer = Color(0xFF2D3955)
private val ColorSecondary = Color(0xFF5F6675)
private val ColorError = Color(0xFFD64545)
private val ColorOutline = Color(0xFF2D3955)
private val ColorOutlineVariant = Color(0xFF5F6675)
private val ColorOnSurfaceVariant = Color(0xFF757E91)

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun TaskScreen(taskViewModel: TaskViewModel) {
    var taskText by remember { mutableStateOf("") }
    val taskList by taskViewModel.tasks.collectAsStateWithLifecycle()
    var showDeleteDialog by remember { mutableStateOf(false) }

    val totalTasks = taskList.size
    val doneTasks = taskList.count { it.isDone }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Clear List") },
            text = { Text("Are you sure you want to delete all tasks? This action cannot be undone.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        taskViewModel.clearAllTasks()
                        showDeleteDialog = false
                    }
                ) {
                    Text("Clear All", color = ColorError)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tasks App") },
                actions = {
                    if (taskList.isNotEmpty()) {
                        IconButton(onClick = { showDeleteDialog = true }) {
                            Icon(
                                imageVector = Icons.Default.DeleteSweep,
                                contentDescription = "Clear all tasks"
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors (
                    containerColor = ColorPrimaryContainer,
                    titleContentColor = ColorOnPrimaryContainer,
                    actionIconContentColor = ColorOnPrimaryContainer
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                OutlinedTextField(
                    value = taskText,
                    onValueChange = { taskText = it },
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("New Task") },
                    shape = RoundedCornerShape(22.dp),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = ColorPrimary,
                        unfocusedIndicatorColor = ColorOutline,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent
                    )
                )
                Button(
                    onClick = {
                        if (taskText.isNotBlank()) {
                            taskViewModel.insertTask(taskText)
                            taskText = ""
                        }
                    },
                    colors = ButtonDefaults.buttonColors(ColorPrimary)
                ) {
                    Text("Add")
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$doneTasks of $totalTasks completed",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = ColorSecondary
                    )
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider(color = ColorOutlineVariant)
            Spacer(modifier = Modifier.height(12.dp))

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(items =  taskList, key = { it.id }) { taskItem ->
                    var isEditing by remember (taskItem.id) { mutableStateOf(false) }
                    var newTitle by remember (taskItem.id) { mutableStateOf(taskItem.title) }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        )
                        {
                            Checkbox(
                                checked = taskItem.isDone,
                                onCheckedChange = { taskViewModel.toggleTaskDone(taskItem) },
                                colors = CheckboxDefaults.colors(ColorPrimary)
                            )
                            if (isEditing) {
                                OutlinedTextField(
                                    value = newTitle,
                                    onValueChange = { newTitle = it },
                                    modifier = Modifier.weight(1f),
                                    shape = RoundedCornerShape(22.dp),
                                    colors = TextFieldDefaults.colors(
                                        focusedIndicatorColor = ColorPrimary,
                                        unfocusedIndicatorColor = ColorOutline
                                    )
                                )
                                Button(
                                    onClick = {
                                        taskViewModel.editTask(taskItem, newTitle)
                                        isEditing = false
                                    },
                                    modifier = Modifier.padding(start = 4.dp),
                                    colors = ButtonDefaults.buttonColors(ColorPrimary)

                                ) {
                                    Text("Save")
                                }
                            }
                            else {
                                Text(
                                    text = taskItem.title,
                                    modifier = Modifier.padding(start = 8.dp).weight(1f),
                                    style = if (taskItem.isDone)
                                        LocalTextStyle.current.copy(
                                            textDecoration = TextDecoration.LineThrough,
                                            color = ColorOnSurfaceVariant.copy(alpha = 0.6f)
                                        )
                                        else LocalTextStyle.current
                                )
                            }
                        }
                        Row {
                            IconButton(onClick = {
                                if (!isEditing) newTitle = taskItem.title
                                isEditing = !isEditing
                            }) {
                                Icon(
                                    Icons.Default.Edit,
                                    contentDescription = "Edit",
                                    tint = ColorSecondary
                                )
                            }
                            IconButton(onClick = {
                                taskViewModel.deleteTask(taskItem)
                            }) {
                                Icon(
                                    Icons.Default.Delete,
                                    contentDescription = "Delete",
                                    tint = ColorError
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
