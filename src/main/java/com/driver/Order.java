package com.driver;

public class Order {

    private String id;
    private int deliveryTime;
    private String sDeliveryTime;

    public Order(String id, String deliveryTime) {
        // The deliveryTime has to converted from string to int and then stored in the attribute
        //deliveryTime  = HH*60 + MM
        this.id = id;
        this.sDeliveryTime = deliveryTime;
        String[] arr = deliveryTime.split(":");
        this.deliveryTime = Integer.parseInt(arr[0])*60;
        this.deliveryTime+=Integer.parseInt(arr[1]);
    }

    public String getId() {
        return id;
    }

    public int getDeliveryTime() {return deliveryTime;}

    public String getsDeliveryTime() {
        return sDeliveryTime;
    }
}
