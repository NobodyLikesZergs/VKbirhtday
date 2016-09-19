package com.example.maq.sdr.presentation.swipe;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.maq.sdr.R;
import com.example.maq.sdr.domain.entities.Friend;
import com.squareup.picasso.Picasso;

import org.joda.time.DateTime;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SwipeDeckAdapter extends BaseAdapter{

    private List<Friend> friendList;
    private Context context;

    public SwipeDeckAdapter(List<Friend> friends, Context context) {
        this.friendList = friends;
        this.context = context;
    }

    public void setData(List<Friend> friends) {
        this.friendList = friends;
        Collections.sort(friendList, new Comparator<Friend>() {
            @Override
            public int compare(Friend lhs, Friend rhs) {
                if (lhs.getBirthDate() == null && rhs.getBirthDate() == null)
                    return 0;
                if (lhs.getBirthDate() == null)
                    return 1;
                if (rhs.getBirthDate() == null)
                    return -1;
                DateTime currDate = new DateTime();
                int currDay = currDate.getMonthOfYear() * 31 + currDate.getDayOfMonth();
                int lDay = lhs.getBirthDate().getMonthOfYear() * 31 +
                        lhs.getBirthDate().getDayOfMonth();
                int rDay = rhs.getBirthDate().getMonthOfYear() * 31 +
                        rhs.getBirthDate().getDayOfMonth();
                int lDif = lDay < currDay ? lDay + 12 * 31 - currDay : lDay - currDay;
                int rDif = rDay < currDay ? rDay + 12 * 31 - currDay : rDay - currDay;
                if (lDif < rDif) {
                    return -1;
                } else if (lDif > rDif) {
                    return  1;
                } else {
                    return 0;
                }
            }
        });
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return friendList.size();
    }

    @Override
    public Object getItem(int position) {
        return friendList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            v = inflater.inflate(R.layout.swipe_card, parent, false);
        }

        ImageView imageView = (ImageView) v.findViewById(R.id.swipe_avatar);
        Picasso.with(context).load(friendList.get(position).getImgUrl()).into(imageView);
        TextView nameTextView = (TextView) v.findViewById(R.id.swipe_name);
        nameTextView.setText(friendList.get(position).getName());
        TextView bDateTextView = (TextView) v.findViewById(R.id.swipe_card_bdate);
        if (friendList.get(position).getBirthDate() == null) {
            bDateTextView.setText("null");
        } else {
            bDateTextView.setText(friendList.get(position).getBirthDate().toString("dd.MM"));
        }

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Layer type: ", Integer.toString(v.getLayerType()));
                Log.i("Hardware Accel type:", Integer.toString(View.LAYER_TYPE_HARDWARE));
            }
        });
        return v;
    }
}
