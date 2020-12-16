/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longpt.tblorder;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import longpt.dbulti.DbHelpers;
import longpt.tblroom.TblRoomDTO;

/**
 *
 * @author phamt
 */
public class TblOrderDAO implements Serializable {

    private List<TblOrderDTO> listOrder;

    public List<TblOrderDTO> getListOrder() {
        return listOrder;
    }

    public void loadAllOrder(String username) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DbHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT name, address, phoneNumber, discountId, totalPrice, discountPrice, bookingDate"
                        + " FROM tblOrder"
                        + " WHERE username=?";
                stm = con.prepareStatement(sql);
                rs = stm.executeQuery();
                while (rs.next()) {
                    String name = rs.getString("name");
                    String address = rs.getString("address");
                    String phoneNumber = rs.getString("phoneNumber");
                    int discountId = rs.getInt("discountId");
                    double totalPrice = rs.getDouble("totalPrice");
                    double discountPrice = rs.getDouble("discountPrice");
                    Date bookingDate = rs.getDate("bookingDate");
                    TblOrderDTO dto = new TblOrderDTO(null, username, name, address, name, discountId, totalPrice, discountPrice, bookingDate);
                    if (listOrder == null) {
                        listOrder = new ArrayList<>();
                    }
                    listOrder.add(dto);
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
    
    public String createOrder(String username, String name, String address, String phone, double totalPrice, double discountPrice) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        String orderId = null;
        try {
            con = DbHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT NEWID()"
                        + " AS orderId";
                stm = con.prepareStatement(sql);
                rs = stm.executeQuery();
                if (rs.next()) {
                    orderId = rs.getString("orderId");
                }

                sql = "INSERT INTO tblOrder(orderId, username, name, address, phoneNumber, totalPrice, discountPrice)"
                        + " VALUES(?,?,?,?,?,?,?)";
                stm = con.prepareStatement(sql);
                stm.setString(1, orderId);
                stm.setString(2, username);
                stm.setString(3, name);
                stm.setString(4, address);
                stm.setString(5, phone);
                stm.setDouble(6, totalPrice);
                stm.setDouble(7, discountPrice);

                stm.executeUpdate();
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
        return orderId;
    }
}
