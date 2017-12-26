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

    protected static final String TAG_SCROLL = "SCROLL_POSITION";
    private Contract.PostsPresenter presenter = new PostsPresenter(this);
    private ActivityPostsBinding binding;
    private PostsAdapter adapter;
    private PaginationController paginationController;
    private LinearLayoutManager layoutManager;
    private Parcelable parcelable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_posts);

        layoutManager = new LinearLayoutManager(null);
        binding.rvContent.setLayoutManager(layoutManager);
        adapter = new PostsAdapter(presenter);
        binding.rvContent.setAdapter(adapter);

        paginationController = new PaginationController<>(this, presenter);
        binding.rvContent.addOnScrollListener(paginationController);
        if (savedInstanceState != null && savedInstanceState.containsKey(TAG_SCROLL)) {
            parcelable = savedInstanceState.getParcelable(TAG_SCROLL);
        }
        presenter.onCreate(savedInstanceState);
        binding.btnFab.setOnClickListener(v -> {
            binding.btnFab.setVisibility(View.GONE);
            presenter.onClickRepeat();
        });
    }

    @Override
    public Presenter getPresenter() {
        return presenter;
    }

    @Override
    public void setPosts(List<Post> posts) {
        adapter.setItems(posts);
        if (parcelable == null) {
            return;
        }
        layoutManager.onRestoreInstanceState(parcelable);
    }

    @Override
    public void addPost(List<Post> data) {
        adapter.addItems(data);
    }

    @Override
    public void showRepeatButton() {
        binding.btnFab.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        binding.progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showLoading() {
        binding.progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void showPostActivity(Post post) {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder()
                .setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))
                ;
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(post.getPostUrl()));

    }

    @Override
    public void unblockPaginationController() {
        paginationController.unblockLoadingState();
    }

    @Override
    public int getItemsCount() {
        return adapter.getItemCount();
    }

    @Nullable
    @Override
    public Post getItemByPosition(int position) {
        return adapter.getItem(position);
    }

    @Override
    public int getLastVisibleItem() {
        return layoutManager.findLastVisibleItemPosition();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(TAG_SCROLL, layoutManager.onSaveInstanceState());
        super.onSaveInstanceState(outState);
    }
}
