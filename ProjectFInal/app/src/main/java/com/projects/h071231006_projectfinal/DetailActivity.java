package com.projects.h071231006_projectfinal;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DetailActivity extends AppCompatActivity {

    private ImageView imageViewRecipe;
    private TextView textViewRecipeTitle, textViewIngredients, textViewInstructions;
    private FloatingActionButton fabFavorite;
    private FavoriteDbHelper favoriteDbHelper;
    private Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        imageViewRecipe = findViewById(R.id.imageViewRecipeDetail);
        textViewRecipeTitle = findViewById(R.id.textViewRecipeTitle);
        textViewIngredients = findViewById(R.id.textViewIngredients);
        textViewInstructions = findViewById(R.id.textViewInstructions);
        fabFavorite = findViewById(R.id.fabFavorite);

        favoriteDbHelper = new FavoriteDbHelper(this);

        recipe = getIntent().getParcelableExtra("recipe");

        if (recipe != null) {
            displayRecipeDetails();
            updateFavoriteButton();
        } else {
            Toast.makeText(this, "Failed to load recipe", Toast.LENGTH_SHORT).show();
            finish();
        }

        fabFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFavorite();
            }
        });
    }

    private void displayRecipeDetails() {
        textViewRecipeTitle.setText(recipe.getTitle());
        textViewIngredients.setText(recipe.getIngredients());
        textViewInstructions.setText(recipe.getInstructions());

        String imageUrl = recipe.getImageUrl();
        if (imageUrl != null && !imageUrl.trim().isEmpty()) {
            Glide.with(this)
                    .load(imageUrl)
                    .placeholder(R.drawable.ic_placeholder)
                    .error(R.drawable.ic_placeholder)
                    .into(imageViewRecipe);
        } else {
            imageViewRecipe.setImageResource(R.drawable.ic_placeholder);
        }
    }

    private void updateFavoriteButton() {
        int iconRes = favoriteDbHelper.isFavorite(recipe.getId())
                ? R.drawable.ic_favorite
                : R.drawable.ic_favorite_border;

        fabFavorite.setImageDrawable(ContextCompat.getDrawable(this, iconRes));
    }

    private void toggleFavorite() {
        boolean isFav = favoriteDbHelper.isFavorite(recipe.getId());

        if (isFav) {
            favoriteDbHelper.removeFavorite(recipe.getId());
            fabFavorite.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite_border));
            Toast.makeText(getApplicationContext(), "Removed from favorites", Toast.LENGTH_SHORT).show();
        } else {
            favoriteDbHelper.addFavorite(recipe);
            fabFavorite.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite));
            Toast.makeText(getApplicationContext(), "Added to favorites", Toast.LENGTH_SHORT).show();
        }
    }
}