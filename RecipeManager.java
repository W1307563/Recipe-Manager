package com.example.cmis202project;

import javafx.collections.FXCollections;

import java.util.ArrayList;


public class RecipeManager extends Recipe {
    private ArrayList<Recipe> recipes;

    public RecipeManager() {
        recipes = new ArrayList<>();
    }

    public void addRecipe(Recipe recipe) {
        recipes.add(recipe);
    }

    public void removeRecipe(Recipe recipe) {
        recipes.remove(recipe);
    }

    public ArrayList<Recipe> getRecipes() {
        return recipes;
    }

}

