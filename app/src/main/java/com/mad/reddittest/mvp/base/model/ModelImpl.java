package com.mad.reddittest.mvp.base.model;

import android.support.annotation.Nullable;

import com.mad.reddittest.App;
import com.mad.reddittest.R;
import com.mad.reddittest.mvp.base.model.api.ApiInterface;
import com.mad.reddittest.mvp.base.model.api.ServerError;
import com.mad.reddittest.mvp.base.model.database.Database;
import com.mad.reddittest.mvp.screens.posts.data.Post;
import com.mad.reddittest.mvp.screens.posts.data.PostMapper;
import com.mad.reddittest.other.utils.NetworkUtils;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by mad on 17.12.2017.
 */

public class ModelImpl implements Model {

    @Inject
    ApiInterface api;
    @Inject
    Database database;
    @Inject
    NetworkUtils networkUtils;

    public ModelImpl() {
        App.getAppGraph().inject(this);
    }

    @Override
    public Single<DataContainer<List<Post>>> getPosts(@Nullable String lastElementId, boolean useCache) {
        if (useCache && database.containData()) {
            return Single.just(new DataContainer<>(database.getPosts()))
                    .compose(applySchedulers());
        } else {
            return Single.just(networkUtils)
                    .map(NetworkUtils::isConnectedToInternet)
                    .flatMap(aBoolean -> {
                        if (!aBoolean) {
                            return Single.error(new ServerError(R.string.error_data_no_internet));
                        }
                        return api.getPosts(lastElementId, ApiInterface.LIMIT, ApiInterface.POSTS_TIME);
                    })
                    .flatMap(this::errorHandler)
                    .map(new PostMapper())
                    .doOnSuccess(posts1 -> {
                        if (lastElementId == null) {
                            database.eraseData();
                        }
                        database.addPosts(posts1);
                    })
                    .map(DataContainer::new)
                    .onErrorReturn(throwable -> {
                        ServerError serverError = throwable instanceof ServerError ? ((ServerError) throwable) : null;
                        if (serverError != null) {
                            return new DataContainer<>(serverError.getMessageId());
                        }
                        return new DataContainer<>(R.string.error_data_other);
                    })
                    .compose(applySchedulers());
        }
    }


    <T> SingleTransformer<T, T> applySchedulers() {
        return observable -> observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    <T> Single<T> errorHandler(Response<T> response) {
        if (!response.isSuccessful() || response.body() == null) {
            return Single.error(new ServerError(response));
        }
        return Single.just(response.body());
    }
}
