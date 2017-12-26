package com.mad.reddittest.mvp.base.model.api;

import retrofit2.Response;

public class ServerError extends Throwable {

    private String message;
    private int code;

    public ServerError(Response response) {
        code = response.code();
        message = "EMPTY MESSAGE";
    }

    public ServerError(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
