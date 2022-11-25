package com.example.anywhereeat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RestaurantRecyclerViewAdapter<RecyclerViewHolder> extends RecyclerView.Adapter<RestaurantRecyclerViewAdapter.RecyclerViewHolder> {
    Context context;
    ArrayList<Restaurant> restaurants;

    public RestaurantRecyclerViewAdapter(Context context, ArrayList<Restaurant> restaurants) {
        this.context = context;
        this.restaurants = restaurants;
    }

    @NonNull
    @Override
    public RestaurantRecyclerViewAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.restaurants_list_recycler_row, parent, false);

        return new RestaurantRecyclerViewAdapter.RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantRecyclerViewAdapter.RecyclerViewHolder holder, int position) {
        holder.restaurantNameTextView.setText(restaurants.get(position).getRestaurantName());
        holder.restuarantLogo.setImageResource(restaurants.get(position).getLogo());
        holder.restaurantBackground.setImageResource(restaurants.get(position).getBackground());
    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder{

        TextView restaurantNameTextView;
        ImageView restuarantLogo;
        ImageView restaurantBackground;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            restaurantNameTextView = itemView.findViewById(R.id.restaurantNameTextView);
            restuarantLogo = itemView.findViewById(R.id.restaurantLogoimageView);
            restaurantBackground = itemView.findViewById(R.id.restaurantBackgroundImageView);
        }
    }
}
