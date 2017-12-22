package com.mad.reddittest.mvp.base.model.api;

import com.mad.reddittest.mvp.screens.posts.data.response.PostResponse;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface ApiInterface {
    String HOST = "https://www.reddit.com/";
    int LIMIT = 10;
    String POSTS_TIME = "day";

    @GET("top/.json")
    Single<Response<PostResponse>> getPosts(@Query("after") String lastElementId, @Query("limit") int limit, @Query("t") String time);

}
