package todoapiclient

import retrofit.Call
import retrofit.http.Body
import retrofit.http.DELETE
import retrofit.http.GET
import retrofit.http.POST
import retrofit.http.PUT
import retrofit.http.Path
import todoapiclient.dto.TaskDto

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
