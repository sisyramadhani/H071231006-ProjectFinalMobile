package com.projects.h071231006_projectfinal;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerViewRecipes;
    private RecipeAdapter recipeAdapter;
    private List<Recipe> recipeList = new ArrayList<>();
    private ProgressBar progressBar;
    private TextView textViewError;
    private SwipeRefreshLayout swipeRefreshLayout;
    private SearchView searchView;
    private ImageButton btnDarkMode;

    private Executor executor = Executors.newSingleThreadExecutor();
    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerViewRecipes = root.findViewById(R.id.recyclerViewRecipes);
        progressBar = root.findViewById(R.id.progressBar);
        textViewError = root.findViewById(R.id.textViewError);
        swipeRefreshLayout = root.findViewById(R.id.swipeRefreshLayout);
        searchView = root.findViewById(R.id.searchView);
        btnDarkMode = root.findViewById(R.id.btnDarkMode);

        recipeAdapter = new RecipeAdapter(getContext(), recipeList);
        recyclerViewRecipes.setAdapter(recipeAdapter);

        loadRecipes();

        swipeRefreshLayout.setOnRefreshListener(() -> {
            loadRecipes();
            swipeRefreshLayout.setRefreshing(false);
        });

        textViewError.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            textViewError.setVisibility(View.GONE);
            loadRecipes();
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchRecipes(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    loadRecipes();
                }
                return false;
            }
        });

        btnDarkMode.setOnClickListener(v -> {
            SharedPreferences preferences = requireContext().getSharedPreferences("settings", MODE_PRIVATE);
            boolean current = preferences.getBoolean("dark_mode", false);
            boolean newMode = !current;

            preferences.edit().putBoolean("dark_mode", newMode).apply();
            AppCompatDelegate.setDefaultNightMode(
                    newMode ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO
            );

            requireActivity().recreate();
        });

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        customizeSearchViewIconColor();
    }

    private void customizeSearchViewIconColor() {
        int color = ContextCompat.getColor(requireContext(), R.color.white);

        ImageView searchIcon = searchView.findViewById(androidx.appcompat.R.id.search_mag_icon);
        if (searchIcon != null) {
            searchIcon.setImageTintList(null);
            searchIcon.setColorFilter(color, android.graphics.PorterDuff.Mode.SRC_IN);
        }

        ImageView closeIcon = searchView.findViewById(androidx.appcompat.R.id.search_close_btn);
        if (closeIcon != null) {
            closeIcon.setImageTintList(null);
            closeIcon.setColorFilter(color, android.graphics.PorterDuff.Mode.SRC_IN);
        }

        ImageView submitIcon = searchView.findViewById(androidx.appcompat.R.id.search_go_btn);
        if (submitIcon != null) {
            submitIcon.setImageTintList(null);
            submitIcon.setColorFilter(color, android.graphics.PorterDuff.Mode.SRC_IN);
        }

        EditText searchText = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        if (searchText != null) {
            searchText.setTextColor(color);
        }
    }

    private void loadRecipes() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerViewRecipes.setVisibility(View.GONE);
        textViewError.setVisibility(View.GONE);

        executor.execute(() -> {
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<MealsResponse> call = apiInterface.getAllRecipes();

            call.enqueue(new Callback<MealsResponse>() {
                @Override
                public void onResponse(Call<MealsResponse> call, Response<MealsResponse> response) {
                    handler.post(() -> {
                        progressBar.setVisibility(View.GONE);

                        if (response.isSuccessful() && response.body() != null && response.body().getMeals() != null) {
                            recipeList.clear();
                            for (Recipe recipe : response.body().getMeals()) {
                                recipe.buildIngredients();
                                recipeList.add(recipe);
                            }
                            recipeAdapter.updateList(recipeList);
                            recyclerViewRecipes.setVisibility(View.VISIBLE);
                            textViewError.setVisibility(View.GONE);
                        } else {
                            textViewError.setVisibility(View.VISIBLE);
                            textViewError.setText("No data available. Tap to refresh.");
                            Toast.makeText(getContext(), "Failed to load recipes", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onFailure(Call<MealsResponse> call, Throwable t) {
                    handler.post(() -> {
                        progressBar.setVisibility(View.GONE);
                        textViewError.setVisibility(View.VISIBLE);
                        textViewError.setText("No data available. Tap to refresh.");
                        Toast.makeText(getContext(), "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    });
                }
            });
        });
    }

    private void searchRecipes(String query) {
        progressBar.setVisibility(View.VISIBLE);
        recyclerViewRecipes.setVisibility(View.GONE);
        textViewError.setVisibility(View.GONE);

        executor.execute(() -> {
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<MealsResponse> call = apiInterface.searchRecipes(query);

            call.enqueue(new Callback<MealsResponse>() {
                @Override
                public void onResponse(Call<MealsResponse> call, Response<MealsResponse> response) {
                    handler.post(() -> {
                        progressBar.setVisibility(View.GONE);

                        if (response.isSuccessful() && response.body() != null && response.body().getMeals() != null) {
                            List<Recipe> searchResults = new ArrayList<>();
                            for (Recipe recipe : response.body().getMeals()) {
                                recipe.buildIngredients();
                                searchResults.add(recipe);
                            }
                            recipeAdapter.updateList(searchResults);
                            recyclerViewRecipes.setVisibility(View.VISIBLE);

                            if (searchResults.isEmpty()) {
                                textViewError.setVisibility(View.VISIBLE);
                                textViewError.setText("No menu found with that name.");
                                Toast.makeText(getContext(), "No recipes found", Toast.LENGTH_SHORT).show();
                            } else {
                                textViewError.setVisibility(View.GONE);
                            }
                        } else {
                            textViewError.setVisibility(View.VISIBLE);
                            textViewError.setText("No menu found with that name.");
                            Toast.makeText(getContext(), "Failed to search recipes", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onFailure(Call<MealsResponse> call, Throwable t) {
                    handler.post(() -> {
                        progressBar.setVisibility(View.GONE);
                        textViewError.setVisibility(View.VISIBLE);
                        textViewError.setText("No data available. Tap to refresh.");
                        Toast.makeText(getContext(), "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    });
                }
            });
        });
    }
}