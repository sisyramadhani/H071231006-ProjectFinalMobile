package com.projects.h071231006_projectfinal;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.projects.h071231006_projectfinal.RecipeAdapter;
import com.projects.h071231006_projectfinal.FavoriteDbHelper;
import com.projects.h071231006_projectfinal.Recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class FavoritesFragment extends Fragment {

    private RecyclerView recyclerViewFavorites;
    private RecipeAdapter recipeAdapter;
    private TextView textViewNoFavorites;
    private FavoriteDbHelper favoriteDbHelper;

    private Executor executor = Executors.newSingleThreadExecutor();
    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_favorites, container, false);

        recyclerViewFavorites = root.findViewById(R.id.recyclerViewFavorites);
        textViewNoFavorites = root.findViewById(R.id.textViewNoFavorites);
        favoriteDbHelper = new FavoriteDbHelper(getContext());

        recipeAdapter = new RecipeAdapter(getContext(), new ArrayList<>());
        recyclerViewFavorites.setAdapter(recipeAdapter);

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadFavorites();
    }

    private void loadFavorites() {
        executor.execute(() -> {
            List<Recipe> favorites = favoriteDbHelper.getAllFavorites();

            handler.post(() -> {
                if (favorites.isEmpty()) {
                    textViewNoFavorites.setVisibility(View.VISIBLE);
                    recyclerViewFavorites.setVisibility(View.GONE);
                } else {
                    textViewNoFavorites.setVisibility(View.GONE);
                    recyclerViewFavorites.setVisibility(View.VISIBLE);
                    recipeAdapter.updateList(favorites);
                }
            });
        });
    }
}
