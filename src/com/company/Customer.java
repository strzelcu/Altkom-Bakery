package com.company;

import com.company.exceptions.ClosedDayException;
import com.company.orders.OrderItem;
import com.company.orders.SubmittedOrder;
import com.company.pastries.Menu;
import com.company.pastries.Size;
import com.company.utilities.Configuration;
import com.company.utilities.Randomize;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

public class Customer {

    private String name;
    private String email;
    private String phoneNumber;
    private Bakery bakery;
    private int interval;

    LinkedList<Integer> ordersToCollect = new LinkedList<>();

    Customer(String name, String email, String phoneNumber, Bakery bakery, int orderInterval) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.bakery = bakery;
        this.interval = orderInterval;
    }

    void orderPastries() throws ClosedDayException {
        System.out.println(String.format("Klient %s rozpoczyna składanie zamówienia", name));
        SubmittedOrder preorder = new SubmittedOrder(name, phoneNumber, email);
        for(int i = 0; i < Randomize.nextInt(Configuration.CUSTOMER_ORDER_CAKE_RANDOM_BOUND)+1; i++){
            preorder.addOrderItem(new OrderItem(
                    Menu.getRandomMenuItem(),
                    Size.getRandomSize()));
        }
        preorder.setDeliverToCustomer(Randomize.nextBoolean());
        preorder.setDistance(Randomize.nextInt(60));
        preorder.setCustomerNotifyChannel(this);
        bakery.submitOrder(preorder);
    }

    void collectPastries() throws ClosedDayException {
        if(ordersToCollect.size() > 0) {
            System.out.println(String.format("Klient %s odbiera zamówienie nr %s", name,
                    ordersToCollect.getFirst()));
            bakery.getOrder(ordersToCollect.getFirst());
            ordersToCollect.removeFirst();
        }
    }

    public void updateOrdersToCollect(int orderID){
        ordersToCollect.add(orderID);
    }

    public void start(){
        startCollectingOrders();
        startMakingOrders();
    }

    private void startMakingOrders() {
        Thread makingThread = new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(TimeUnit.SECONDS.toMillis(interval));
                        orderPastries();
                    } catch (ClosedDayException e) {
                        try {
                            System.out.println(String.format("Cukiernia zamknięta. Klient %s czeka na złożenie zamówienia", name));
                            Thread.sleep(TimeUnit.SECONDS.toMillis(Configuration.CUSTOMER_FAILD_ORDER_INTERVAL));
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        makingThread.start();
    }

    private void startCollectingOrders(){
        Thread collectThread = new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(TimeUnit.SECONDS.toMillis(interval));
                        collectPastries();
                    } catch (ClosedDayException e) {
                        try {
                            System.out.println(String.format("Cukiernia zamknięta. Klient %s czeka na odbiór zamówienia", name));
                            Thread.sleep(TimeUnit.SECONDS.toMillis(Configuration.CUSTOMER_FAILD_ORDER_INTERVAL));
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        collectThread.start();
    }

    public String getInfo() {
        StringBuilder info = new StringBuilder();
        info.append("Dane klienta: \n");
        info.append(name).append(" \n");
        info.append(phoneNumber).append(" \n");
        info.append(email).append(" \n");
        return info.toString();
    }
}