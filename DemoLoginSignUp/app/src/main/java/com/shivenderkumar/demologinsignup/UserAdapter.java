package com.shivenderkumar.demologinsignup;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private ArrayList<User> listUser;
    Context mContext;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public UserAdapter(ArrayList<User> listUser, Context mContext) {
        this.listUser = listUser;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User ld = listUser.get(position);
        holder.txtname.setText(ld.getName());
        holder.txtcity.setText(ld.getCity());
        holder.txtgender.setText(ld.getGender());
        holder.txtage.setText(ld.getAge());


    }

    @Override
    public int getItemCount() {
        return listUser.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txtname, txtcity, txtgender, txtage;

        public ViewHolder(View itemView) {
            super(itemView);
            txtname = (TextView) itemView.findViewById(R.id.name);
            txtcity = (TextView) itemView.findViewById(R.id.city);
            txtgender = (TextView) itemView.findViewById(R.id.gender);
            txtage = (TextView) itemView.findViewById(R.id.age);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
                }
            });

        }
    }
}











//
//    @Override
//    public void onClick(View view) {
//
//        Intent intent = new Intent(mContext, UserDetailActivity.class);
//        intent.putExtra("user", Parcels.wrap());
//
//        mContext.startActivity(intent);
//
//    }