package com.example.anywhereeat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class CartActivity extends AppCompatActivity {

    public static Restaurant restaurant;
    public static int[] cart;

    RecyclerView cartList;

    Button checkout;

    CartItemAdapter cartItemAdapter;

    public static double total_price = 0;

    public static TextView totalPrice;
    public static TextView restaurantName;
    public static ImageView cancel_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        //Hides action bar
        getSupportActionBar().hide();

        totalPrice = findViewById(R.id.cartTotalPrice);
        cartList = findViewById(R.id.cart_item_list);
        checkout = findViewById(R.id.checkOutbutton);

        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(CartActivity.this, MapsActivity.class);
                //startActivity(intent);
            }
        });

        restaurantName = findViewById(R.id.cart_restaurant_name);
        cancel_button = findViewById(R.id.rp_csButton);
        cancel_button.setClickable(true);

        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartActivity.this, RestaurantPageActivity.class);
                startActivity(intent);
            }
        });



        if(getIntent().getExtras() != null) {
            restaurant = (Restaurant) getIntent().getSerializableExtra("RestaurantInfo");
            Bundle extras = getIntent().getExtras();
            cart = extras.getIntArray("Cart");

            total_price = 0;
            for(int i = 0; i < cart.length; i++){
                total_price = cart[i] * restaurant.prices[i] + total_price;
            }
            totalPrice.setText("CA$" + Math.round(total_price*100.00)/100.00);
            restaurantName.setText(restaurant.getRestaurantName());

            //Construct restaurant recyclerview
            cartItemAdapter = new CartItemAdapter(this, restaurant, cart);
            cartList.setAdapter(cartItemAdapter);
            cartList.setLayoutManager(new LinearLayoutManager(this));

            cancel_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(CartActivity.this, RestaurantPageActivity.class);
                    intent.putExtra("RestaurantInfo", restaurant);
                    Bundle extras = new Bundle();
                    extras.putIntArray("Cart", cart);
                    intent.putExtras(extras);
                    startActivity(intent);
                }
            });

        }
    }

    public static void updateView(){
        total_price = 0;
        for(int i = 0; i < cart.length; i++){
            total_price = cart[i] * restaurant.prices[i] + total_price;
        }
        totalPrice.setText("CA$" + Math.round(total_price*100.00)/100.00);
        restaurantName.setText(restaurant.getRestaurantName());
    }
}