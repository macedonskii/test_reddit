package com.mad.reddittest.mvp.screens.posts;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.mad.reddittest.mvp.base.presenter.AbstractPresenter;
import com.mad.reddittest.mvp.screens.posts.data.Post;

import io.reactivex.disposables.Disposable;

public class PostsPresenter extends AbstractPresenter implements Contract.PostsPresenter {

    private Contract.PostsView mView;

    public PostsPresenter(Contract.PostsView view) {
        mView = view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        loadData(savedInstanceState);
    }

    private void loadData(@Nullable Bundle savedInstanceState) {
        mView.showLoading();
        Disposable subscribe = mModel
                .getPosts(null, savedInstanceState != null)
                .subscribe(dataContainer -> {
                    mView.hideLoading();
                    if (!dataContainer.isSuccessful()
//                            || dataContainer.getData().size() == 0
                            ) {
                        mView.showMessage(dataContainer.getMessageId());
                        mView.showRepeatButton();
                        return;
                    }
                    mView.setPosts(dataContainer.getData());
                }, throwable -> {
                    mView.onError(throwable);
                    mView.hideLoading();
                    mView.showRepeatButton();
                });
        mCompositeDisposable.add(subscribe);
    }

    @Override
    public void onClickRepeat() {
        loadData(null);
    }

    @Override
    public void whenPositionAchieved(Post object) {
        mView.showLoading();
        Disposable subscribe = mModel
                .getPosts(object.getId(), false)
                .subscribe(listDataContainer -> {
                    mView.hideLoading();
                    mView.unblockPaginationController();
                    if (!listDataContainer.isSuccessful()) {
                        mView.showMessage(listDataContainer.getMessageId());
                        return;
                    }
                    mView.addPost(listDataContainer.getData());
                }, throwable -> {
                    mView.onError(throwable);
                    mView.hideLoading();
                });
        mCompositeDisposable.add(subscribe);
    }

    @Override
    public void onClickRoot(Post post) {
        mView.showPostActivity(post);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mView = null;
    }
}
