package todoapiclient.dto

import com.google.gson.annotations.SerializedName

data class TaskDto(
        @SerializedName("id") val id: String,
        @SerializedName("userId") val userId: String,
        @SerializedName("title") val title: String,
        @SerializedName("finished") val isFinished: Boolean)
