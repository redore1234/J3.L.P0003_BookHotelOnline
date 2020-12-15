/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longpt.tblroom;

import java.io.Serializable;

/**
 *
 * @author phamt
 */
public class tblRoomDTO implements Serializable {

    private int roomId;
    private String typeId;
    private String image;
    private double price;
    private int statusId;

    public tblRoomDTO() {
    }

    public tblRoomDTO(int roomId, String typeId, String image, double price, int statusId) {
        this.roomId = roomId;
        this.typeId = typeId;
        this.image = image;
        this.price = price;
        this.statusId = statusId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

}
