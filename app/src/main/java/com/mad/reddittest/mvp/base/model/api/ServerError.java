package com.mad.reddittest.mvp.base.model.api;

import retrofit2.Response;

public class ServerError extends Throwable {

    private String message;
    private int code;
    private int messageId;

    public ServerError(Response response) {
        code = response.code();
    }

    public ServerError(int messageId) {
        this.messageId = messageId;
    }

    public int getMessageId() {
        return messageId;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
