package com.mad.reddittest.mvp.base.model;

import com.mad.reddittest.mvp.screens.posts.data.Post;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by mad on 17.12.2017.
 */

public interface Model {

    Single<DataContainer<List<Post>>> getPosts(String lastElementId, boolean isRestoreState);
}
