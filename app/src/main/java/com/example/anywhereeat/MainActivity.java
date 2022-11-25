package com.example.anywhereeat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ImageView restaurantIcon;
    ImageView ordersIcon;
    ImageView accountIcon;

    EditText searchBar;

    CardView topSearchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Hides action bar
        getSupportActionBar().hide();

        //Initialize top search bar
        topSearchBar = findViewById(R.id.TopSearchBar);
        searchBar = findViewById(R.id.searchRestaurantEditText);
        searchBar.setHint("Search Restaurants");

        //Initialize bottom menu bar
        restaurantIcon = findViewById(R.id.restaurantListIcon);
        ordersIcon = findViewById(R.id.ordersIcon);
        accountIcon = findViewById(R.id.accountIcon);
        restaurantIcon.setAlpha((float) 0.64);

        //Enable bottom menu on click listener
        restaurantIcon.setClickable(true);
        ordersIcon.setClickable(true);
        accountIcon.setClickable(true);

        restaurantIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Set menu button color
                restaurantIcon.setAlpha((float) 0.64);
                ordersIcon.setAlpha((float) 0.32);
                accountIcon.setAlpha((float) 0.32);

                //Set hint text in search bar
                searchBar.setHint("Search Restaurants");
                //Set search bar visibility
                topSearchBar.setVisibility(view.VISIBLE);

                //Fragment switch
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.homeCenteredfragment, RestaurantListFragment.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .commit();
            }
        });

        ordersIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Set menu button color
                restaurantIcon.setAlpha((float) 0.32);
                ordersIcon.setAlpha((float) 0.64);
                accountIcon.setAlpha((float) 0.32);

                //Set hint text in search bar
                topSearchBar.setVisibility(view.VISIBLE);
                //Set search bar visibility
                searchBar.setHint("Search past orders");

                //Fragment switch
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.homeCenteredfragment, OrdersFragment.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .commit();
            }
        });

        accountIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Set menu button color
                restaurantIcon.setAlpha((float) 0.32);
                ordersIcon.setAlpha((float) 0.32);
                accountIcon.setAlpha((float) 0.64);

                //Set search bar visibility
                topSearchBar.setVisibility(view.INVISIBLE);

                //Fragment switch
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.homeCenteredfragment, AccountPageFragment.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

}