package com.mad.reddittest.mvp.base.model;

import android.support.annotation.NonNull;

/**
 * Created by mad on 19.12.2017.
 */

public class DataContainer<T> {
    private T mData;
    private int mMessageId;
    private boolean mSuccessful;
    private boolean mCached;

    public DataContainer(@NonNull T data, boolean cached) {
        mData = data;
        mSuccessful = true;
        mCached = cached;
    }

    public DataContainer(int messageId) {
        mMessageId = messageId;
        mSuccessful = false;
    }

    public T getData() {
        return mData;
    }

    public boolean isCached() {
        return mCached;
    }

    public int getMessageId() {
        return mMessageId;
    }

    public boolean isSuccessful() {
        return mSuccessful;
    }
}
