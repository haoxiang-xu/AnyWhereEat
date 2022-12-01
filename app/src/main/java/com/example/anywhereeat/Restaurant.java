package com.example.anywhereeat;

import java.io.Serializable;

public class Restaurant implements Serializable {
    String restaurantName;
    int logo;
    int background;
    public String[] itemNames;
    public int[] itemImages;
    public double[] prices;

    public Restaurant(String restaurantName, int logo, int background, String[] menu_names, int[] item_icons, double[] item_prices) {
        this.restaurantName = restaurantName;
        this.logo = logo;
        this.background = background;

        itemNames = new String[menu_names.length];
        itemImages = new int[item_icons.length];
        prices = new double[item_prices.length];

        for(int i = 0; i < menu_names.length; i++){
            itemNames[i] = menu_names[i];
        }
        for(int i = 0; i < item_icons.length; i++){
            itemImages[i] = item_icons[i];
        }
        for(int i = 0; i < item_prices.length; i++){
            prices[i] = item_prices[i];
        }
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public int getLogo() {
        return logo;
    }

    public int getBackground() {
        return background;
    }
}
