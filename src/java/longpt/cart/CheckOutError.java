/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longpt.cart;

import java.io.Serializable;

/**
 *
 * @author phamt
 */
public class CheckOutError implements Serializable {

    private String checkInAfterCheckOut;
    private String checkInCheckOutBeforeCurDate;
    private String roomIdBooked;

    public CheckOutError() {
    }

    public String getCheckInAfterCheckOut() {
        return checkInAfterCheckOut;
    }

    public void setCheckInAfterCheckOut(String checkInAfterCheckOut) {
        this.checkInAfterCheckOut = checkInAfterCheckOut;
    }

    public String getCheckInCheckOutBeforeCurDate() {
        return checkInCheckOutBeforeCurDate;
    }

    public void setCheckInCheckOutBeforeCurDate(String checkInCheckOutBeforeCurDate) {
        this.checkInCheckOutBeforeCurDate = checkInCheckOutBeforeCurDate;
    }

    public String getRoomIdBooked() {
        return roomIdBooked;
    }

    public void setRoomIdBooked(String roomIdBooked) {
        this.roomIdBooked = roomIdBooked;
    }

}
