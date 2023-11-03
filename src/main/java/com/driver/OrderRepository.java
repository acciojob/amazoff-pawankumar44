package com.driver;

import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public class OrderRepository {
    HashMap<String,Order> orderDb = new HashMap<>();

    HashMap<String,DeliveryPartner> partnerDb = new HashMap<>();

    //order,partner
    HashMap<String,String> orderPartnerPairDb = new HashMap<>();

}
