package com.mad.reddittest.mvp.screens.posts;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mad.reddittest.R;
import com.mad.reddittest.databinding.ListItemPostBinding;
import com.mad.reddittest.mvp.screens.posts.data.Post;
import com.mad.reddittest.other.utils.DateParserUtils;
import com.mad.reddittest.other.utils.GlideApp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mad on 19.12.2017.
 */

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.PostHolder> {

    private List<Post> items = new ArrayList<>();
    private ClickListener clickListener;

    public PostsAdapter(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public PostHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PostHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_item_post, parent, false));
    }

    @Override
    public void onBindViewHolder(PostHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    public void setItems(List<Post> items) {
        this.items = items;
        notifyDataSetChanged();
    }
    public void addItems(@NonNull List<Post> items) {
        int start = this.items.size();
        this.items.addAll(items);
        notifyItemRangeInserted(start, items.size());
    }

    public Post getItem(int position) {
        return position >= items.size() ? null : items.get(position);
    }


    protected class PostHolder extends RecyclerView.ViewHolder{

        private ListItemPostBinding mBinding;

        public PostHolder(ListItemPostBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bind(Post post) {
            mBinding.tvTimeAndSubbredit.setText(mBinding.getRoot().getContext().getString(R.string.post_first_header,post.getAuthor(), DateParserUtils.getHumanReadableDate(post.getCreationDate(), mBinding.getRoot().getContext()), post.getSubredit()));
            mBinding.tvTitle.setText(Html.fromHtml(post.getTitle()));
            mBinding.tvCommentsCount.setText(String.format("%d", post.getCommentsCount()));
            mBinding.tvScore.setText(String.format("%d", post.getScore()));
            mBinding.vgRoot.setOnClickListener(v -> clickListener.onClickRoot(post));
            if (post.getImage() != null) {
                GlideApp.with(mBinding.getRoot()).load(post.getImage()).centerCrop().into(mBinding.ivContent);
                mBinding.ivContent.setVisibility(View.VISIBLE);
                return;
            }
            mBinding.ivContent.setVisibility(View.INVISIBLE);
        }
    }

    public interface ClickListener{
        void onClickRoot(Post post);
    }
}
