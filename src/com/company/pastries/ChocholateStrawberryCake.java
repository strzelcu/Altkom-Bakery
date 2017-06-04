package com.company.pastries;

import com.company.pastries.ingredients.Cherries;
import com.company.pastries.ingredients.ChocholateSpongeCake;
import com.company.pastries.ingredients.Strawberries;
import com.company.pastries.ingredients.StrawberryCream;

public class ChocholateStrawberryCake extends Cake{

    public ChocholateStrawberryCake(Size size) {
        name = "Tort czekoladowo-truskawkowy";
        priceOfSmall = 55;
        priceOfBig = 85;
        setSize(size);
        ingredients.add(new ChocholateSpongeCake());
        ingredients.add(new StrawberryCream());
        ingredients.add(new Cherries());
        ingredients.add(new Strawberries());
    }
}
