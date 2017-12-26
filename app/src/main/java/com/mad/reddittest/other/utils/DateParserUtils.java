package com.mad.reddittest.other.utils;


import android.content.Context;

import com.mad.reddittest.R;

/**
 * Created by mad on 19.12.2017.
 */

public class DateParserUtils {

    private static final long ONE_SECOND = 1000;
    private static final long ONE_MINUTE = ONE_SECOND * 60;
    private static final long ONE_HOUR = ONE_MINUTE * 60;
    private static final long ONE_DAY = ONE_HOUR * 24;

    public static String getHumanReadableDate(long timestamp, Context context) {
        timestamp = System.currentTimeMillis() - timestamp * 1000;
        if (timestamp < ONE_MINUTE) {
            return context.getResources().getQuantityString(R.plurals.time_seconds, (int) (timestamp / ONE_SECOND), timestamp / ONE_SECOND);
        }
        if (timestamp < ONE_HOUR) {
            return context.getResources().getQuantityString(R.plurals.time_minutes, (int) (timestamp / ONE_MINUTE), timestamp / ONE_MINUTE);
        }
        if (timestamp < ONE_DAY){
            return context.getResources().getQuantityString(R.plurals.time_hours, (int) (timestamp / ONE_HOUR), timestamp / ONE_HOUR);
        }
        return context.getResources().getQuantityString(R.plurals.time_days, (int) (timestamp / ONE_DAY), timestamp / ONE_DAY);
    }

}
