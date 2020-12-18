/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longpt.tblorderdetail;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.naming.NamingException;
import longpt.cart.Cart;
import longpt.cart.RoomItem;
import longpt.dbulti.DbHelpers;
import longpt.tblroom.TblRoomDAO;

/**
 *
 * @author phamt
 */
public class TblOrderDetailDAO implements Serializable {

    private Map<TblOrderDetailDTO, String> listOrderDetail;

    public Map<TblOrderDetailDTO, String> getListOrderDetail() {
        return listOrderDetail;
    }

    public boolean addOrderDetail(Cart cart, String orderId, String checkinDate, String checkoutDate) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        boolean flag = true;
        try {
            con = DbHelpers.makeConnection();
            if (con != null) {
                con.setAutoCommit(false); //dữ liệu sẽ chỉ update vào database khi gọi lệnh commit()
                String sql = "INSERT INTO tblOrderDetail(orderId, roomId, totalPrice, checkinDate, checkoutDate)"
                        + " VALUES(?,?,?,?,?)";
                stm = con.prepareStatement(sql);
                for (int roomId : cart.getCompartment().keySet()) {
                    stm.setString(1, orderId);
                    stm.setInt(2, roomId);
                    RoomItem item = cart.getCompartment().get(roomId);
                    stm.setDouble(3, item.getPrice());
                    stm.setString(4, checkinDate);
                    stm.setString(5, checkoutDate);
                    stm.addBatch();
                }

                int[] result = stm.executeBatch();

                for (int i = 0; i < result.length; i++) {
                    if (result[i] == 0) { //if found err in result array (value == 0)
                        flag = false;
                    }
                }

                if (flag == true) {
                    con.commit();
                } else {
                    con.rollback();
                }
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return flag;
    }

    public void loadOrderDetail(String orderId) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DbHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT detailId, roomId, totalPrice, checkinDate, checkoutDate"
                        + " FROM tblOrderDetail"
                        + " WHERE orderId=?";
                stm = con.prepareStatement(sql);
                stm.setString(1, orderId);
                rs = stm.executeQuery();
                while (rs.next()) {
                    int detailId = rs.getInt("detailId");
                    int roomId = rs.getInt("roomId");
                    //Get RoomName by Id
                    TblRoomDAO roomDAO = new TblRoomDAO();
                    String roomType = roomDAO.getRoomTypeById(roomId);
                    
                    double total = rs.getDouble("totalPrice");
                    Date checkIn = rs.getDate("checkinDate");
                    Date checkOut = rs.getDate("checkoutDate");

                    TblOrderDetailDTO dto = new TblOrderDetailDTO(detailId, orderId, roomId, total, checkIn, checkOut);
                    if (listOrderDetail == null) {
                        listOrderDetail = new HashMap<>();
                    }
                    listOrderDetail.put(dto, roomType);
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
