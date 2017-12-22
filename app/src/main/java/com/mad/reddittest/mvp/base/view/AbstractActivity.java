package com.mad.reddittest.mvp.base.view;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.mad.reddittest.mvp.base.presenter.Presenter;

/**
 * Created by mad on 17.12.2017.
 */

public abstract class AbstractActivity extends AppCompatActivity implements View {

    public final String TAG = getClass().getSimpleName();
    public abstract Presenter getPresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (getPresenter() != null) {
            getPresenter().onDestroy();
        }
    }

    @Override
    public void showMessage(int messageId) {
        Toast.makeText(this,messageId, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }
}
