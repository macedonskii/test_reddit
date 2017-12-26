package com.mad.reddittest.mvp.base.model.database;

import android.content.Context;
import android.content.SharedPreferences;

import com.mad.reddittest.mvp.screens.posts.data.Post;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by mad on 18.12.2017.
 */

public class DatabaseImpl implements Database {

    private static final String PFER_NAME = "APP_PREFERENCES";
    private static final String TAG_CONTAIN_DATA = "APP_PREFERENCES";

    private SharedPreferences preferences;

    public DatabaseImpl(Context context) {
        preferences = context.getSharedPreferences(PFER_NAME, Context.MODE_PRIVATE);
    }


    @Override
    public List<Post> getPosts() {
        Realm defaultInstance = Realm.getDefaultInstance();
        List<Post> output = new ArrayList<>();
        defaultInstance.executeTransaction(realm -> {
            RealmResults<Post> all = realm.where(Post.class).findAll();
            output.addAll(realm.copyFromRealm(all));
        });
        defaultInstance.close();
        return output;
    }

    @Override
    public boolean containData() {
        return preferences.getBoolean(TAG_CONTAIN_DATA, false);
    }

    @Override
    public void eraseData() {
        Realm defaultInstance = Realm.getDefaultInstance();
        defaultInstance.executeTransaction(realm -> realm.where(Post.class).findAll().deleteAllFromRealm());
        defaultInstance.close();
        preferences.edit().putBoolean(TAG_CONTAIN_DATA, false).commit();
    }

    @Override
    public void addPosts(List<Post> post) {
        preferences.edit().putBoolean(TAG_CONTAIN_DATA, true).commit();
        Realm defaultInstance = Realm.getDefaultInstance();
        defaultInstance.executeTransaction(realm -> realm.insert(post));
        defaultInstance.close();
    }
}
