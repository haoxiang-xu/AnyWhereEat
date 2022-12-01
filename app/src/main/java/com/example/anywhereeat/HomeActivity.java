package com.example.anywhereeat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    ImageView restaurantIcon;
    ImageView ordersIcon;
    ImageView accountIcon;
    ImageView customerServiceabutton;

    EditText searchBar;

    CardView topSearchBar;
    CardView bottomMenuBar;
    CardView cancel;

    public static boolean Logined = false;
    public static String[] userInformations;

    public static ArrayList<Restaurant> restaurants;
    public static ArrayList<UserInfo> userInfos;

    public static int[] restaurantLogos = {R.drawable.ic_baseline_image_24, R.drawable.logo_mcdonalds, R.drawable.logo_kfc, R.drawable.logo_starbucks, R.drawable.logo_timhortons, R.drawable.logo_burgerking, R.drawable.logo_popeyes, R.drawable.ic_baseline_image_24};
    public static int[] restaurantBackgrounds = {R.drawable.ic_baseline_image_24, R.drawable.background_mc, R.drawable.background_kfc, R.drawable.background_starbucks, R.drawable.background_th, R.drawable.background_bk, R.drawable.background_pe, R.drawable.ic_baseline_image_24};
    public static int[] infoIcon = {R.drawable.ic_baseline_address_24, R.drawable.ic_baseline_local_phone_24, R.drawable.ic_baseline_email_24, R.drawable.ic_baseline_credit_card_24, R.drawable.ic_baseline_password_24};

    public static int[] kfc_menu = {R.drawable.kfc_25_piece_party_pack, R.drawable.kfc_6_piece_bucket_and_2_large_sides, R.drawable.kfc_big_crunch_sandwich, R.drawable.kfc_double_tender_sandwich_box, R.drawable.kfc_gravy_lovers_double_bucket, R.drawable.kfc_gravy_lovers_double_bucket_feast, R.drawable.kfc_gravy_lovers_sandwich_box_meal, R.drawable.kfc_gravy_lovers_sandwich_combo, R.drawable.kfc_kfc_famous_chicken_chicken_sandwich, R.drawable.kfc_kfc_famous_chicken_chicken_sandwich_box_meal};
    public static int[] mcdonalds_menu = {R.drawable.mcdonalds_barqs_root_beer, R.drawable.mcdonalds_big_mac, R.drawable.mcdonalds_cheeseburger, R.drawable.mcdonalds_double_big_mac, R.drawable.mcdonalds_double_quarter_pounder_blt, R.drawable.mcdonalds_filet_o_fish, R.drawable.mcdonalds_hamburger, R.drawable.mcdonalds_happy_meal_snack_wrap_with_crispy_chicken, R.drawable.mcdonalds_mccrispy, R.drawable.mcdonalds_world_famous_fries_small};
    public static int[] startbucks_menu = {R.drawable.starbucks_artisan_charcuterie_snack_box, R.drawable.starbucks_beyond_meat_cheddar_egg_sandwich, R.drawable.starbucks_caffe_misto, R.drawable.starbucks_everything_bagel, R.drawable.starbucks_everything_croissant_roasted_ham_sandwich, R.drawable.starbucks_featured_dark_roast, R.drawable.starbucks_hot_hocolate, R.drawable.starbucks_peppermint_hot_chocolate, R.drawable.starbucks_peppermint_white_hot_chocolate, R.drawable.starbucks_rolled_steel_cut_oatmeal, R.drawable.starbucks_southwest_potato_black_bean_egg_wrap, R.drawable.starbucks_white_hot_chocolate};

    public static double[] kfc_price = {65.99, 26.99, 13.99, 11.79, 42.99, 48.99, 17.49, 13.99, 15.49, 9.99};
    public static double[] mcdonalds_price = {1.69, 7.09, 7.89, 9.29, 9.09, 6.89, 4.29, 4.59, 3.29, 2.99};
    public static double[] starbucks_price = {5.75, 3.75, 3.25, 3.75, 3.75, 3.75, 3.75, 3.95, 3.95, 3.45, 3.75, 3.75};

    public static String[] kfc_item = {"25 PIECE PARTY PACK", "6 PIECE BUCKET AND 2 LARGE SIDES", "BIG CRUNCH SANDWICH", "DOUBLE TENDER SANDWICH BOX", "GRAVY LOVERS DOUBLE BUCKET", "GRAVY LOVERS DOUBLE BUCKET FEAST", "GRAVY LOVERS SANDWICH BOX MEAL", "GRAVY LOVERS SANDWICH COMBO", "KFC FAMOUS CHICKEN CHICKEN SANDWICH", "KFC FAMOUS CHICKEN CHICKEN SANDWICH BOX MEAL"};
    public static String[] mcdonalds_item = {"Barq's Root Beer", "Big Mac", "Cheeseburger", "Double Big Mac", "Double Quarter Pounder BLT", "Filet-O-Fish", "Hamburger", "Happy Meal Snack Wrap with Crispy Chicken", "McCrispy", "World Famous Fries – Small"};
    public static String[] starbucks_item = {"Artisan Charcuterie Snack Box", "Beyond Meat, Cheddar & Egg Sandwich", "Caffè Misto", "Everything Bagel", "Everything Croissant & Roasted Ham Sandwich", "Featured Dark Roast", "Hot Chocolate", "Peppermint Hot Chocolate", "Peppermint White Hot Chocolate", "Rolled & Steel-Cut Oatmeal", "Southwest Potato, Black Bean & Egg Wrap", "White Hot Chocolate"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Hides action bar
        getSupportActionBar().hide();

        //Check user login or not
        String loginInfo = readFromFile("userInfo.txt", this);

        if(loginInfo.equals("")){
            Logined = false;
        }else{
            int spliter = loginInfo.indexOf("#");
            String username_login = loginInfo.substring(0, spliter);
            String password_login = loginInfo.substring(1 + spliter);

            userInformations = login(username_login, password_login);

            Logined = true;
        }

        //Initialize Fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.homeCenteredfragment, RestaurantListFragment.class, null)
                .setReorderingAllowed(true)
                .addToBackStack(null)
                .commit();

        //Initialize top search bar
        topSearchBar = findViewById(R.id.TopSearchBar);
        searchBar = findViewById(R.id.searchRestaurantEditText);
        cancel = findViewById(R.id.goBackButton);
        cancel.setVisibility(View.INVISIBLE);
        searchBar.setHint("Search Restaurants");
        customerServiceabutton = findViewById(R.id.rp_csButton);
        customerServiceabutton.setClickable(true);
        customerServiceabutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, CustomerServiceActivity.class);
                startActivity(intent);
            }
        });

        //Initialize bottom menu bar
        bottomMenuBar = findViewById(R.id.BottomMenuBar);
        restaurantIcon = findViewById(R.id.restaurantListIcon);
        ordersIcon = findViewById(R.id.ordersIcon);
        accountIcon = findViewById(R.id.accountIcon);
        restaurantIcon.setAlpha((float) 0.64);

        //Enable on click listener
        restaurantIcon.setClickable(true);
        ordersIcon.setClickable(true);
        accountIcon.setClickable(true);
        cancel.setClickable(true);

        restaurantIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Set menu button color
                bottomMenuBar.setVisibility(view.VISIBLE);
                restaurantIcon.setAlpha((float) 0.64);
                ordersIcon.setAlpha((float) 0.32);
                accountIcon.setAlpha((float) 0.32);

                //Set hint text in search bar
                searchBar.setHint("Search Restaurants");

                //Set search bar visibility
                cancel.setVisibility(View.INVISIBLE);
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
                if(Logined) {
                    //Set menu button color
                    bottomMenuBar.setVisibility(view.VISIBLE);
                    restaurantIcon.setAlpha((float) 0.32);
                    ordersIcon.setAlpha((float) 0.64);
                    accountIcon.setAlpha((float) 0.32);

                    //Set hint text in search bar
                    topSearchBar.setVisibility(view.VISIBLE);

                    //Set search bar visibility
                    searchBar.setHint("Search past orders");
                    cancel.setVisibility(View.INVISIBLE);

                    //Fragment switch
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.homeCenteredfragment, OrdersFragment.class, null)
                            .setReorderingAllowed(true)
                            .addToBackStack(null)
                            .commit();
                }else {
                    Toast.makeText(HomeActivity.this, "Login to see the past orders",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        accountIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(Logined){
                    //Account Fragment

                    //Set menu button color
                    bottomMenuBar.setVisibility(view.VISIBLE);
                    restaurantIcon.setAlpha((float) 0.32);
                    ordersIcon.setAlpha((float) 0.32);
                    accountIcon.setAlpha((float) 0.64);

                    //Set search bar visibility
                    topSearchBar.setVisibility(view.VISIBLE);
                    cancel.setVisibility(View.INVISIBLE);
                    //Set search bar visibility
                    searchBar.setHint("Search for help");

                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.homeCenteredfragment, AccountPageFragment.class, null)
                            .setReorderingAllowed(true)
                            .addToBackStack(null)
                            .commit();
                }else{
                    //Account Fragment

                    //Invisible bottom bar
                    bottomMenuBar.setVisibility(view.INVISIBLE);

                    //Invisible top search bar
                    topSearchBar.setVisibility(view.INVISIBLE);
                    cancel.setVisibility(View.VISIBLE);

                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.homeCenteredfragment, LoginFragment.class, null)
                            .setReorderingAllowed(true)
                            .addToBackStack(null)
                            .commit();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Set menu button color
                bottomMenuBar.setVisibility(view.VISIBLE);
                restaurantIcon.setAlpha((float) 0.64);
                ordersIcon.setAlpha((float) 0.32);
                accountIcon.setAlpha((float) 0.32);

                //Set hint text in search bar
                searchBar.setHint("Search Restaurants");

                //Set search bar visibility
                cancel.setVisibility(View.INVISIBLE);
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
    }

    @Override
    public void onBackPressed() {
        return;
    }

    public static void writeToFile(String data, String file, Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(file, Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
            Log.d("Successful", "File write successful: ");
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    public static String readFromFile(String file, Context context) {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput(file);

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
            Log.d("Successful", "File read successful: ");
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }

    public String[] login(String username, String password) {
        if (username.equals("")) {
        } else {
            if (password.equals("")) {
            } else {
                String[][] accounts = readData();

                if(accounts != null) {
                    for (int i = 0; i < accounts.length; i++) {
                        if (accounts[i][0].equals(username) && accounts[i][5].equals(password)) {
                            return accounts[i];
                        }
                    }
                }else{
                    return null;
                }
            }
        }
        Toast.makeText(this, "No account found with that username and password", Toast.LENGTH_SHORT).show();
        return null;
    }

    public String[][] readData() {
        try {
            FileInputStream fis = openFileInput("users.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);

            ArrayList<String> accountsRaw = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                accountsRaw.add(line);
            }

            String[][] accounts = new String[accountsRaw.size()][6];
            for (int i = 0; i < accountsRaw.size(); i++) {
                accounts[i] = accountsRaw.get(i).split(",");
            }
            return accounts;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}