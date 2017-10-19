package todoapiclient

import todoapiclient.dto.TaskDto

object TodoApiClientPlayground {
    @JvmStatic
    fun main(args: Array<String>) {
        val todoApiClient = TodoApiClient()
        // Get all tasks.
        val tasks = todoApiClient.allTasks
        println(tasks)

        // Get task by id.
        val task = todoApiClient.getTaskById("1")
        println(task)

        //Delete task by id.
        todoApiClient.deleteTaskById("1")

        //Update task by id.
        val taskToUpdate = TaskDto("1", "1", "Finish this kata", false)
        val updatedTask = todoApiClient.updateTaskById(taskToUpdate)
        println(updatedTask)

        //Add task.
        val taskToAdd = TaskDto("1", "1", "Finish this kata", false)
        val addedTask = todoApiClient.addTask(taskToAdd)
        println(addedTask)
    }
}
