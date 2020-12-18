/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longpt.cart;

import java.io.Serializable;
import longpt.tblroom.TblRoomDTO;

/**
 *
 * @author phamt
 */
public class RoomItem extends TblRoomDTO implements Serializable {

    private double total;
    private String checkinDate;
    private String checkoutDate;

    public RoomItem() {
    }

    public RoomItem(int roomId, String typeId, double price) {
        super(roomId, typeId, price);
    }

    public RoomItem(double total, String checkinDate, String checkoutDate) {
        this.total = total;
        this.checkinDate = checkinDate;
        this.checkoutDate = checkoutDate;
    }

    public RoomItem(double total, String checkinDate, String checkoutDate, int roomId, String typeId, String image, double price) {
        super(roomId, typeId, image, price);
        this.total = total;
        this.checkinDate = checkinDate;
        this.checkoutDate = checkoutDate;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getCheckinDate() {
        return checkinDate;
    }

    public void setCheckinDate(String checkinDate) {
        this.checkinDate = checkinDate;
    }

    public String getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(String checkoutDate) {
        this.checkoutDate = checkoutDate;
    }
}
