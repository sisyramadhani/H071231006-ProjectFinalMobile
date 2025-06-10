package com.projects.h071231006_projectfinal;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private final Context context;
    private List<Recipe> recipeList;

    public RecipeAdapter(Context context, List<Recipe> recipeList) {
        this.context = context;
        this.recipeList = recipeList;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recipe, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe recipe = recipeList.get(position);

        holder.textViewTitle.setText(recipe.getTitle());

        String ingredients = recipe.getIngredients();
        if (ingredients == null || ingredients.trim().isEmpty()) {
            ingredients = "(No ingredients listed)";
        }
        holder.textViewIngredients.setText(ingredients);

        Glide.with(context)
                .load(recipe.getImageUrl())
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_placeholder)  // fallback jika gagal load
                .into(holder.imageViewRecipe);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("recipe", recipe);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return recipeList != null ? recipeList.size() : 0;
    }

    public void updateList(List<Recipe> newList) {
        this.recipeList = newList;
        notifyDataSetChanged();
    }

    static class RecipeViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle, textViewIngredients;
        ImageView imageViewRecipe;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewIngredients = itemView.findViewById(R.id.textViewIngredients);
            imageViewRecipe = itemView.findViewById(R.id.imageViewRecipe);
        }
    }
}