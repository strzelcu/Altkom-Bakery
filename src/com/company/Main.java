package com.company;

import com.company.exceptions.ClosedDayException;
import com.company.pastries.Cake;
import com.company.pastries.ChocholateStrawberryCake;
import com.company.pastries.Size;
import com.company.utilities.Configuration;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

//        excerciseOne();
//        excerciseTwo();
        excerciseThree();
    }

    private static void excerciseOne() {
        Cake cake = new ChocholateStrawberryCake(Size.BIG);
        System.out.println(cake.getInfo());
    }

    private static void excerciseTwo() {
        Bakery bakery = Bakery.getInstance();

        Customer customer1 = new Customer("Jeronimo Martins Polska S.A.",
                "zamowienia@biedronka.eu",
                "(22)2053301",
                bakery,
                Configuration.CUSTOMER1_ORDER_INTERVAL);

        Customer customer2 = new Customer("ALDI Sp. z o.o.",
                "zamowienia@aldi.pl",
                "774041090",
                bakery,
                Configuration.CUSTOMER2_ORDER_INTERVAL);

        Customer customer3 = new Customer("POLOmarket Sp. z o.o.",
                "zamowienia@polomarket.pl",
                "324044393",
                bakery,
                Configuration.CUSTOMER3_ORDER_INTERVAL);

        try {
            customer1.orderPastries();
            customer2.orderPastries();
            customer3.orderPastries();
        } catch (ClosedDayException e) {
            e.printStackTrace();
        }
    }

    static void excerciseThree() {
        Bakery bakery = Bakery.getInstance();
        bakery.start();

        Customer customer1 = new Customer("Jeronimo Martins Polska S.A.",
                "zamowienia@biedronka.eu",
                "(22)2053301",
                bakery,
                Configuration.CUSTOMER1_ORDER_INTERVAL);
        customer1.start();

        Customer customer2 = new Customer("ALDI Sp. z o.o.",
                "zamowienia@aldi.pl",
                "774041090",
                bakery,
                Configuration.CUSTOMER2_ORDER_INTERVAL);
        customer2.start();

        Customer customer3 = new Customer("POLOmarket Sp. z o.o.",
                "zamowienia@polomarket.pl",
                "324044393",
                bakery,
                Configuration.CUSTOMER3_ORDER_INTERVAL);
        customer3.start();
    }
}