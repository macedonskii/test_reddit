package com.mad.reddittest.mvp.base.model.api;

import retrofit2.Response;

public class ServerError extends Throwable {

    private String TAG_MESSAGE = "message";
    private String TAG_CODE = "code";
    private String mMessage;
    private int mCode;

    public ServerError(Response response) {
        mCode = response.code();
        mMessage = "EMPTY MESSAGE";
    }

    public ServerError(String message) {
        mMessage = message;
    }

    @Override
    public String getMessage() {
        return mMessage;
    }
}
