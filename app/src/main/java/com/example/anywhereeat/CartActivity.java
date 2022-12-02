package com.example.anywhereeat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.Toast;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileOutputStream;

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

                if(total_price != 0){

                    int count = 0;
                    String[][] itemInOrder;

                    for(int i = 0; i < cart.length; i++){
                        if(cart[i] != 0){count++;}
                    }
                    itemInOrder = new String[count][3];
                    count = 0;
                    for(int i = 0; i < cart.length; i++){
                        if(cart[i] != 0){
                            itemInOrder[count][0] = restaurant.itemNames[i];
                            itemInOrder[count][1] = String.valueOf(restaurant.prices[i]);
                            itemInOrder[count][2] = String.valueOf(cart[i]);
                            count++;
                        }
                    }

                    NewOrders(itemInOrder, restaurant.getRestaurantName(), "unfullfiled");

                    Intent intent = new Intent(CartActivity.this, MapsActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(CartActivity.this, "Nothing in cart", Toast.LENGTH_SHORT).show();
                }
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

    public void NewOrders(String[][] order,
                          String restaurantID,
                          String state){

        String FileName = "order.txt";

        String FileContents = restaurantID + "\n"
                + state + "\n";

        for (int i = 0; i < order.length; i++) {
            for (int j = 0; j < order[0].length; j++) {
                FileContents = FileContents + order[i][j] + "\n";
            }
        }

        FileOutputStream accountOutputStream; //allow a file to be opened for writing

        try {
            accountOutputStream = openFileOutput(FileName, Context.MODE_APPEND);
            accountOutputStream.write(FileContents.getBytes());
            accountOutputStream.close();
            Toast.makeText(CartActivity.this, "Order placed successfully", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}