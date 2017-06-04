package com.company.pastries;

import com.company.pastries.ingredients.ChocolateTopping;
import com.company.pastries.ingredients.ForestFruits;
import com.company.pastries.ingredients.SpongeCake;

public class ForestFruitsCake extends Cake{

    public ForestFruitsCake(Size size) {
        name = "Tort z owocami leśnymi";
        priceOfSmall = 60;
        priceOfBig = 101;
        setSize(size);
        ingredients.add(new SpongeCake());
        ingredients.add(new ForestFruits());
        ingredients.add(new ChocolateTopping());
    }
}
