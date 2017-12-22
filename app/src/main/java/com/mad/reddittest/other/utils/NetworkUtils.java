package com.mad.reddittest.other.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by mad on 20.12.2017.
 */

public class NetworkUtils {
    private Context mContext;

    public NetworkUtils(Context context) {
        mContext = context;
    }

    public boolean isConnectedToInternet(){
        ConnectivityManager connectivityManager = (ConnectivityManager) (mContext.getSystemService(Context.CONNECTIVITY_SERVICE));
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();

    }
}
