package todoapiclient

import todoapiclient.dto.TaskDto
import retrofit.Call
import retrofit.http.*


internal interface TodoService {

    @get:GET(value = "/todos")
    val all: Call<List<TaskDto>>

    @GET(value = "/todos/{taskId}")
    fun getById(@Path("taskId") taskId: String): Call<TaskDto>

    @POST("/todos")
    fun add(@Body task: TaskDto): Call<TaskDto>

    @PUT("/todos/{taskId}")
    fun updateById(@Path("taskId") taskId: String,
                   @Body task: TaskDto): Call<TaskDto>

    @DELETE("/todos/{taskId}")
    fun deleteById(@Path("taskId") taskId: String): Call<Void>
}
