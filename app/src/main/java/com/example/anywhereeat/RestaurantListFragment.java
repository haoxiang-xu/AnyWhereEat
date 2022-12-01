package com.example.anywhereeat;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RestaurantListFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class RestaurantListFragment extends Fragment {

    RecyclerView restaurantList;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RestaurantListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RestaurantListFragment newInstance(String param1, String param2) {
        RestaurantListFragment fragment = new RestaurantListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public RestaurantListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_restaurant_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Initialize restaurant data
        HomeActivity.restaurants = new ArrayList<>();
        loadRestaurantData();

        //Construct restaurant recyclerview
        restaurantList = view.findViewById(R.id.restaurantsListRecyclerView);
        RestaurantRecyclerViewAdapter restaurantListAdapter = new RestaurantRecyclerViewAdapter(getContext(), HomeActivity.restaurants);
        restaurantList.setAdapter(restaurantListAdapter);
        restaurantList.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void loadRestaurantData(){
        String[] restaurantNames = getResources().getStringArray(R.array.restaurantNames);

        String[][] restaurantMenuNames = {{}, HomeActivity.mcdonalds_item, HomeActivity.kfc_item, HomeActivity.starbucks_item, {}, {}, {}, {}};
        double[][] restaurantMenuPrices = {{}, HomeActivity.mcdonalds_price, HomeActivity.kfc_price, HomeActivity.starbucks_price, {}, {}, {}, {}};
        int[][] restaurantMenuIcons = {{}, HomeActivity.mcdonalds_menu, HomeActivity.kfc_menu, HomeActivity.startbucks_menu, {}, {}, {}, {}};

        for(int i = 0; i < restaurantNames.length; i++){
            HomeActivity.restaurants.add(new Restaurant(restaurantNames[i],
                                                        HomeActivity.restaurantLogos[i],
                                                        HomeActivity.restaurantBackgrounds[i],
                                                        restaurantMenuNames[i],
                                                        restaurantMenuIcons[i],
                                                        restaurantMenuPrices[i]));
        }
    }

}