package com.karumi.todoapiclient

import com.squareup.okhttp.mockwebserver.MockResponse
import com.squareup.okhttp.mockwebserver.MockWebServer
import com.squareup.okhttp.mockwebserver.RecordedRequest
import org.apache.commons.io.FileUtils
import org.hamcrest.core.StringContains.containsString
import org.junit.After
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import java.io.File

open class MockWebServerTest {

    private lateinit var server: MockWebServer

    @Before
    open fun setUp() {
        this.server = MockWebServer()
        this.server.start()
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    fun enqueueMockResponse(code: Int = 200, fileName: String? = null) {
        val mockResponse = MockResponse()
        val fileContent = getContentFromFile(fileName)
        mockResponse.setResponseCode(code)
        mockResponse.setBody(fileContent)
        server.enqueue(mockResponse)
    }

    protected fun assertRequestSentTo(url: String) {
        val request = server.takeRequest()
        assertEquals(url, request.path)
    }

    protected fun assertGetRequestSentTo(url: String) {
        val request = server.takeRequest()
        assertEquals(url, request.path)
        assertEquals("GET", request.method)
    }

    protected fun assertPostRequestSentTo(url: String) {
        val request = server.takeRequest()
        assertEquals(url, request.path)
        assertEquals("POST", request.method)
    }

    protected fun assertPutRequestSentTo(url: String) {
        val request = server.takeRequest()
        assertEquals(url, request.path)
        assertEquals("PUT", request.method)
    }

    protected fun assertDeleteRequestSentTo(url: String) {
        val request = server.takeRequest()
        assertEquals(url, request.path)
        assertEquals("DELETE", request.method)
    }

    protected fun assertRequestSentToContains(vararg paths: String) {
        val request = server.takeRequest()

        for (path in paths) {
            Assert.assertThat(request.path, containsString(path))
        }
    }

    fun assertRequestContainsHeader(key: String, expectedValue: String, requestIndex: Int = 0) {
        val recordedRequest = getRecordedRequestAtIndex(requestIndex)
        val value = recordedRequest!!.getHeader(key)
        assertEquals(expectedValue, value)
    }

    protected val baseEndpoint: String
        get() = server.url("/").toString()

    protected fun assertRequestBodyEquals(jsonFile: String) {
        val request = server.takeRequest()
        assertEquals(getContentFromFile(jsonFile), request.body.readUtf8())
    }

    private fun getContentFromFile(fileName: String? = null): String {
        if (fileName == null) {
            return ""
        }

        val file = File(javaClass.getResource("/" + fileName).file)
        val lines = FileUtils.readLines(file, FILE_ENCODING)
        val stringBuilder = StringBuilder()
        for (line in lines) {
            stringBuilder.append(line)
        }
        return stringBuilder.toString()
    }

    private fun getRecordedRequestAtIndex(requestIndex: Int): RecordedRequest? =
            (0..requestIndex).map { server.takeRequest() }.last()

    companion object {

        private val FILE_ENCODING = "UTF-8"
    }

}