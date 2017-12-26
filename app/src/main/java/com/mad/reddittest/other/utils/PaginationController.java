package com.mad.reddittest.other.utils;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

public class PaginationController<T> extends RecyclerView.OnScrollListener {

    private PaginationListener<T> paginationListener;
    private ItemsProvider<T> itemsProvider;
    private boolean loading = false;

    public PaginationController(@NonNull ItemsProvider<T> itemsProvider, PaginationListener<T> paginationListener) {
        this.paginationListener = paginationListener;
        this.itemsProvider = itemsProvider;
    }

    //    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        if (paginationListener == null || loading || itemsProvider.getItemsCount() == 0) {
            return;
        }
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            if (itemsProvider.getLastVisibleItem() == itemsProvider.getItemsCount() - 1) {
                Log.d("TAG", "scroll item position = " + itemsProvider.getLastVisibleItem());
                paginationListener.whenPositionAchieved(itemsProvider.getItemByPosition(itemsProvider.getLastVisibleItem()));
                loading = true;
            }
        }
    }

    public void unblockLoadingState() {
        loading = false;
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
