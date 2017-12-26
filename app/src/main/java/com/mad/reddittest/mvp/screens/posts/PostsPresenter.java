package com.mad.reddittest.mvp.screens.posts;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.mad.reddittest.mvp.base.presenter.AbstractPresenter;
import com.mad.reddittest.mvp.screens.posts.data.Post;

import io.reactivex.disposables.Disposable;

public class PostsPresenter extends AbstractPresenter implements Contract.PostsPresenter {

    private Contract.PostsView view;

    public PostsPresenter(Contract.PostsView view) {
        this.view = view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        loadData(savedInstanceState);
    }

    private void loadData(@Nullable Bundle savedInstanceState) {
        view.showLoading();
        Disposable subscribe = model
                .getPosts(null, savedInstanceState != null)
                .subscribe(dataContainer -> {
                    view.hideLoading();
                    if (!dataContainer.isSuccessful()) {
                        view.showMessage(dataContainer.getMessageId());
                        view.showRepeatButton();
                        return;
                    }
                    view.setPosts(dataContainer.getData());
                }, throwable -> {
                    view.onError(throwable);
                    view.hideLoading();
                    view.showRepeatButton();
                });
        compositeDisposable.add(subscribe);
    }

    @Override
    public void onClickRepeat() {
        loadData(null);
    }

    @Override
    public void whenPositionAchieved(Post object) {
        view.showLoading();
        Disposable subscribe = model
                .getPosts(object.getId(), false)
                .subscribe(listDataContainer -> {
                    view.hideLoading();
                    view.unblockPaginationController();
                    if (!listDataContainer.isSuccessful()) {
                        view.showMessage(listDataContainer.getMessageId());
                        return;
                    }
                    view.addPost(listDataContainer.getData());
                }, throwable -> {
                    view.onError(throwable);
                    view.hideLoading();
                });
        compositeDisposable.add(subscribe);
    }

    @Override
    public void onClickRoot(Post post) {
        view.showPostActivity(post);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        view = null;
    }
}
