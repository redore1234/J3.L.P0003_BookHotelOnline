/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longpt.cart;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author phamt
 */
public class Cart implements Serializable {

    private Map<Integer, RoomItem> compartment;
    protected double totalPrice = 0;
    protected int discountID;
    protected int discountPer;
    protected double priceDiscount = 0;
    protected double priceAfterDiscount;

    public Cart() {
    }

    public Map<Integer, RoomItem> getCompartment() {
        return compartment;
    }

    public void bookRoomToCart(int roomId, RoomItem dto) {
        if (compartment == null) {
            compartment = new HashMap<>();
        }
        if (!compartment.containsKey(roomId)) { //nếu chọn roomId khác thì add vào cart
            compartment.put(roomId, dto);
        }
    }

    public void removeRoomFromCart(int roomId) {

    }

    public double getTotalPrice() {
        if (this.compartment == null || this.compartment.isEmpty()) {
            return 0;
        } else {
            totalPrice = 0;
            for (int roomId : this.compartment.keySet()) {
                totalPrice += this.compartment.get(roomId).getPrice();
            }
        }
        return totalPrice;
    }
    
        //---DISCOUNT ID---//
    public int getDiscountID() {
        return discountID;
    }

    public void setDiscountID(int discountID) {
        if (this.discountID == 0) {
            this.discountID = discountID;
        }
    }

    //---DISCOUNT Percent---//
    public int getDiscountPer() {
        return discountPer;
    }

    public void setDiscountPer(int discountPer) {
        if (this.discountPer == 0) {
            this.discountPer = discountPer;
        }
    }

    //---DISCOUNT Price---//
    public double getPriceDiscount() {
        if(discountPer != 0){
            double priceTotal = getTotalPrice();
            priceDiscount = priceTotal * discountPer / 100;
        }
        
        return priceDiscount;
    }

    //---PRICE AFTER DISCOUNT---//
    public double getPriceAfterDiscount() {
        return priceAfterDiscount = getTotalPrice() - getPriceDiscount();
    }
}
