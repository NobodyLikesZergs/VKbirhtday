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
        TextView textView = (TextView) v.findViewById(R.id.swipe_name);
        textView.setText(friendList.get(position).getName());

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
