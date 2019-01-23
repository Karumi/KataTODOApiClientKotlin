package com.karumi.todoapiclient

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import todoapiclient.TodoApiClient
import todoapiclient.dto.TaskDto
import todoapiclient.exception.ItemNotFoundError
import todoapiclient.exception.UnknownApiError

class TodoApiClientTest : MockWebServerTest() {

    companion object {
        private const val ANY_TASK_ID = "1"
        private val ANY_TASK = TaskDto("1", "2", "Finish this kata", false)
    }

    private lateinit var apiClient: TodoApiClient

    @Before
    override fun setUp() {
        super.setUp()
        val mockWebServerEndpoint = baseEndpoint
        apiClient = TodoApiClient(mockWebServerEndpoint)
    }

    @Test
    fun sendsAcceptAndContentTypeHeaders() {
        enqueueMockResponse(200, "getTasksResponse.json")

        apiClient.allTasks

        assertRequestContainsHeader("Accept", "application/json")
    }

    @Test
    fun sendsContentTypeHeader() {
        enqueueMockResponse(200, "getTasksResponse.json")

        apiClient.allTasks

        assertRequestContainsHeader("Content-Type", "application/json")
    }

    @Test
    fun sendsGetAllTaskRequestToTheCorrectEndpoint() {
        enqueueMockResponse(200, "getTasksResponse.json")

        apiClient.allTasks

        assertGetRequestSentTo("/todos")
    }

    @Test
    fun parsesTasksProperlyGettingAllTheTasks() {
        enqueueMockResponse(200, "getTasksResponse.json")

        val tasks = apiClient.allTasks.right!!

        assertEquals(200, tasks.size.toLong())
        assertTaskContainsExpectedValues(tasks[0])
    }

    @Test
    fun throwsUnknownErrorExceptionIfThereIsNotHandledErrorGettingAllTasks() {
        enqueueMockResponse(418)

        val error = apiClient.allTasks.left!!

        assertEquals(UnknownApiError(418), error)
    }

    @Test
    fun sendsGetTaskByIdRequestToTheCorrectPath() {
        enqueueMockResponse(200, "getTaskByIdResponse.json")

        apiClient.getTaskById(ANY_TASK_ID)

        assertGetRequestSentTo("/todos/" + ANY_TASK_ID)
    }

    @Test
    @Throws(Exception::class)
    fun parsesTaskProperlyGettingTaskById() {
        enqueueMockResponse(200, "getTaskByIdResponse.json")

        val task = apiClient.getTaskById(ANY_TASK_ID).right!!

        assertTaskContainsExpectedValues(task)
    }

    @Test
    fun returnsItemNotFoundGettingTaskByIdIfThereIsNoTaskWithThePassedId() {
        enqueueMockResponse(404)

        val error = apiClient.getTaskById(ANY_TASK_ID).left!!

        assertEquals(ItemNotFoundError, error)
    }

    @Test
    fun throwsUnknownErrorExceptionIfThereIsNotHandledErrorGettingTaskById() {
        enqueueMockResponse(418)

        val error = apiClient.getTaskById(ANY_TASK_ID).left!!

        assertEquals(UnknownApiError(418), error)
    }

    @Test
    fun sendsAddTaskRequestToTheCorrectPath() {
        enqueueMockResponse(201, "addTaskResponse.json")

        apiClient.addTask(ANY_TASK)

        assertPostRequestSentTo("/todos")
    }

    @Test
    fun sendsTheCorrectBodyAddingANewTask() {
        enqueueMockResponse(201, "addTaskResponse.json")

        apiClient.addTask(ANY_TASK)

        assertRequestBodyEquals("addTaskRequest.json")
    }

    @Test
    fun testParsesTheTaskCreatedProperlyAddingANewTask() {
        enqueueMockResponse(201, "addTaskResponse.json")

        val task = apiClient.addTask(ANY_TASK).right!!

        assertTaskContainsExpectedValues(task)
    }

    @Test
    fun returnsUnknownErrorIfThereIsAnyErrorAddingATask() {
        enqueueMockResponse(418)

        val error = apiClient.addTask(ANY_TASK).left!!

        assertEquals(UnknownApiError(418), error)
    }

    @Test
    fun sendsTheRequestToTheCorrectPathDeletingATask() {
        enqueueMockResponse()

        apiClient.deleteTaskById(ANY_TASK_ID)

        assertDeleteRequestSentTo("/todos/1")
    }

    @Test
    fun returnsItemNotFoundIfThereIsNoTaskWithIdTheAssociateId() {
        enqueueMockResponse(404)

        val error = apiClient.deleteTaskById(ANY_TASK_ID)

        assertEquals(ItemNotFoundError, error)
    }

    @Test
    fun returnsUnknownErrorIfThereIsAnyErrorDeletingTask() {
        enqueueMockResponse(418)

        val error = apiClient.deleteTaskById(ANY_TASK_ID)

        assertEquals(UnknownApiError(418), error)
    }

    @Test
    fun sendsTheExpectedBodyUpdatingATask() {
        enqueueMockResponse(200, "updateTaskResponse.json")

        apiClient.updateTaskById(ANY_TASK)

        assertRequestBodyEquals("updateTaskRequest.json")
    }

    @Test
    fun sendsRequestToTheCorrectPathUpdatingATask() {
        enqueueMockResponse(200, "updateTaskResponse.json")

        apiClient.updateTaskById(ANY_TASK)

        assertRequestSentTo("/todos/1")
    }

    @Test
    fun parsesTheTaskProperlyUpdatingATask() {
        enqueueMockResponse(200, "updateTaskResponse.json")

        val task = apiClient.updateTaskById(ANY_TASK).right!!

        assertTaskContainsExpectedValues(task)
    }

    @Test
    fun returnsItemNotFoundErrorIfThereIsNoTaskToUpdateWithTheUsedId() {
        enqueueMockResponse(404)

        val error = apiClient.updateTaskById(ANY_TASK).left!!

        assertEquals(ItemNotFoundError, error)
    }

    @Test
    fun returnsUnknownErrorIfThereIsAnyHandledErrorUpdatingATask() {
        enqueueMockResponse(418)

        val error = apiClient.updateTaskById(ANY_TASK).left!!

        assertEquals(UnknownApiError(418), error)
    }

    private fun assertTaskContainsExpectedValues(task: TaskDto?) {
        assertTrue(task != null)
        assertEquals(task?.id, "1")
        assertEquals(task?.userId, "1")
        assertEquals(task?.title, "delectus aut autem")
        assertFalse(task!!.isFinished)
    }
}
