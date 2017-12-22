package com.mad.reddittest.mvp.base.model;

import android.support.annotation.Nullable;
import android.util.Log;

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

import io.reactivex.Observable;
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
    ApiInterface mApi;
    @Inject
    Database mDatabase;
    @Inject
    NetworkUtils mNetworkUtils;

    public ModelImpl() {
        App.getAppGraph().inject(this);
    }

    @Override
    public Single<DataContainer<List<Post>>> getPosts(@Nullable String lastElementId, boolean useCache) {
        if (useCache && mDatabase.containData()) {
            return Single.just(new DataContainer<>(mDatabase.getPosts(), false))
                    .compose(applySchedulers());
        } else {
            return Single.just(mNetworkUtils)
                    .map(NetworkUtils::isConnectedToInternet)
                    .flatMap(aBoolean -> {
                        if (!aBoolean) {
                            return Single.error(new ServerError(""));
                        }
                        return mApi.getPosts(lastElementId, ApiInterface.LIMIT, ApiInterface.POSTS_TIME);
                    })
                    .flatMap(this::errorHandler)
                    .map(new PostMapper())
                    .doOnSuccess(posts1 -> {
                        if (lastElementId == null) {
                            mDatabase.eraseData();
                        }
                        mDatabase.addPosts(posts1);
                    })
                    .map(data -> new DataContainer<>(data, false))
                    .onErrorReturn(throwable -> {
                        throwable.printStackTrace();
                        return new DataContainer<>(R.string.error_data_doesnt_load);
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
