package com.mad.reddittest.mvp.base.presenter;

import com.mad.reddittest.App;
import com.mad.reddittest.mvp.base.model.Model;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by mad on 17.12.2017.
 */

public abstract class AbstractPresenter implements Presenter {

    @Inject
    protected Model mModel;
    protected CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    public AbstractPresenter() {
        App.getAppGraph().inject(this);
    }

    @Override
    public void onDestroy() {
        mCompositeDisposable.dispose();
    }
}
