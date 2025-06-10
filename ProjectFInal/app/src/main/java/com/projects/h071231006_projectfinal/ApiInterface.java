package com.projects.h071231006_projectfinal;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("search.php?s=")
    Call<MealsResponse> getAllRecipes();

    @GET("search.php")
    Call<MealsResponse> searchRecipes(@Query("s") String query);
}