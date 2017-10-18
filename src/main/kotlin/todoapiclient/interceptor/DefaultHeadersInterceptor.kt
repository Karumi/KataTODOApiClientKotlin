package todoapiclient.interceptor

import com.squareup.okhttp.Interceptor
import com.squareup.okhttp.Response
import java.io.IOException


class DefaultHeadersInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request = request.newBuilder()
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .build()
        return chain.proceed(request)
    }
}