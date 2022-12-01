package com.example.anywhereeat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.RecyclerViewHolder>{
    Context context;
    Restaurant restaurant;
    int[] cart;

    public CartItemAdapter(Context context, Restaurant restaurant, int[] cart) {
        this.context = context;
        this.restaurant = restaurant;
        this.cart = cart;
    }


    @NonNull
    @Override
    public CartItemAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_in_cart, parent, false);

        return new CartItemAdapter.RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartItemAdapter.RecyclerViewHolder holder, int position) {
        int count = 0;

        for(int i = 0; i < cart.length; i++){
            if(cart[i] != 0){
                if(count == position){
                    double totalPrice = restaurant.prices[i] * cart[i];
                    holder.itemName.setText(restaurant.itemNames[i]);
                    holder.itemTotalPrice.setText("CA$" + Math.round(totalPrice*100.00)/100.00);
                    holder.itemAmount.setText(Integer.toString(cart[i]));
                    holder.cancelOneButton.setOnClickListener(new ClickListener(i,false));
                    holder.addOneButton.setClickable(true);
                    holder.addOneButton.setOnClickListener(new ClickListener(i,true));
                }
                count++;
            }
        }
    }

    @Override
    public int getItemCount() {
        int count = 0;

        for(int i = 0; i < cart.length; i++){
            if(cart[i] != 0){
                count++;
            }
        }

        return count;
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder{

        TextView itemName;
        TextView itemTotalPrice;
        TextView itemAmount;
        Button cancelOneButton;
        ImageView addOneButton;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            itemName = itemView.findViewById(R.id.cart_item_name);
            itemTotalPrice = itemView.findViewById(R.id.total_item_price);
            itemAmount = itemView.findViewById(R.id.item_amount);
            cancelOneButton = itemView.findViewById(R.id.cart_remove_one_button);
            addOneButton = itemView.findViewById(R.id.cart_add_one_button);
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
                CartActivity.cart[p]++;
            }else{
                CartActivity.cart[p]--;
            }
            CartActivity.updateView();
            notifyDataSetChanged();
        }
    }
}
