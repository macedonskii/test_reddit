package com.mad.reddittest.mvp.base.model.api;

import com.google.gson.Gson;
import com.ihsanbal.logging.Level;
import com.ihsanbal.logging.LoggingInterceptor;
import com.mad.reddittest.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.internal.platform.Platform;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiProvider {

    private String mHost;
    private ApiInterface mApiInterface;

    public ApiProvider(String host) {
        mHost = host;
        createInterface();
    }

    private void createInterface() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.readTimeout(7000, TimeUnit.MILLISECONDS);
        httpClient.connectTimeout(7000, TimeUnit.MILLISECONDS);

        httpClient.addInterceptor(new LoggingInterceptor.Builder()
                .loggable(BuildConfig.DEBUG)
                .setLevel(Level.BASIC)
                .log(Platform.INFO)
                .request("Request")
                .response("Response")
                .addHeader("version", BuildConfig.VERSION_NAME)
                .build());

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(mHost)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient.build());
        mApiInterface = builder.build().create(ApiInterface.class);
    }

    public ApiInterface getApiInterface() {
        return mApiInterface;
    }
}