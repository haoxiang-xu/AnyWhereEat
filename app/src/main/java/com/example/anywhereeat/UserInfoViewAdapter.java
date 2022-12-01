package com.example.anywhereeat;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UserInfoViewAdapter<RecyclerViewHolder> extends RecyclerView.Adapter<UserInfoViewAdapter.RecyclerViewHolder>{
    Context context;
    ArrayList<UserInfo> userInfo;

    public UserInfoViewAdapter(Context context, ArrayList<UserInfo> userInfo) {
        this.context = context;
        this.userInfo = userInfo;
    }

    @NonNull
    @Override
    public UserInfoViewAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.profile_list_recycler_row, parent, false);

        return new UserInfoViewAdapter.RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserInfoViewAdapter.RecyclerViewHolder holder, int position) {

        //Initialize EditText
        holder.value.setText(userInfo.get(position).getValue());

        holder.saveButton.setVisibility(View.INVISIBLE);
        holder.value.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                holder.saveButton.setVisibility(View.VISIBLE);
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
                holder.saveButton.setVisibility(View.INVISIBLE);
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });

        holder.icon.setImageResource(userInfo.get(position).getIcon());
    }

    @Override
    public int getItemCount() {
        return userInfo.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder{

        EditText value;
        ImageView icon;
        CardView saveButton;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            value = itemView.findViewById(R.id.profileInfoValue);
            icon = itemView.findViewById(R.id.profileInfoIcon);
            saveButton = itemView.findViewById(R.id.saveButton);
        }
    }
}
