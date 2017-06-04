package com.company;

import com.company.orders.*;
import com.company.pastries.*;
import com.company.utilities.Configuration;
import com.company.utilities.DateStamp;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.logging.Logger;

class Baker{

    private Logger logger = Logger.getLogger("com.company.Baker");
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
                    (int) ((prepairdOrders.size() / Configuration.ORDER_TRESHOLD) + 1)));
        }
        confirmationInfo.append("###### Koniec potwierdzenia ######\n");
        logger.info(String.format("Baker confirmed order nr %s to customer %s",
                prepairdOrder.getId(),
                prepairdOrder.getCustomerName()));
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
        logger.info(String.format("Baker prepared order for %s / Details: %s",
                prepairdOrder.getCustomerName(),
                prepairdOrder.getShortInfo()));
        return prepairdOrder;
    }

    void realizeOrders() {

        double totalDailyIncome = 0;
        int totalDailyMadeOrders = 0;
        int totalDailyOrdersToDeliver = 0;
        int totalDailyOrdersToGiveOnSpot = 0;

        logger.info("Baker start to make cakes");
        int orderTreshold;
        if(prepairdOrders.size() < Configuration.ORDER_TRESHOLD){
            orderTreshold = prepairdOrders.size();
        } else {
            orderTreshold = Configuration.ORDER_TRESHOLD;
        }
        for (int i = 0; i < orderTreshold; i++) {
            Order order = prepairdOrders.getFirst();
            logger.fine(String.format("Baker will make order no %s",
                    order.getId()));
            while (!order.isDone()){
                for (Cake cake :
                        order.getOrderedCakes()) {
                    logger.finest(String.format("Baker made cake: %s",
                            cake.getInfo()));
                    System.out.println(cake.make());
                }
            }
            logger.fine(String.format("Baker made order no %s",
                    order.getId()));
            System.out.println(String.format("Zamówienie nr %s z godz %s od klienta %s wykonane",
                    prepairdOrders.getFirst().getId(),
                    DateStamp.getStringFromDate(prepairdOrders.getFirst().getOrderDate()),
                    prepairdOrders.getFirst().getCustomerName()));
            realizedOrders.put(order.getId(), order);
            prepairdOrders.removeFirst();
            notifyCustomer(order);
            System.out.println("\n");

            totalDailyIncome += order.getTotalPrice();
            totalDailyMadeOrders++;
            if(order.mustDeliverToCustomer()){
                totalDailyOrdersToDeliver++;
            } else {
                totalDailyOrdersToGiveOnSpot++;
            }

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        logger.info("Baker ended making cakes");
        logger.severe(new StringBuilder().append("Summary of the ")
                .append(Configuration.dayNumber).append(" day \n")
                .append(" Expected income: ").append(totalDailyIncome).append(" PLN\n")
                .append(" Orders made today: ").append(totalDailyMadeOrders).append("\n")
                .append(" Orders to deliver: ").append(totalDailyOrdersToDeliver).append("\n")
                .append(" Other orders: ").append(totalDailyOrdersToGiveOnSpot).append("\n")
                .toString());
    }

    private void notifyCustomer(Order order){
        logger.info(String.format("Customer %s was informed about finished order", order.getCustomerName()));
        System.out.println(String.format("Wysłano email do klienta %s o gotowym zamówieniu", order.getCustomerName()));
        System.out.println(String.format("Wysłano SMS do klienta %s o gotowym zamówieniu", order.getCustomerName()));
        if(order.mustDeliverToCustomer()){
            logger.fine(String.format("Order no %s was packed to truck", order.getId()));
            System.out.println(String.format("Zamówienie nr %s zostało załadowane na pojazd dowożący", order.getId()));
            realizedOrders.remove(order.getId());
        } else {
            order.getCustomerNotifyChannel().updateOrdersToCollect(order.getId());
        }
    }
}