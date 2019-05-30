package com.karumi.todoapiclient

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import todoapiclient.TodoApiClient
import todoapiclient.dto.TaskDto
import todoapiclient.exception.ItemNotFoundError
import todoapiclient.exception.UnknownApiError

class TodoApiClientTest : MockWebServerTest() {

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
    fun parsesAnEmptyListOfTasksIfThereAreNoTasksCreatedBefore() {
        enqueueMockResponse(200, "emptyTasksResponse.json")

        val tasks = apiClient.allTasks

        assertNotNull(tasks.right)
        assertTrue(tasks.right?.isEmpty() ?: false)
    }

    @Test
    fun throwsUnknownErrorExceptionIfThereIsNoHandledErrorGettingAt() {
        enqueueMockResponse(418)

        val error = apiClient.allTasks.left!!

        assertEquals(UnknownApiError(418), error)
    }

    @Test
    fun sendsAGetTaskByIdRequestToTheRightPath() {
        enqueueMockResponse(200, "getTaskByIdResponse.json")

        val taskId = 1.toString()

        apiClient.getTaskById(taskId)

        assertGetRequestSentTo("/todos/$taskId")
    }

    @Test
    fun parsesASingleTaskWhenFindingATaskThatExistsById() {
        enqueueMockResponse(200, "getTaskByIdResponse.json")

        val task = apiClient.getTaskById(1.toString())

        assertNotNull(task.right)
    }

    @Test
    fun throwsTheRightExceptionWhenRequestedTaskIsNotFound() {
        enqueueMockResponse(404)

        val error = apiClient.getTaskById(1.toString())

        assertNotNull(error.left)
        assertEquals(ItemNotFoundError, error.left)
    }

    @Test
    fun throwsTheRightExceptionWhenApiReturnsAnError() {
        enqueueMockResponse(500)

        val error = apiClient.getTaskById(1.toString())

        assertNotNull(error.left)
        assertEquals(UnknownApiError(500), error.left)
    }

    private fun assertTaskContainsExpectedValues(task: TaskDto?) {
        assertTrue(task != null)
        assertEquals(task?.id, "1")
        assertEquals(task?.userId, "1")
        assertEquals(task?.title, "delectus aut autem")
        assertFalse(task!!.isFinished)
    }
}
