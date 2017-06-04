package com.company.orders;

import com.company.Customer;
import com.company.utilities.DateStamp;

import java.util.ArrayList;
import java.util.Date;

public class SubmittedOrder {

    private Customer customer;
    private String customerName;
    private String customerPhone;
    private String customerEmail;
    private boolean deliverToCustomer;
    private Date orderDate;
    private int distance;

    public Customer getCustomerNotifyChannel() {
        return customer;
    }

    public void setCustomerNotifyChannel(Customer customer) {
        this.customer = customer;
    }

    public String getCustomerName() {
        return customerName;
    }

     private void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    private void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    private void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public boolean isDeliverToCustomer() {
        return deliverToCustomer;
    }

    public void setDeliverToCustomer(boolean deliverToCustomer) {
        this.deliverToCustomer = deliverToCustomer;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public ArrayList<OrderItem> orderItems = new ArrayList<>();

    public SubmittedOrder(String custName, String phone, String email) {
        setCustomerName(custName);
        setCustomerPhone(phone);
        setCustomerEmail(email);
        orderDate = new Date();
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
    }

    public String getInfo() {
        StringBuilder info = new StringBuilder();
        info.append(String.format("Zamówienie z dnia %s", DateStamp.getStringFromDate(orderDate)));
        info.append(String.format(" od klienta %s \n", customerName));
        info.append(String.format(" Telefon %s \n", customerPhone));
        info.append(String.format(" Email: %s \n", customerEmail));
        if(deliverToCustomer){
            info.append(String.format(" Dowóz do klienta: %s \n", deliverToCustomer));
            info.append(String.format(" Odległość od piekarni %s \n", distance));
        }
        for (OrderItem order : orderItems) {
            info.append(order.getInfo());
        }
        return info.toString();
    }
}