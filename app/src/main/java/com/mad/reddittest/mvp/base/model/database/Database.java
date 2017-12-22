package com.mad.reddittest.mvp.base.model.database;

import com.mad.reddittest.mvp.screens.posts.data.Post;

import java.util.List;


public interface Database {

    List<Post> getPosts();
    boolean containData();
    void eraseData();
    void addPosts(List<Post> post);

}
