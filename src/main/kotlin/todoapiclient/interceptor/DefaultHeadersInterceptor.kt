package todoapiclient.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class DefaultHeadersInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request = request.newBuilder()
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .build()
        return chain.proceed(request)
    }
}