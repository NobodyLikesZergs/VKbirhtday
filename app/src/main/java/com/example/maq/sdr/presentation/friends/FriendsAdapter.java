package com.example.maq.sdr.presentation.friends;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.maq.sdr.R;
import com.example.maq.sdr.domain.entities.Friend;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.ViewHolder>{

    private List<Friend> mFriends;

    private Context mContext;

    private FriendsDateConverter mDateConverter;

    public FriendsAdapter(List<Friend> friends, Context context) {
        mFriends = friends;
        mContext = context;
        mDateConverter = new FriendsDateConverter(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_card, parent,
                false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(mFriends.get(position).getName());
        holder.date.setText(mDateConverter.convertDate(mFriends.get(position).getBirthDate()));
        String src = mFriends.get(position).getPhoto100();
        Picasso.with(mContext)
                .load(src)
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        if (mFriends == null)
            return 0;
        return mFriends.size();
    }

    public void addData(List<Friend> friends) {
        int startPod = this.getItemCount();
        mFriends.addAll(friends);
        sortList();
        int cnt = this.getItemCount() - startPod;
        this.notifyItemRangeChanged(startPod, cnt);
    }

    public void replaceData(List<Friend> friends) {
        mFriends = friends;
        sortList();
        int cnt = this.getItemCount();
        this.notifyItemRangeChanged(0, cnt);
    }

    private void sortList() {
        Collections.sort(mFriends, new Comparator<Friend>() {
            @Override
            public int compare(Friend lhs, Friend rhs) {
                return lhs.getName().compareTo(rhs.getName());
            }
        });
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView date;
        private CircleImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            this.name = (TextView) itemView.findViewById(R.id.friend_name);
            this.date = (TextView) itemView.findViewById(R.id.birth_date);
            this.image = (CircleImageView) itemView.findViewById(R.id.image);
        }
    }
}
