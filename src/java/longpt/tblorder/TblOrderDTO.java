/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longpt.tblorder;

import java.io.Serializable;
import java.sql.Date;

/**
 *
 * @author phamt
 */
public class TblOrderDTO implements Serializable {
    private String orderId;
    private String username;
    private String name;
    private String address;
    private String phone;
    private int discountId;
    private double totalPrice;
    private double discountPrice;
    private Date bookDate;

    public TblOrderDTO() {
    }

    public TblOrderDTO(String orderId, String username, String name, String address, String phone, int discountId, double totalPrice, double discountPrice, Date bookDate) {
        this.orderId = orderId;
        this.username = username;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.discountId = discountId;
        this.totalPrice = totalPrice;
        this.discountPrice = discountPrice;
        this.bookDate = bookDate;
    }



    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getDiscountId() {
        return discountId;
    }

    public void setDiscountId(int discountId) {
        this.discountId = discountId;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(double discountPrice) {
        this.discountPrice = discountPrice;
    }

    public Date getBookDate() {
        return bookDate;
    }

    public void setBookDate(Date bookDate) {
        this.bookDate = bookDate;
    }
    
    
}
