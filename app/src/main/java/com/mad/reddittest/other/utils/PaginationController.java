package com.mad.reddittest.other.utils;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

public class PaginationController<T> extends RecyclerView.OnScrollListener {

    private PaginationListener<T> mPaginationListener;
    private ItemsProvider<T> mItemsProvider;
    private boolean mLoading = false;

    public PaginationController(@NonNull ItemsProvider<T> itemsProvider, PaginationListener<T> paginationListener) {
        mPaginationListener = paginationListener;
        mItemsProvider = itemsProvider;
    }

    //    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        if (mPaginationListener == null || mLoading || mItemsProvider.getItemsCount() == 0) {
            return;
        }
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            if (mItemsProvider.getLastVisibleItem() == mItemsProvider.getItemsCount() - 1) {
                Log.d("TAG", "scroll item position = " + mItemsProvider.getLastVisibleItem());
                mPaginationListener.whenPositionAchieved(mItemsProvider.getItemByPosition(mItemsProvider.getLastVisibleItem()));
                mLoading = true;
            }
        }
    }

    public void unblockLoadingState() {
        mLoading = false;
    }

    public interface PaginationListener<T> {
        void whenPositionAchieved(T object);
    }

    public interface ItemsProvider<T> {
        int getItemsCount();

        @Nullable
        T getItemByPosition(int position);

        int getLastVisibleItem();
    }
}
