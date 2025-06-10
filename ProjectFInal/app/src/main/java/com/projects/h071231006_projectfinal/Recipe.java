package com.projects.h071231006_projectfinal;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Recipe implements Parcelable {

    @SerializedName("idMeal")
    private String id;

    @SerializedName("strMeal")
    private String title;

    @SerializedName("strInstructions")
    private String instructions;

    @SerializedName("strMealThumb")
    private String imageUrl;

    private String ingredients;

    @SerializedName("strIngredient1") private String ingredient1;
    @SerializedName("strIngredient2") private String ingredient2;
    @SerializedName("strIngredient3") private String ingredient3;
    @SerializedName("strIngredient4") private String ingredient4;
    @SerializedName("strIngredient5") private String ingredient5;
    @SerializedName("strIngredient6") private String ingredient6;
    @SerializedName("strIngredient7") private String ingredient7;
    @SerializedName("strIngredient8") private String ingredient8;
    @SerializedName("strIngredient9") private String ingredient9;
    @SerializedName("strIngredient10") private String ingredient10;
    @SerializedName("strIngredient11") private String ingredient11;
    @SerializedName("strIngredient12") private String ingredient12;
    @SerializedName("strIngredient13") private String ingredient13;
    @SerializedName("strIngredient14") private String ingredient14;
    @SerializedName("strIngredient15") private String ingredient15;
    @SerializedName("strIngredient16") private String ingredient16;
    @SerializedName("strIngredient17") private String ingredient17;
    @SerializedName("strIngredient18") private String ingredient18;
    @SerializedName("strIngredient19") private String ingredient19;
    @SerializedName("strIngredient20") private String ingredient20;

    @SerializedName("strMeasure1") private String measure1;
    @SerializedName("strMeasure2") private String measure2;
    @SerializedName("strMeasure3") private String measure3;
    @SerializedName("strMeasure4") private String measure4;
    @SerializedName("strMeasure5") private String measure5;
    @SerializedName("strMeasure6") private String measure6;
    @SerializedName("strMeasure7") private String measure7;
    @SerializedName("strMeasure8") private String measure8;
    @SerializedName("strMeasure9") private String measure9;
    @SerializedName("strMeasure10") private String measure10;
    @SerializedName("strMeasure11") private String measure11;
    @SerializedName("strMeasure12") private String measure12;
    @SerializedName("strMeasure13") private String measure13;
    @SerializedName("strMeasure14") private String measure14;
    @SerializedName("strMeasure15") private String measure15;
    @SerializedName("strMeasure16") private String measure16;
    @SerializedName("strMeasure17") private String measure17;
    @SerializedName("strMeasure18") private String measure18;
    @SerializedName("strMeasure19") private String measure19;
    @SerializedName("strMeasure20") private String measure20;

    public Recipe() {}

    protected Recipe(Parcel in) {
        id = in.readString();
        title = in.readString();
        ingredients = in.readString();
        instructions = in.readString();
        imageUrl = in.readString();
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(ingredients);
        dest.writeString(instructions);
        dest.writeString(imageUrl);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getInstructions() { return instructions; }
    public void setInstructions(String instructions) { this.instructions = instructions; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getIngredients() { return ingredients; }
    public void setIngredients(String ingredients) { this.ingredients = ingredients; }

    public void buildIngredients() {
        StringBuilder builder = new StringBuilder();

        String[] ingredientsArray = {
                ingredient1, ingredient2, ingredient3, ingredient4, ingredient5,
                ingredient6, ingredient7, ingredient8, ingredient9, ingredient10,
                ingredient11, ingredient12, ingredient13, ingredient14, ingredient15,
                ingredient16, ingredient17, ingredient18, ingredient19, ingredient20
        };

        String[] measuresArray = {
                measure1, measure2, measure3, measure4, measure5,
                measure6, measure7, measure8, measure9, measure10,
                measure11, measure12, measure13, measure14, measure15,
                measure16, measure17, measure18, measure19, measure20
        };

        for (int i = 0; i < ingredientsArray.length; i++) {
            String ing = ingredientsArray[i];
            String meas = measuresArray[i];

            if (ing != null && !ing.trim().isEmpty()) {
                builder.append("- ")
                        .append(meas != null ? meas.trim() : "")
                        .append(" ")
                        .append(ing.trim())
                        .append("\n");
            }
        }

        this.ingredients = builder.toString().trim();
    }
}