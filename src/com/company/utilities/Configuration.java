package com.company.utilities;

import java.util.logging.Level;

public class Configuration {

    public static int orderId = 1; // Zmienna symulująca ID zamówień
    public static int dayNumber = 0; // Zmienna symulująca dzień

    public static final Level LOG_LEVEl = Level.ALL; // Konfigurowalny poziom logowania

    public static final int ORDER_TRESHOLD = 100; // Próg zamówień, które może wykonać cukiernia w ciągu doby
    public static final int CUSTOMER_ORDER_CAKE_RANDOM_BOUND = 5; // Zakres losowej liczby zamówionych wypieków przez klienta

    // Zmienne poniżej podaję w sekundach

    public static final int CUSTOMER1_ORDER_INTERVAL = 1; // Opóźnienie zamówienia pierwszego klienta
    public static final int CUSTOMER2_ORDER_INTERVAL = 2; // Opóźnienie zamówienia drugiego klienta
    public static final int CUSTOMER3_ORDER_INTERVAL = 3; // Opóźnienie zamówienia trzeciego klienta
    public static final int CUSTOMER_FAILD_ORDER_INTERVAL = 5; // Opóźnienie po nieudanym zamówieniu dla wszystkich klientów

    public static final int TIME_OF_DAY = 30; // Symulowany czas doby

}