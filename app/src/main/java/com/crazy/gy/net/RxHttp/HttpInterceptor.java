package com.crazy.gy.net.RxHttp;


import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 网络拦截器
 */

public class HttpInterceptor implements Interceptor {


    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request;

        request = chain.request()
                .newBuilder()
                .addHeader("Content-Type", "application/json;charset=UTF-8")
                .build();
        // try the request
        Response originalResponse = chain.proceed(request);
        int code = originalResponse.code();

        return originalResponse;
    }
}
