/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longpt.tblroom;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import longpt.dbulti.DbHelpers;

/**
 *
 * @author phamt
 */
public class TblRoomDAO implements Serializable{
    
    private List<TblRoomDTO> listRoom;

    public List<TblRoomDTO> getListRoom() {
        return listRoom;
    }
    
    public void loadAllRooms() throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DbHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT roomId, typeId, image, price"
                        + " FROM tblRoom";
                stm = con.prepareStatement(sql);
                rs = stm.executeQuery();
                while (rs.next()) {
                    int roomId =rs.getInt("roomId");
                    String typeId = rs.getString("typeId");
                    String image = rs.getString("image");
                    double price = rs.getDouble("price");
                    TblRoomDTO dto = new TblRoomDTO(roomId, typeId, image, price);
                    if (listRoom == null) {
                        listRoom = new ArrayList<>();
                    }
                    listRoom.add(dto);
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
    
    public void searchRoomType(String roomType) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DbHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT roomId, image, price"
                        + " FROM tblRoom"
                        + " WHERE typeId=?"; 
                stm = con.prepareStatement(sql);
                stm.setString(1, roomType);
                
                rs = stm.executeQuery();
                while (rs.next()) {
                    int roomId =rs.getInt("roomId");
                    String image = rs.getString("image");
                    double price = rs.getDouble("price");
                    TblRoomDTO dto = new TblRoomDTO(roomId, roomType, image, price);
                    if (listRoom == null) {
                        listRoom = new ArrayList<>();
                    }
                    listRoom.add(dto);
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
}
