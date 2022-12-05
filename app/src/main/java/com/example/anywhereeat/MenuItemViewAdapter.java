package com.example.anywhereeat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class MenuItemViewAdapter extends RecyclerView.Adapter<MenuItemViewAdapter.RecyclerViewHolder>{
    Context context;
    Restaurant restaurant;

    public MenuItemViewAdapter(Context context, Restaurant restaurant) {
        this.context = context;
        this.restaurant = restaurant;
    }


    @NonNull
    @Override
    public MenuItemViewAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.menu_item, parent, false);

        return new MenuItemViewAdapter.RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuItemViewAdapter.RecyclerViewHolder holder, int position) {

        if(position == restaurant.itemNames.length){
            holder.item.setVisibility(View.INVISIBLE);
        }else {
            holder.item.setVisibility(View.VISIBLE);
            holder.itemImage.setImageResource(restaurant.itemImages[position]);
            holder.itemName.setText(restaurant.itemNames[position].toString());
            holder.itemPrice.setText("CA$" + String.valueOf(restaurant.prices[position]));

            if(RestaurantPageActivity.cart[position] == 0){
                holder.numberOfItem.setText("");
                holder.addItem.setVisibility(View.VISIBLE);
                holder.addItemButton.setOnClickListener(new ClickListener(position, true));
                holder.cancel.setVisibility(View.INVISIBLE);
            }else{
                holder.addItem.setVisibility(View.INVISIBLE);
                holder.numberOfItem.setText(Integer.toString(RestaurantPageActivity.cart[position]));
                holder.addItemButton.setOnClickListener(new ClickListener(position, true));
                holder.cancel.setVisibility(View.VISIBLE);
                holder.cancelItem.setOnClickListener(new ClickListener(position, false));
            }
        }
    }

    @Override
    public int getItemCount() {
        return restaurant.itemNames.length + 1;
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder{
        ImageView itemImage;
        TextView itemName;
        TextView itemPrice;
        CardView item;
        ImageView addItem;
        TextView numberOfItem;
        Button cancelItem;
        CardView cancel;
        Button addItemButton;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            itemImage = itemView.findViewById(R.id.productImage);
            itemName = itemView.findViewById(R.id.productName);
            itemPrice = itemView.findViewById(R.id.productPrice);
            item = itemView.findViewById(R.id.itemCardView);
            addItem = itemView.findViewById(R.id.addItemToCart);
            numberOfItem = itemView.findViewById(R.id.numberOfitem);
            cancelItem = itemView.findViewById(R.id.cancelItemButtom);
            cancel = itemView.findViewById(R.id.CancelCard);
            addItemButton = itemView.findViewById(R.id.addItemButton);
        }
    }

    public class ClickListener implements View.OnClickListener
    {
        public int p;
        public boolean add;

        public ClickListener(int position, boolean add){
            p = position;
            this.add = add;
        }

        @Override
        public void onClick(View view)
        {
            if(add){
                RestaurantPageActivity.total++;
            }else{
                RestaurantPageActivity.total--;
            }
            RestaurantPageActivity.updateCart(p, add);
            Toast.makeText(context, "Item added", Toast.LENGTH_SHORT).show();
        }
    }
}
