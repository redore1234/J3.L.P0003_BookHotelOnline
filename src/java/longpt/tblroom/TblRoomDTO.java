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
public class TblRoomDTO implements Serializable {

    private int roomId;
    private String typeId;
    private String image;
    private double price;

    public TblRoomDTO() {
    }

    public TblRoomDTO(int roomId, String typeId, double price) {
        this.roomId = roomId;
        this.typeId = typeId;
        this.price = price;
    }

    public TblRoomDTO(int roomId, String typeId, String image, double price) {
        this.roomId = roomId;
        this.typeId = typeId;
        this.image = image;
        this.price = price;
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

}
