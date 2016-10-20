package com.example.maq.sdr.presentation.friends;

import android.content.Context;

import com.example.maq.sdr.R;

import org.joda.time.DateTime;

public class FriendsDateConverter {

    private String[] mon;

    public FriendsDateConverter(Context context) {
        mon = new String[]{
                context.getString(R.string.mon0),
                context.getString(R.string.mon1),
                context.getString(R.string.mon2),
                context.getString(R.string.mon3),
                context.getString(R.string.mon4),
                context.getString(R.string.mon5),
                context.getString(R.string.mon6),
                context.getString(R.string.mon7),
                context.getString(R.string.mon8),
                context.getString(R.string.mon9),
                context.getString(R.string.mon10),
                context.getString(R.string.mon11),
                context.getString(R.string.mon12),
        };
    }

    public String convertDate(DateTime date) {
        if (date == null) {
            return mon[0];
        }
        String result = "" + date.getDayOfMonth() + " " + mon[date.getMonthOfYear()];
        if (date.getYear() != 3000) {
            result += " " + date.getYear();
        }
        return result;
    }

}
