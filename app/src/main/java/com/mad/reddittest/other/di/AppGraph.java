package com.mad.reddittest.other.di;

import com.mad.reddittest.mvp.base.model.ModelImpl;
import com.mad.reddittest.mvp.base.presenter.AbstractPresenter;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by mad on 18.12.2017.
 */
@Singleton
@Component(modules = {ModelModule.class, PresenterModule.class, ContextModule.class})
public interface AppGraph {

    void inject(ModelImpl model);

    void inject(AbstractPresenter abstractPresenter);

}
