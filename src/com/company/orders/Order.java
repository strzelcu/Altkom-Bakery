package com.company.orders;

import com.company.Customer;
import com.company.pastries.Cake;
import com.company.utilities.Configuration;
import com.company.utilities.DateStamp;
import java.util.ArrayList;
import java.util.Date;

public class Order {

    private int id;
    private String customerName;
    private String customerPhone;
    private String customerEmail;
    private boolean deliverToCustomer;
    private Date orderDate;
    private int distance;
    private Customer customer;

    private ArrayList<Cake> orderedCakes = new ArrayList<>();

    public void setId() {
        this.id = Configuration.ORDER_ID;
        Configuration.ORDER_ID++;
    }

    public int getId() {
        return id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public boolean mustDeliverToCustomer() {
        return deliverToCustomer && isQualifiedToDeliver();
    }

    public void setDeliverToCustomer(boolean deliverToCustomer) {
        this.deliverToCustomer = deliverToCustomer;
    }

    public void addCake(Cake cake){
        orderedCakes.add(cake);
    }

    public ArrayList<Cake> getOrderedCakes() {
        return orderedCakes;
    }

    public Customer getCustomerNotifyChannel() {
        return customer;
    }

    public void setCustomerNotifyChannel(Customer customer) {
        this.customer = customer;
    }

    public double getOrderPrice(){
        double price = 0;
        for (Cake cake :
                orderedCakes) {
            price += cake.getPrice();
        }
        return price;
    }

    public double getTotalPrice(){
        double price = getOrderPrice();
        if (deliverToCustomer
                && distance < 50
                && getOrderPrice() >= 200){
            price += 20;
        }
        return price;
    }

    public boolean isQualifiedToDeliver(){
        return distance < 50 && getOrderPrice() >= 200;
    }

    public String getShortInfo() {
        StringBuilder info = new StringBuilder();
        info.append(String.format("Zamówienie nr %s z dnia %s", id, DateStamp.getStringFromDate(orderDate)));
        info.append(String.format(" od klienta %s \n", customerName));
        info.append(String.format("Cena do zapłaty %s \n", getTotalPrice()));
        return info.toString();
    }

    public String getInfo() {
        StringBuilder info = new StringBuilder();
        info.append(String.format("Zamówienie nr %s z dnia %s", id, DateStamp.getStringFromDate(orderDate)));
        info.append(String.format(" od klienta %s \n", customerName));
        info.append(String.format(" Telefon: %s \n", customerPhone));
        info.append(String.format(" Email: %s \n", customerEmail));
        if(deliverToCustomer && isQualifiedToDeliver()){
            info.append(String.format(" Zamówienie zostanie dowiezione do klienta \n"));
        } else if(deliverToCustomer && !isQualifiedToDeliver()){
            info.append(" Zamówienie nie kwalifikuje się do dowozu. Odbiór na miejscu\n");
        } else {
            info.append(" Odbiór na miejscu\n");
        }
        for (Cake cake :
                orderedCakes) {
            info.append(cake.getInfo());
        }
        info.append(String.format("Cena do zapłaty %s \n", getTotalPrice()));

        return info.toString();
    }

    public boolean isDone(){
        boolean isRealized = true;
        for (Cake cake :
                orderedCakes) {
            isRealized = cake.getStatus();
        }
        return isRealized;
    }
}