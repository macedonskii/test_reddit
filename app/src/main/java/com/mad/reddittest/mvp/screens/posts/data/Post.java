package com.mad.reddittest.mvp.screens.posts.data;

import android.support.annotation.Nullable;

import io.realm.RealmObject;

public class Post extends RealmObject {

    private String id;
    private String postUrl;
    private String title;
    private String author;
    private String subredit;
    private long creationDate;
    private String image;
    private long score;
    private long commentsCount;

    public Post() {
    }

    public Post(String id, String title, String author, String subredit, @Nullable String image, long score, long creationDate, long commentsCount, String url) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.subredit = subredit;
        this.creationDate = creationDate;
        this.image = image;
        this.score = score;
        this.commentsCount = commentsCount;
        postUrl = url;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getSubredit() {
        return subredit;
    }

    public long getCreationDate() {
        return creationDate;
    }

    public String getImage() {
        return image;
    }

    public long getScore() {
        return score;
    }

    public long getCommentsCount() {
        return commentsCount;
    }

    public String getId() {
        return id;
    }

    public String getPostUrl() {
        return postUrl;
    }
}
