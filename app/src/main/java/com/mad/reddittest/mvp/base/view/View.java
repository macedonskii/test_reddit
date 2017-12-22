package com.mad.reddittest.mvp.base.view;

/**
 * Created by mad on 17.12.2017.
 */

public interface View {
    void onError(Throwable throwable);

    void showMessage(int messageId);
}
