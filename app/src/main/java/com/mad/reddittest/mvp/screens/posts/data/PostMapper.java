package com.mad.reddittest.mvp.screens.posts.data;

import com.mad.reddittest.mvp.screens.posts.data.response.Child;
import com.mad.reddittest.mvp.screens.posts.data.response.Data;
import com.mad.reddittest.mvp.screens.posts.data.response.PostResponse;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Function;

/**
 * Created by mad on 18.12.2017.
 */

public class PostMapper implements Function<PostResponse, List<Post>> {

    @Override
    public List<Post> apply(PostResponse postResponse) throws Exception {
        List<Post> list = new ArrayList<>();
        Data data;
        for (Child child : postResponse.getData().getChildren()) {
            data = child.getData();
            list.add(new Post(data.getName(),
                    data.getTitle(),
                    data.getAuthor(),
                    data.getSubreddit(),
                    data.getThumbnail(),
                    data.getScore(),
                    data.getCreatedUtc(),
                    data.getNumComments(),
                    data.getUrl()
            ));
        }
        return list;
    }
}
