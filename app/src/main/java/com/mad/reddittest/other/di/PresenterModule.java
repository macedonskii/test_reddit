package com.mad.reddittest.other.di;

import com.mad.reddittest.mvp.base.model.Model;
import com.mad.reddittest.mvp.base.model.ModelImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by mad on 18.12.2017.
 */
@Module
public class PresenterModule {

    @Provides
    @Singleton
    public Model provideModel(){
        return new ModelImpl();
    }
}
