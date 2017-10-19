package todoapiclient

import org.funktionale.either.Either
import retrofit.GsonConverterFactory
import retrofit.Response
import retrofit.Retrofit
import todoapiclient.TodoApiClientConfig.BASE_ENDPOINT
import todoapiclient.dto.TaskDto
import todoapiclient.exception.ItemNotFoundError
import todoapiclient.exception.NetworkError
import todoapiclient.exception.TodoApiClientError
import todoapiclient.exception.UnknownApiError
import todoapiclient.interceptor.DefaultHeadersInterceptor
import java.io.IOException

class TodoApiClient @JvmOverloads constructor(baseEndpoint: String = BASE_ENDPOINT) {

    private val todoService: TodoService

    init {
        val retrofit = Retrofit.Builder()
                .baseUrl(baseEndpoint)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        retrofit.client().interceptors().add(DefaultHeadersInterceptor())
        this.todoService = retrofit.create(TodoService::class.java)
    }

    val allTasks: Either<TodoApiClientError, List<TaskDto>>
        get() = try {
            val response = todoService.all.execute()
            inspectResponseForErrors(response)
        } catch (e: IOException) {
            Either.left(NetworkError)
        }

    fun getTaskById(taskId: String): Either<TodoApiClientError, TaskDto> = try {
        val response = todoService.getById(taskId).execute()
        inspectResponseForErrors(response)
    } catch (e: IOException) {
        Either.left(NetworkError)
    }

    fun addTask(task: TaskDto): Either<TodoApiClientError, TaskDto> = try {
        val response = todoService.add(task).execute()
        inspectResponseForErrors(response)
    } catch (e: IOException) {
        Either.left(NetworkError)
    }

    fun updateTaskById(task: TaskDto): Either<TodoApiClientError, TaskDto> = try {
        val response = todoService.updateById(task.id, task).execute()
        inspectResponseForErrors(response)
    } catch (e: IOException) {
        Either.left(NetworkError)
    }

    fun deleteTaskById(taskId: String): TodoApiClientError? = try {
        val response = todoService.deleteById(taskId).execute()
        inspectResponseForErrors(response).component1()
    } catch (e: IOException) {
        NetworkError
    }

    private fun <T> inspectResponseForErrors(response: Response<T>): Either<TodoApiClientError, T> = when {
        response.code() == 404 -> Either.left(ItemNotFoundError)
        response.code() >= 400 -> Either.left(UnknownApiError(response.code()))
        else -> Either.right(response.body())
    }
}
