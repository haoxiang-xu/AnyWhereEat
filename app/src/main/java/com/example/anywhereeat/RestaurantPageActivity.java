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
import android.widget.Toast;

public class RestaurantPageActivity extends AppCompatActivity {

    ImageView restaurantIcon;
    TextView restaurantName;
    ImageView restaurantBackground;
    ImageView goBackButton;
    Button viewCartButton;

    public static TextView totalItem;
    public static int total;

    Restaurant restaurant;

    RecyclerView menu;

    public static MenuItemViewAdapter menuItemViewAdapter;

    public static int[] cart = new int[10];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_page);

        //Hides action bar
        getSupportActionBar().hide();

        //Construct view items
        restaurantIcon = findViewById(R.id.restaurantIconLarge);
        restaurantName = findViewById(R.id.restaurantName_menuPage);
        restaurantBackground = findViewById(R.id.restaurant_bg_menu);
        goBackButton = findViewById(R.id.goBackButton_menu);
        totalItem = findViewById(R.id.numberOfItemInCart);
        menu = findViewById(R.id.menuRecyclerView);
        viewCartButton = findViewById(R.id.viewCartButton);

        total = 0;
        for(int i = 0; i < cart.length; i++){
            cart[i] = 0;
        }

        goBackButton.setClickable(true);

        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RestaurantPageActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        if(getIntent().getExtras() != null) {
            restaurant = (Restaurant) getIntent().getSerializableExtra("RestaurantInfo");
            restaurantIcon.setImageResource(restaurant.getLogo());
            restaurantName.setText(restaurant.getRestaurantName());
            restaurantBackground.setImageResource(restaurant.getBackground());
            if(total != 0){
                totalItem.setText(Integer.toString(total));
            }else{
                totalItem.setText("");
            }

            //initialize cart
            Bundle extras = getIntent().getExtras();
            if(extras.getIntArray("Cart")!=null){
                cart = extras.getIntArray("Cart");
                for(int i = 0; i < cart.length; i++){
                    total = cart[i] + total;
                }
                totalItem.setText(Integer.toString(total));
            }else{
                cart = new int[restaurant.itemNames.length];
                for(int i = 0; i < cart.length; i++){
                    cart[i] = 0;
                }
            }

            //Construct restaurant recyclerview
            menuItemViewAdapter = new MenuItemViewAdapter(this, restaurant);
            menu.setAdapter(menuItemViewAdapter);
            menu.setLayoutManager(new LinearLayoutManager(this));

            viewCartButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(total == 0) {
                        Toast.makeText(RestaurantPageActivity.this, "Cart is empty",
                                Toast.LENGTH_LONG).show();
                    }else if(HomeActivity.Logined == false){
                        Toast.makeText(RestaurantPageActivity.this, "Please login first",
                                Toast.LENGTH_LONG).show();
                    }else {
                        Intent intent = new Intent(RestaurantPageActivity.this, CartActivity.class);
                        intent.putExtra("RestaurantInfo", restaurant);
                        Bundle extras = new Bundle();
                        extras.putIntArray("Cart", cart);
                        intent.putExtras(extras);
                        startActivity(intent);
                    }
                }
            });

        }

    }

    public static void updateCart(int index, boolean add){
        if(add){
            cart[index]++;
        }else{
            cart[index]--;
        }

        if(total != 0){
            totalItem.setText(Integer.toString(total));
        }else{
            totalItem.setText("");
        }
        menuItemViewAdapter.notifyDataSetChanged();
    }

}