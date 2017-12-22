package com.mad.reddittest;

import android.app.Application;

import com.mad.reddittest.other.di.AppGraph;
import com.mad.reddittest.other.di.ContextModule;
import com.mad.reddittest.other.di.DaggerAppGraph;

import io.realm.Realm;

/**
 * Created by mad on 17.12.2017.
 */

public class App extends Application {

    private static AppGraph sAppGraph;
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        sAppGraph = DaggerAppGraph.builder().contextModule(new ContextModule(this)).build();
    }

    public static AppGraph getAppGraph() {
        return sAppGraph;
    }
}
