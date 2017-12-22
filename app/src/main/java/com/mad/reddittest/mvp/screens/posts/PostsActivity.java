package com.mad.reddittest.mvp.screens.posts;

import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.mad.reddittest.R;
import com.mad.reddittest.databinding.ActivityPostsBinding;
import com.mad.reddittest.mvp.base.presenter.Presenter;
import com.mad.reddittest.mvp.base.view.AbstractActivity;
import com.mad.reddittest.mvp.screens.posts.data.Post;
import com.mad.reddittest.other.utils.PaginationController;

import java.util.List;

public class PostsActivity extends AbstractActivity implements Contract.PostsView, PaginationController.ItemsProvider<Post> {

    private static final String TAG_SCROLL = "SCROLL_POSITION";
    private Contract.PostsPresenter mPresenter = new PostsPresenter(this);
    private ActivityPostsBinding mBinding;
    private PostsAdapter mAdapter;
    private PaginationController mPaginationController;
    private LinearLayoutManager mLayoutManager;
    private Parcelable mParcelable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_posts);

        mLayoutManager = new LinearLayoutManager(null);
        mBinding.rvContent.setLayoutManager(mLayoutManager);
        mAdapter = new PostsAdapter(mPresenter);
        mBinding.rvContent.setAdapter(mAdapter);

        mPaginationController = new PaginationController<>(this, mPresenter);
        mBinding.rvContent.addOnScrollListener(mPaginationController);
        if (savedInstanceState != null && savedInstanceState.containsKey(TAG_SCROLL)) {
            mParcelable = savedInstanceState.getParcelable(TAG_SCROLL);
        }
        mPresenter.onCreate(savedInstanceState);
        mBinding.btnFab.setOnClickListener(v -> {
            mBinding.btnFab.setVisibility(View.GONE);
            mPresenter.onClickRepeat();
        });
    }

    @Override
    public Presenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void setPosts(List<Post> posts) {
        mAdapter.setItems(posts);
        if (mParcelable == null) {
            return;
        }
        mLayoutManager.onRestoreInstanceState(mParcelable);
    }

    @Override
    public void addPost(List<Post> data) {
        mAdapter.addItems(data);
    }

    @Override
    public void showRepeatButton() {
        mBinding.btnFab.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mBinding.progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showLoading() {
        mBinding.progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void showPostActivity(Post post) {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder()
                .setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))
                ;
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(post.getUrl()));

    }

    @Override
    public void unblockPaginationController() {
        mPaginationController.unblockLoadingState();
    }

    @Override
    public int getItemsCount() {
        return mAdapter.getItemCount();
    }

    @Nullable
    @Override
    public Post getItemByPosition(int position) {
        return mAdapter.getItem(position);
    }

    @Override
    public int getLastVisibleItem() {
        return mLayoutManager.findLastVisibleItemPosition();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(TAG_SCROLL, mLayoutManager.onSaveInstanceState());
        super.onSaveInstanceState(outState);
    }
}
