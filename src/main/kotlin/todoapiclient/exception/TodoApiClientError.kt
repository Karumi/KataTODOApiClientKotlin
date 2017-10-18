package todoapiclient.exception

sealed class TodoApiClientError
data class UnknownApiError(val code: Int) : TodoApiClientError()
object NetworkError : TodoApiClientError()
object ItemNotFoundError: TodoApiClientError()