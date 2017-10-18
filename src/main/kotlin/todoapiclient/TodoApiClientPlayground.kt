package todoapiclient

import todoapiclient.dto.TaskDto

object TodoApiClientPlayground {
    @JvmStatic
    fun main(args: Array<String>) {
        val todoApiClient = TodoApiClient()
        // Get all tasks.
        val tasks = todoApiClient.allTasks
        print(tasks)

        // Get task by id.
        val task = todoApiClient.getTaskById("1")
        print(task)

        //Delete task by id.
        todoApiClient.deleteTaskById("1")

        //Update task by id.
        val taskToUpdate = TaskDto("1", "1", "Finish this kata", false)
        val updatedTask = todoApiClient.updateTaskById(taskToUpdate)
        print(updatedTask)

        //Add task.
        val taskToAdd = TaskDto("1", "1", "Finish this kata", false)
        val addedTask = todoApiClient.addTask(taskToAdd)
        print(addedTask)
    }

    private fun print(value: Any) {
        println(value)
    }
}
