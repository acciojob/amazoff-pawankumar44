package com.driver;

import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public class OrderRepository {
    //orderId->Order Object
    HashMap<String,Order> orderDb = new HashMap<>();

    //partnerId -> PartnerObject
    HashMap<String,DeliveryPartner> partnerDb = new HashMap<>();

    //orderId -> partnerId
    HashMap<String,String> orderPartnerPairDb = new HashMap<>();

}
