package com.mad.reddittest.mvp.screens.posts;

import android.os.Bundle;

import com.mad.reddittest.mvp.base.presenter.Presenter;
import com.mad.reddittest.mvp.base.view.View;
import com.mad.reddittest.mvp.screens.posts.data.Post;
import com.mad.reddittest.other.utils.PaginationController;

import java.util.List;

/**
 * Created by mad on 17.12.2017.
 */

public interface Contract {
    interface PostsView extends View {
        void setPosts(List<Post> posts);

        void addPost(List<Post> data);

        void showRepeatButton();

        void hideLoading();

        void showLoading();

        void showPostActivity(Post post);

        void unblockPaginationController();
    }

    interface PostsPresenter extends Presenter, PaginationController.PaginationListener<Post>,PostsAdapter.ClickListener {
        void onCreate(Bundle savedInstanceState);

        void onClickRepeat();

        @Override
        void onClickRoot(Post post);
    }
}
