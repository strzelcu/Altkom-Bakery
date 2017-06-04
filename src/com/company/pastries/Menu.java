package com.company.pastries;

import com.company.utilities.Randomize;

public enum Menu {

    /**
     * Piekarnia po dodaniu nowego wypieku powinna dodać go również do menu
     */

    CHERRY_CAKE,
    CHOCHOLATE_STRAWBERRY_CAKE,
    FOREST_FRUITS_CAKE;

    private static Menu[] menuItems = {
            CHERRY_CAKE,
            CHOCHOLATE_STRAWBERRY_CAKE,
            FOREST_FRUITS_CAKE
    };

    public static Menu getRandomMenuItem() {
        return menuItems[Randomize.nextInt(menuItems.length)];
    }

    public static Cake getRecipe(Menu menuItem, Size size) {
        switch (menuItem) {
            case CHERRY_CAKE: {
                return new CherryCake(size);
            }
            case FOREST_FRUITS_CAKE: {
                return new ForestFruitsCake(size);
            }
            case CHOCHOLATE_STRAWBERRY_CAKE: {
                return new ChocholateStrawberryCake(size);
            }
            default: return null;
        }
    }
}
