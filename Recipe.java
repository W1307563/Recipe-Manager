package com.example.cmis202project;

public class Recipe {
    private String name;
    private String ingredients;
    private String instructions;
    private int servings;

    public Recipe() {

    }
    public Recipe(String name, String ingredients, String instructions, int servings) {
        this.name = name;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.servings = servings;
    }

    public Recipe(int id, String name, String ingredients, String instructions, int servings) {
        this.name = name;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.servings = servings;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "name='" + name + '\'' +
                ", ingredients='" + ingredients + '\'' +
                ", instructions='" + instructions + '\'' +
                ", servings=" + servings +
                '}';
    }
}


