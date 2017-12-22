package com.mad.reddittest.mvp.base.model.database;

import android.content.Context;
import android.content.SharedPreferences;

import com.mad.reddittest.mvp.screens.posts.data.Post;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by mad on 18.12.2017.
 */

public class DatabaseImpl implements Database {

    private static final String PFER_NAME = "APP_PREFERENCES";
    private static final String TAG_CONTAIN_DATA = "APP_PREFERENCES";
    private List<Post> mList = new ArrayList<>();

    private SharedPreferences mPreferences;

    public DatabaseImpl(Context context) {
        mPreferences = context.getSharedPreferences(PFER_NAME, Context.MODE_PRIVATE);
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
        return mPreferences.getBoolean(TAG_CONTAIN_DATA, false);
    }

    @Override
    public void eraseData() {
        Realm defaultInstance = Realm.getDefaultInstance();
        defaultInstance.executeTransaction(realm -> realm.where(Post.class).findAll().deleteAllFromRealm());
        defaultInstance.close();
        mPreferences.edit().putBoolean(TAG_CONTAIN_DATA, false).commit();
    }

    @Override
    public void addPosts(List<Post> post) {
        mPreferences.edit().putBoolean(TAG_CONTAIN_DATA, true).commit();
        Realm defaultInstance = Realm.getDefaultInstance();
        defaultInstance.executeTransaction(realm -> realm.insert(post));
        defaultInstance.close();
    }
}
