package com.example.anywhereeat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class ordersAdapter<RecyclerViewHolder> extends RecyclerView.Adapter<ordersAdapter.RecyclerViewHolder>{
    Context context;
    String restaurant_name;

    public ordersAdapter(Context context, String restaurant_name){
        this.context = context;
        this.restaurant_name = restaurant_name;
    }

    @NonNull
    @Override
    public ordersAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.orders_list_row, parent, false);

        return new ordersAdapter.RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ordersAdapter.RecyclerViewHolder holder, int position) {
        if(position == 0){
            holder.orederCard.setVisibility(View.INVISIBLE);
        }else{
            holder.restaurantName.setText(restaurant_name);
            if(restaurant_name.equals("McDonald's")){
                holder.restaurantImage.setImageResource(R.drawable.logo_mcdonalds);
            }else if(restaurant_name.equals("KFC")){
                holder.restaurantImage.setImageResource(R.drawable.logo_kfc);
            }else{
                holder.restaurantImage.setImageResource(R.drawable.logo_starbucks);
            }
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder{

        TextView restaurantName;
        ConstraintLayout orederCard;
        ImageView restaurantImage;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            restaurantName = itemView.findViewById(R.id.order_restaurant_name);
            orederCard = itemView.findViewById(R.id.orederCard);
            restaurantImage = itemView.findViewById(R.id.restaurant_image_order);
        }
    }
}
