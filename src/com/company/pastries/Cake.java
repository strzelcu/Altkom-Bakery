package com.company.pastries;

import com.company.pastries.ingredients.Ingredient;

import java.util.ArrayList;

public abstract class Cake {

    public String name;
    public Size size;
    public double priceOfBig;
    public double priceOfSmall;
    private boolean isDone = false;

    ArrayList<Ingredient> ingredients = new ArrayList();

    public Cake() { }

    public void setSize(Size size){
        this.size = size;
    }

    public void addIngredient(Ingredient ingredient) {
        ingredients.add(ingredient);
    }

    public void removeIngredient(Ingredient ingredient){
        ingredients.add(ingredient);
    }

    public double getPrice(){
        switch (size){
            case SMALL: {
                return priceOfSmall;
            }
            case BIG: {
                return priceOfBig;
            }
            default: return 0;
        }
    }

    public String make() {
        isDone = true;
        return String.format("Ciasto: %s zostało wykonane", name);
    }

    public boolean getStatus(){
        return isDone;
    }

    public String getInfo() {

        StringBuilder info = new StringBuilder();
        info.append(String.format("  Ciasto: %s \n", name));
        info.append(String.format("   Rozmiar: %s \n", size.toString()));
        info.append("   Składniki: \n");
        for (Ingredient ingredient: ingredients) {
            info.append(String.format("    * %s \n", ingredient.getInfo()));
        }
        info.append(String.format("   Cena: %s PLN\n\n", getPrice()));
        return info.toString();
    }
}