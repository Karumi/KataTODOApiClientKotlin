package todoapiclient

import todoapiclient.TodoApiClientConfig.Companion.BASE_ENDPOINT
import todoapiclient.dto.TaskDto
import todoapiclient.exception.ItemNotFoundException
import todoapiclient.exception.NetworkErrorException
import todoapiclient.exception.TodoApiClientException
import todoapiclient.exception.UnknownErrorException
import todoapiclient.interceptor.DefaultHeadersInterceptor
import retrofit.GsonConverterFactory
import retrofit.Response
import retrofit.Retrofit
import java.io.IOException


class TodoApiClient @JvmOverloads constructor(baseEndpoint: String = BASE_ENDPOINT) {

    private val todoService: TodoService

    init {
        val retrofit = Retrofit.Builder().baseUrl(baseEndpoint)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        retrofit.client().interceptors().add(DefaultHeadersInterceptor())
        this.todoService = retrofit.create(TodoService::class.java)
    }

    val allTasks: List<TaskDto>
        @Throws(TodoApiClientException::class)
        get() {
            try {
                val response = todoService.all.execute()
                inspectResponseForErrors(response)
                return response.body()
            } catch (e: IOException) {
                throw NetworkErrorException()
            }

        }

    @Throws(TodoApiClientException::class)
    fun getTaskById(taskId: String): TaskDto {
        try {
            val response = todoService.getById(taskId).execute()
            inspectResponseForErrors(response)
            return response.body()
        } catch (e: IOException) {
            throw NetworkErrorException()
        }

    }

    @Throws(TodoApiClientException::class)
    fun addTask(task: TaskDto): TaskDto {
        try {
            val response = todoService.add(task).execute()
            inspectResponseForErrors(response)
            return response.body()
        } catch (e: IOException) {
            throw NetworkErrorException()
        }

    }

    @Throws(TodoApiClientException::class)
    fun updateTaskById(task: TaskDto): TaskDto {
        try {
            val response = todoService.updateById(task.id, task).execute()
            inspectResponseForErrors(response)
            return response.body()
        } catch (e: IOException) {
            throw NetworkErrorException()
        }

    }

    @Throws(TodoApiClientException::class)
    fun deleteTaskById(taskId: String) {
        try {
            val response = todoService.deleteById(taskId).execute()
            inspectResponseForErrors(response)
        } catch (e: IOException) {
            throw NetworkErrorException()
        }

    }

    @Throws(TodoApiClientException::class)
    private fun <T> inspectResponseForErrors(response: Response<T>) {
        val code = response.code()
        if (code == 404) {
            throw ItemNotFoundException()
        } else if (code >= 400) {
            throw UnknownErrorException(code)
        }
    }
}
