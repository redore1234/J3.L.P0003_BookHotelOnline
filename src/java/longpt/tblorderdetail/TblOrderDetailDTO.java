/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longpt.tblorderdetail;

import java.io.Serializable;
import java.sql.Date;

/**
 *
 * @author phamt
 */
public class TblOrderDetailDTO implements Serializable {

    private int detailId;
    private String orderId;
    private int roomId;
    private double totalPrice;
    private Date checkinDate;
    private Date checkoutDate;

    public TblOrderDetailDTO() {
    }

    public TblOrderDetailDTO(int detailId, String orderId, int roomId, double totalPrice, Date checkinDate, Date checkoutDate) {
        this.detailId = detailId;
        this.orderId = orderId;
        this.roomId = roomId;
        this.totalPrice = totalPrice;
        this.checkinDate = checkinDate;
        this.checkoutDate = checkoutDate;
    }

    public int getDetailId() {
        return detailId;
    }

    public void setDetailId(int detailId) {
        this.detailId = detailId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getCheckinDate() {
        return checkinDate;
    }

    public void setCheckinDate(Date checkinDate) {
        this.checkinDate = checkinDate;
    }

    public Date getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(Date checkoutDate) {
        this.checkoutDate = checkoutDate;
    }
}
