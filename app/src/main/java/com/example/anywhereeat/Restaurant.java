package com.example.anywhereeat;

public class Restaurant {
    String restaurantName;
    int logo;
    int background;

    public Restaurant(String restaurantName, int logo, int background) {
        this.restaurantName = restaurantName;
        this.logo = logo;
        this.background = background;
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
