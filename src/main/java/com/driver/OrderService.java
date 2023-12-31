package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class OrderService {
//    @Autowired
    OrderRepository orderRepository = new OrderRepository();

    public void addOrder(Order order){
        orderRepository.orderDb.put(order.getId(),order);
    }

    public void addPartner(String partnerId){
        orderRepository.partnerDb.put(partnerId, new DeliveryPartner(partnerId));
    }

    public void orderPartnerPair(String orderId,String partnerId){
        //if order is not assigned
        if(!orderRepository.orderDb.containsKey(orderId)
                || !orderRepository.partnerDb.containsKey(partnerId)) return;

        if(!orderRepository.orderPartnerPairDb.containsKey(orderId)){
            orderRepository.orderPartnerPairDb.put(orderId,partnerId);
            DeliveryPartner deliveryPartner = orderRepository.partnerDb.get(partnerId);
            deliveryPartner.setNumberOfOrders(deliveryPartner.getNumberOfOrders()+1);
        }
    }

    public Order getOrder(String orderID){
        if(!orderRepository.orderDb.containsKey(orderID)) return null;
        return orderRepository.orderDb.get(orderID);
    }

    public DeliveryPartner getDeliverPartner(String id){
        if(!orderRepository.partnerDb.containsKey(id)) return null;
        return orderRepository.partnerDb.get(id);
    }

    public int orderAssigned(String partnerId){
        int count = 0;
        for(String orderId : orderRepository.orderPartnerPairDb.keySet()){
            if(orderRepository.orderPartnerPairDb.get(orderId).equals(partnerId)) count++;
        }
        return count;
//        return orderRepository.partnerDb.get(partnerId).getNumberOfOrders();
    }

    public List<String> getListOrderToPartner(String partnerID){
        if(!orderRepository.partnerDb.containsKey(partnerID)) return  new ArrayList<>();
        List<String> list = new ArrayList<>();
        for(String orderId : orderRepository.orderPartnerPairDb.keySet()){
            if(Objects.equals(orderRepository.orderPartnerPairDb.get(orderId), partnerID)){
                list.add(orderId);
            }
        }
        return list;
    }

    public List<String> getAllOrders(){
        return new ArrayList<>(orderRepository.orderDb.keySet());
    }

    public int unassignedOrders(){
        int count = 0;
        for(String orderId : orderRepository.orderDb.keySet()){
            if(!orderRepository.orderPartnerPairDb.containsKey(orderId)) count++;
        }
        return count;
    }

    public int sTimeToInt(String time){
        int intTime = 0;
        String[] arr = time.split(":");
        intTime = Integer.parseInt(arr[0])*60;
        intTime+=Integer.parseInt(arr[1]);
        return intTime;
    }

    public int countOrderLeftAfterTime(String time,String partnerId){
        int count = 0;
        int intTime = sTimeToInt(time);
        for(String orderId : orderRepository.orderPartnerPairDb.keySet()){
            if(orderRepository.orderPartnerPairDb.get(orderId).equals(partnerId) &&
                    orderRepository.orderDb.get(orderId).getDeliveryTime()>intTime){
                count++;
            }
        }
        return  count;
    }

    public String getLastDeliveryTimeByPartnerId(String partnerId){
        String time = null;
        int intTime = 0;
        for(String orderId : orderRepository.orderPartnerPairDb.keySet()){
            if(orderRepository.orderPartnerPairDb.get(orderId).equals(partnerId)){
                int getTime = orderRepository.orderDb.get(orderId).getDeliveryTime();
                if(getTime>intTime){
                    intTime = getTime;
                    time = orderRepository.orderDb.get(orderId).getsDeliveryTime();
                }
            }
        }
        return time;
    }

    public void deletePartner(String partnerId){
//        error occur while iterate with remove method
//        if(!orderRepository.partnerDb.containsKey(partnerId)) return;
//        for(String orderId : orderRepository.orderPartnerPairDb.keySet()){
//            if(orderRepository.orderPartnerPairDb.get(orderId).equals(partnerId)){
//                orderRepository.orderPartnerPairDb.remove(orderId);
//            }
//        }
//        orderRepository.partnerDb.remove(partnerId);

        if (!orderRepository.partnerDb.containsKey(partnerId)) return;

        List<String> keysToRemove = new ArrayList<>();

        for (String orderId : orderRepository.orderPartnerPairDb.keySet()) {
            if (orderRepository.orderPartnerPairDb.get(orderId).equals(partnerId)) {
                keysToRemove.add(orderId);
            }
        }

        for (String orderId : keysToRemove) {
            orderRepository.orderPartnerPairDb.remove(orderId);
        }

        orderRepository.partnerDb.remove(partnerId);

    }

    public void deleteOrderById(String orderId){
        if(!orderRepository.orderDb.containsKey(orderId)) return;
        orderRepository.orderDb.remove(orderId);
        String partnerId = orderRepository.orderPartnerPairDb.get(orderId);
        DeliveryPartner deliveryPartner = orderRepository.partnerDb.get(partnerId);
        deliveryPartner.setNumberOfOrders(deliveryPartner.getNumberOfOrders()-1);
        orderRepository.orderPartnerPairDb.remove(orderId);
    }
}
