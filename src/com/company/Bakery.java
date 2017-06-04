package com.company;

import com.company.exceptions.ClosedDayException;
import com.company.orders.Order;
import com.company.orders.SubmittedOrder;
import com.company.utilities.Configuration;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class Bakery extends Thread {

    private String name = "Cukiernia Strzelecki Sp. K.";
    Logger logger = Logger.getLogger("Bakery.Bakery");
    private static Bakery bakery;
    private int timer = 0;
    private boolean dayOpen = true;

    private Bakery(){ }

    static Bakery getInstance() {
        if (null == bakery) {
            bakery = new Bakery();
        }
        return bakery;
    }

    private LinkedList<Order> prepairdOrders = new LinkedList<>();
    private HashMap<Integer, Order> realizedOrders = new HashMap<Integer, Order>();

    private Baker baker = new Baker(prepairdOrders, realizedOrders);

    void submitOrder(SubmittedOrder submittedOrder) throws ClosedDayException {
        if (dayOpen) {
            baker.confirmOrder(submittedOrder);
            System.out.println(getBakeryStatus());
        } else {
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
            return collectOrder;
        } else {
            throw new ClosedDayException("Day is closed. Try tomorrow");
        }
    }

    private void produce() throws InterruptedException {
        baker.realizeOrders();
        makeHtmlReport();
        openDay();
    }

    @Override
    public void run() {
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

    private void closeDay(){
        dayOpen = false;
    }

    private void openDay(){
        Configuration.DAY_NUMBER++;
        dayOpen = true;
    }

    private void makeHtmlReport() {
        // Tu będzie raport
    }

    public String getBakeryStatus() {
        StringBuilder info = new StringBuilder();
        info.append("### ").append(name).append(" ###").append(" dzień ").append(Configuration.DAY_NUMBER).append("\n");
        info.append(String.format("Ilość zamówień do zrobienia: %s \n", prepairdOrders.size()));
        info.append(String.format("Ilość zamówień do wydania: %s \n", realizedOrders.size()));
        return info.toString();
    }
}