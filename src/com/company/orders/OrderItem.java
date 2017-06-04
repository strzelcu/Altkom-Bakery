package com.company.orders;

import com.company.pastries.Menu;
import com.company.pastries.Size;

public class OrderItem {

    private Menu menuItem;
    private Size size;

    public Menu getMenuItem() {
        return menuItem;
    }

    public Size getSize() {
        return size;
    }

    public OrderItem(Menu menuItem, Size size){
        this.menuItem = menuItem;
        this.size = size;
    }

    public String getInfo() {
        StringBuilder info = new StringBuilder();
        info.append(String.format(" Pozycja menu: %s \n", menuItem));
        info.append(String.format(" Rozmiar: %s \n", size));
        return info.toString();
    }
}
