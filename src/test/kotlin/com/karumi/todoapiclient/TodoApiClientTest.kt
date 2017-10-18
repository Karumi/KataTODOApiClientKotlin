package com.karumi.todoapiclient

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import todoapiclient.TodoApiClient
import todoapiclient.dto.TaskDto
import todoapiclient.exception.ItemNotFoundException
import todoapiclient.exception.UnknownErrorException

class TodoApiClientTest : MockWebServerTest() {

    private lateinit var apiClient: TodoApiClient

    @Before
    @Throws(Exception::class)
    override fun setUp() {
        super.setUp()
        val mockWebServerEndpoint = baseEndpoint
        apiClient = TodoApiClient(mockWebServerEndpoint)
    }

    @Test
    @Throws(Exception::class)
    fun sendsAcceptAndContentTypeHeaders() {
        enqueueMockResponse(200, "getTasksResponse.json")

        apiClient.allTasks

        assertRequestContainsHeader("Accept", "application/json")
    }

    @Test
    @Throws(Exception::class)
    fun sendsContentTypeHeader() {
        enqueueMockResponse(200, "getTasksResponse.json")

        apiClient.allTasks

        assertRequestContainsHeader("Content-Type", "application/json")
    }

    @Test
    @Throws(Exception::class)
    fun sendsGetAllTaskRequestToTheCorrectEndpoint() {
        enqueueMockResponse(200, "getTasksResponse.json")

        apiClient.allTasks

        assertGetRequestSentTo("/todos")
    }

    @Test
    @Throws(Exception::class)
    fun parsesTasksProperlyGettingAllTheTasks() {
        enqueueMockResponse(200, "getTasksResponse.json")

        val tasks = apiClient.allTasks

        assertEquals(tasks.size.toLong(), 200)
        assertTaskContainsExpectedValues(tasks[0])
    }

    @Test(expected = UnknownErrorException::class)
    @Throws(Exception::class)
    fun throwsUnknownErrorExceptionIfThereIsNotHandledErrorGettingAllTasks() {
        enqueueMockResponse(418)

        apiClient.allTasks
    }

    @Test
    @Throws(Exception::class)
    fun sendsGetTaskByIdRequestToTheCorrectPath() {
        enqueueMockResponse(200, "getTaskByIdResponse.json")

        apiClient.getTaskById(ANY_TASK_ID)

        assertGetRequestSentTo("/todos/" + ANY_TASK_ID)
    }

    @Test
    @Throws(Exception::class)
    fun parsesTaskProperlyGettingTaskById() {
        enqueueMockResponse(200, "getTaskByIdResponse.json")

        val task = apiClient.getTaskById(ANY_TASK_ID)

        assertTaskContainsExpectedValues(task)
    }

    @Test(expected = ItemNotFoundException::class)
    @Throws(Exception::class)
    fun returnsItemNotFoundGettingTaskByIdIfThereIsNoTaskWithThePassedId() {
        enqueueMockResponse(404)

        apiClient.getTaskById(ANY_TASK_ID)
    }

    @Test(expected = UnknownErrorException::class)
    @Throws(Exception::class)
    fun throwsUnknownErrorExceptionIfThereIsNotHandledErrorGettingTaskById() {
        enqueueMockResponse(418)

        apiClient.getTaskById(ANY_TASK_ID)
    }

    @Test
    @Throws(Exception::class)
    fun sendsAddTaskRequestToTheCorrectPath() {
        enqueueMockResponse(201, "addTaskResponse.json")

        apiClient.addTask(ANY_TASK)

        assertPostRequestSentTo("/todos")
    }

    @Test
    @Throws(Exception::class)
    fun sendsTheCorrectBodyAddingANewTask() {
        enqueueMockResponse(201, "addTaskResponse.json")

        apiClient.addTask(ANY_TASK)

        assertRequestBodyEquals("addTaskRequest.json")
    }

    @Test
    @Throws(Exception::class)
    fun testParsesTheTaskCreatedProperlyAddingANewTask() {
        enqueueMockResponse(201, "addTaskResponse.json")

        val task = apiClient.addTask(ANY_TASK)

        assertTaskContainsExpectedValues(task)
    }

    @Test(expected = UnknownErrorException::class)
    @Throws(Exception::class)
    fun returnsUnknownErrorIfThereIsAnyErrorAddingATask() {
        enqueueMockResponse(418)

        apiClient.addTask(ANY_TASK)
    }

    @Test
    @Throws(Exception::class)
    fun sendsTheRequestToTheCorrectPathDeletingATask() {
        enqueueMockResponse()

        apiClient.deleteTaskById(ANY_TASK_ID)

        assertDeleteRequestSentTo("/todos/1")
    }

    @Test(expected = ItemNotFoundException::class)
    @Throws(Exception::class)
    fun returnsItemNotFoundIfThereIsNoTaskWithIdTheAssociateId() {
        enqueueMockResponse(404)

        apiClient.deleteTaskById(ANY_TASK_ID)
    }

    @Test(expected = UnknownErrorException::class)
    @Throws(Exception::class)
    fun returnsUnknownErrorIfThereIsAnyErrorDeletingTask() {
        enqueueMockResponse(418)

        apiClient.deleteTaskById(ANY_TASK_ID)
    }

    @Test
    @Throws(Exception::class)
    fun sendsTheExpectedBodyUpdatingATask() {
        enqueueMockResponse(200, "updateTaskResponse.json")

        apiClient.updateTaskById(ANY_TASK)

        assertRequestBodyEquals("updateTaskRequest.json")
    }

    @Test
    @Throws(Exception::class)
    fun sendsRequestToTheCorrectPathUpdatingATask() {
        enqueueMockResponse(200, "updateTaskResponse.json")

        apiClient.updateTaskById(ANY_TASK)

        assertRequestSentTo("/todos/1")
    }

    @Test
    @Throws(Exception::class)
    fun parsesTheTaskProperlyUpdatingATask() {
        enqueueMockResponse(200, "updateTaskResponse.json")

        val task = apiClient.updateTaskById(ANY_TASK)

        assertTaskContainsExpectedValues(task)
    }

    @Test(expected = ItemNotFoundException::class)
    @Throws(Exception::class)
    fun returnsItemNotFoundErrorIfThereIsNoTaskToUpdateWithTheUsedId() {
        enqueueMockResponse(404)

        apiClient.updateTaskById(ANY_TASK)
    }

    @Test(expected = UnknownErrorException::class)
    @Throws(Exception::class)
    fun returnsUnknownErrorIfThereIsAnyHandledErrorUpdatingATask() {
        enqueueMockResponse(418)

        apiClient.updateTaskById(ANY_TASK)
    }

    private fun assertTaskContainsExpectedValues(task: TaskDto?) {
        assertTrue(task != null)
        assertEquals(task?.id, "1")
        assertEquals(task?.userId, "1")
        assertEquals(task?.title, "delectus aut autem")
        assertFalse(task!!.isFinished)
    }

    companion object {

        private val ANY_TASK_ID = "1"
        private val ANY_TASK = TaskDto("1", "2", "Finish this kata", false)
    }
}
