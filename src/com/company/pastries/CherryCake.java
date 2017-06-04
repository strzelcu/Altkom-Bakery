package com.company.pastries;

import com.company.pastries.ingredients.Cherries;
import com.company.pastries.ingredients.ChocolateChips;
import com.company.pastries.ingredients.CocoaSpongeCake;
import com.company.pastries.ingredients.WhippedCream;

public class CherryCake extends Cake {

    public CherryCake(Size size){
        name = "Tort Wiśniowy";
        priceOfSmall = 49;
        priceOfBig = 90;
        setSize(size);
        ingredients.add(new CocoaSpongeCake());
        ingredients.add(new WhippedCream());
        ingredients.add(new ChocolateChips());
        ingredients.add(new Cherries());
    }
}