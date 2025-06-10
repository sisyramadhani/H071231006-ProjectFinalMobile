package com.projects.h071231006_projectfinal;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class MealsResponse {
    @SerializedName("meals")
    private List<Recipe> meals;

    public List<Recipe> getMeals() {
        return meals;
    }

    public void setMeals(List<Recipe> meals) {
        this.meals = meals;
    }
}