import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

@Composable
@Preview
fun App() {
    var toDoList by remember { mutableStateOf(mutableListOf<String>()) }
    var completedList by remember { mutableStateOf(mutableListOf<String>()) }
    var newTask by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    MaterialTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Título do Programa
            Text(
                "ToDo List",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(16.dp)
            )

            TextField(
                value = newTask,
                onValueChange = { newTask = it },
                placeholder = { Text("Nova tarefa") },
                modifier = Modifier.padding(16.dp)
            )

            Button(onClick = {
                if (newTask.isNotBlank()) {
                    toDoList.add(newTask)
                    message = "\"$newTask\" foi adicionado à lista!"
                    newTask = ""
                } else {
                    message = "Por favor, insira uma tarefa."
                }
            }) {
                Text("Adicionar")
            }

            Text(message, modifier = Modifier.padding(16.dp))

            Text("Tarefas pendentes:", style = MaterialTheme.typography.h6)

            Column(modifier = Modifier.padding(16.dp)) {
                toDoList.forEachIndexed { index, task ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                            .padding(8.dp)
                    ) {
                        Checkbox(
                            checked = false,
                            onCheckedChange = {
                                toDoList.removeAt(index)
                                completedList.add(task)
                                message = "\"$task\" foi marcado como concluído!"
                            }
                        )

                        Text(
                            text = task,
                            modifier = Modifier.weight(1f),
                            color = Color.Black
                        )

                        Button(onClick = {
                            toDoList.removeAt(index)
                            message = "\"$task\" foi removido da lista!"
                        }) {
                            Text("Excluir")
                        }
                    }
                }
            }

            if (completedList.isNotEmpty()) {
                Text("Tarefas concluídas:", style = MaterialTheme.typography.h6)

                Column(modifier = Modifier.padding(16.dp)) {
                    completedList.forEachIndexed { index, completedTask ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                                .padding(8.dp)
                        ) {
                            Checkbox(
                                checked = true,
                                onCheckedChange = {
                                    completedList.removeAt(index)
                                    toDoList.add(completedTask)
                                    message = "\"$completedTask\" foi retornado à lista de pendentes!"
                                }
                            )

                            Text(
                                text = completedTask,
                                modifier = Modifier.weight(1f),
                                color = Color.Gray,
                                textDecoration = TextDecoration.LineThrough
                            )

                            Button(onClick = {
                                completedList.removeAt(index)
                                message = "\"$completedTask\" foi removido da lista de concluídos!"
                            }) {
                                Text("Excluir")
                            }
                        }
                    }
                }
            }
        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
