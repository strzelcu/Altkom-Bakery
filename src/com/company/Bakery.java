package com.company;

import com.company.exceptions.ClosedDayException;
import com.company.orders.Order;
import com.company.orders.SubmittedOrder;
import com.company.utilities.BakeryLogger;
import com.company.utilities.Configuration;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class Bakery extends Thread {

    private Logger logger = Logger.getLogger("com.company.Bakery");
    private String name = "Cukiernia Strzelecki Sp. K.";
    private static Bakery bakery;
    private int timer = 0;
    private boolean dayOpen = true;

    private Bakery(){
        try {
            BakeryLogger.setup();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static Bakery getInstance() {
        if (null == bakery) {
            bakery = new Bakery();
        }
        return bakery;
    }

    private LinkedList<Order> prepairdOrders = new LinkedList<>();
    private HashMap<Integer, Order> realizedOrders = new HashMap<>();

    private Baker baker = new Baker(prepairdOrders, realizedOrders);

    void submitOrder(SubmittedOrder submittedOrder) throws ClosedDayException {
        if (dayOpen) {
            baker.confirmOrder(submittedOrder);
            System.out.println(getBakeryStatus());
            logger.info(String.format("Bakery take order from %s / Details: %s",
                    submittedOrder.getCustomerName(),
                    submittedOrder.getInfo()));
        } else {
            logger.warning(String.format("Customer %s try to make order but bakery is closed for night",
                    submittedOrder.getCustomerName()));
            throw new ClosedDayException("Day is closed. Try tomorrow");
        }
    }

    synchronized Order getOrder(int id) throws ClosedDayException {
        if (dayOpen) {
            StringBuilder info = new StringBuilder();
            info.append(String.format("\n##### Zamówienie nr %s zostało wydane #######\n", id));
            Order collectOrder = realizedOrders.get(id);
            realizedOrders.remove(id);
            info.append(collectOrder.getShortInfo());
            info.append("##########################\n");
            System.out.println(info.toString());
            System.out.println(getBakeryStatus());
            logger.info(String.format("Bakery gave order no %s to customer %s for %s PLN",
                    collectOrder.getId(),
                    collectOrder.getCustomerName(),
                    collectOrder.getTotalPrice()));
            return collectOrder;
        } else {
            logger.warning(String.format("Customer %s try to make order but bakery is closed for night",
                    realizedOrders.get(id).getCustomerName()));
            throw new ClosedDayException("Day is closed. Try tomorrow");
        }
    }

    private void produce() throws InterruptedException {
        logger.severe("Pastries production started \n");
        baker.realizeOrders();
        logger.severe("Pastries production ended \n");
        try {
            openDay();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            openDay();
        } catch (IOException e) {
            e.printStackTrace();
        }
        while(true) {
            try {
                Thread.sleep(TimeUnit.SECONDS.toMillis(1));
                if(dayOpen){
                    if(timer == Configuration.TIME_OF_DAY) {
                        System.out.println("######################################################");
                        System.out.println("Koniec dnia. Piekarnia rozpoczyna wykonywanie zamówień.");
                        System.out.println();
                        closeDay();
                        Thread.sleep(5000); // Chwila przestoju aby pozwolić na przeczytanie komunikatu...
                        produce();
                        timer = 0;
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            timer++;
        }
    }

    private void openDay() throws IOException {
        Configuration.dayNumber++;
        dayOpen = true;
        BakeryLogger.setupHtmlLogger(String.format("report_day_%s.html", Configuration.dayNumber));
        logger.severe("Bakery opened day");
    }

    private void closeDay(){
        logger.severe("Bakery closed day");
        dayOpen = false;
    }

    private String getBakeryStatus() {
        String info = "### " + name + " ###" + " dzień " + Configuration.dayNumber + "\n" +
                String.format("Ilość zamówień do zrobienia: %s \n", prepairdOrders.size()) +
                String.format("Ilość zamówień do wydania: %s \n", realizedOrders.size());
        return info;
    }
}