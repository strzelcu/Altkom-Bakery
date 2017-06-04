package com.company;

import com.company.orders.*;
import com.company.pastries.*;
import com.company.utilities.Configuration;
import com.company.utilities.DateStamp;

import java.util.HashMap;
import java.util.LinkedList;

class Baker{

    private LinkedList<Order> prepairdOrders;
    private HashMap<Integer, Order> realizedOrders;

    Baker(LinkedList<Order> prepairdOrders, HashMap<Integer, Order> realizedOrders) {
        this.prepairdOrders = prepairdOrders;
        this.realizedOrders = realizedOrders;
    }

    void confirmOrder(SubmittedOrder order){
        StringBuilder confirmationInfo = new StringBuilder();
        confirmationInfo.append("###### Potwierdzenie przyjęcia zamówienia ######\n");
        Order prepairdOrder = prepareOrder(order);
        confirmationInfo.append(prepairdOrder.getInfo());
        prepairdOrders.add(prepairdOrder);
        if(prepairdOrders.size() / Configuration.ORDER_TRESHOLD < 1){
            confirmationInfo.append("Zamówienie do odbioru w dniu jutrzejszym\n");
        } else {
            confirmationInfo.append(String.format("Zamówienie będzie do odbioru za %s dni \n",
                    (int) prepairdOrders.size() / Configuration.ORDER_TRESHOLD + 1));
        }
        confirmationInfo.append("###### Koniec potwierdzenia ######\n");
        System.out.println(confirmationInfo.toString());
    }

    private synchronized Order prepareOrder(SubmittedOrder submittedOrder) {
        Order prepairdOrder = new Order();
        prepairdOrder.setId();
        prepairdOrder.setCustomerNotifyChannel(submittedOrder.getCustomerNotifyChannel());
        prepairdOrder.setCustomerName(submittedOrder.getCustomerName());
        prepairdOrder.setCustomerPhone(submittedOrder.getCustomerPhone());
        prepairdOrder.setCustomerEmail(submittedOrder.getCustomerEmail());
        prepairdOrder.setDeliverToCustomer(submittedOrder.isDeliverToCustomer());
        prepairdOrder.setOrderDate(submittedOrder.getOrderDate());
        prepairdOrder.setDistance(submittedOrder.getDistance());
        for (OrderItem orderItem : submittedOrder.orderItems){
            prepairdOrder.addCake(Menu.getRecipe(orderItem.getMenuItem(), orderItem.getSize()));
        }
        return prepairdOrder;
    }

    void realizeOrders() {
        int orderTreshold;
        if(prepairdOrders.size() < Configuration.ORDER_TRESHOLD){
            orderTreshold = prepairdOrders.size();
        } else {
            orderTreshold = Configuration.ORDER_TRESHOLD;
        }
        for (int i = 0; i < orderTreshold; i++) {
            Order order = prepairdOrders.getFirst();
            while (!order.isDone()){
                for (Cake cake :
                        order.getOrderedCakes()) {
                    System.out.println(cake.make());
                }
            }
            System.out.println(String.format("Zamówienie nr %s z godz %s od klienta %s wykonane",
                    prepairdOrders.getFirst().getId(),
                    DateStamp.getStringFromDate(prepairdOrders.getFirst().getOrderDate()),
                    prepairdOrders.getFirst().getCustomerName()));
            realizedOrders.put(order.getId(), order);
            prepairdOrders.removeFirst();
            notifyCustomer(order);
            System.out.println("\n");
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void notifyCustomer(Order order){
        System.out.println(String.format("Wysłano email do klienta %s o gotowym zamówieniu", order.getCustomerName()));
        System.out.println(String.format("Wysłano SMS do klienta %s o gotowym zamówieniu", order.getCustomerName()));
        if(order.mustDeliverToCustomer()){
            System.out.println(String.format("Zamówienie nr %s zostało załadowane na pojazd dowożący", order.getId()));
            realizedOrders.remove(order.getId());
        } else {
            order.getCustomerNotifyChannel().updateOrdersToCollect(order.getId());
        }
    }
}