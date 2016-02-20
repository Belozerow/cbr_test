package com.belozerov.cbrrate.api;

import android.support.annotation.NonNull;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import retrofit.SimpleXmlConverterFactory;

/**
 * Created: Belozerov
 * Date: 09.02.2016
 */
public class RetrofitFactory {
    private static final String BASE_URL = "http://www.cbr.ru/";
    private static final int CONNECT_TIMEOUT = 15;
    private static final int WRITE_TIMEOUT = 60;
    private static final int TIMEOUT = 15;
    private static final OkHttpClient CLIENT = new OkHttpClient();
    private static Retrofit RETROFIT_INSTANCE = null;
    private static CBRApi CBR_API_SERVICE_INSTANCE = null;

    static {
        CLIENT.setConnectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS);
        CLIENT.setWriteTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS);
        CLIENT.setReadTimeout(TIMEOUT, TimeUnit.SECONDS);
    }

    private RetrofitFactory() {
    }

    @NonNull
    public static synchronized CBRApi getCbrApiServiceInstance() {
        if (CBR_API_SERVICE_INSTANCE == null) {
            CBR_API_SERVICE_INSTANCE = getRetrofit().create(CBRApi.class);
        }
        return CBR_API_SERVICE_INSTANCE;
    }

    @NonNull
    private static synchronized Retrofit getRetrofit() {
        if (RETROFIT_INSTANCE != null) {
            return RETROFIT_INSTANCE;
        } else {
            RETROFIT_INSTANCE = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(SimpleXmlConverterFactory.create())
                    .client(CLIENT)
                    .build();
            if (RETROFIT_INSTANCE.client().interceptors().size() == 0) {
                RETROFIT_INSTANCE.client().interceptors().add(new DebugInterceptor());
            }
            return RETROFIT_INSTANCE;
        }
    }

    private static class DebugInterceptor implements Interceptor {
        @SuppressWarnings("UnnecessaryLocalVariable")
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Response response = chain.proceed(request);
            return response;
        }
    }

}
