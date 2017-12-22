package com.mad.reddittest.mvp.screens.posts.data;

import android.support.annotation.Nullable;

import io.realm.RealmObject;

public class Post extends RealmObject {

    private String mTitle;
    private String mAuthor;
    private String mSubredit;
    private long mCreationDate;
    private String mImage;
    private long mScore;
    private long mCommentsCount;
    private String mId;
    private String mUrl;

    public Post() {
    }

    public Post(String id, String title, String author, String subredit, @Nullable String image, long score, long creationDate, long commentsCount, String url) {
        mId = id;
        mTitle = title;
        mAuthor = author;
        mSubredit = subredit;
        mCreationDate = creationDate;
        mImage = image;
        mScore = score;
        mCommentsCount = commentsCount;
        mUrl = url;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getSubredit() {
        return mSubredit;
    }

    public long getCreationDate() {
        return mCreationDate;
    }

    public String getImage() {
        return mImage;
    }

    public long getScore() {
        return mScore;
    }

    public long getCommentsCount() {
        return mCommentsCount;
    }

    public String getId() {
        return mId;
    }

    public String getUrl() {
        return mUrl;
    }
}
