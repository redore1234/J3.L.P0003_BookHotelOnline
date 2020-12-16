/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longpt.tbldiscount;

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

/**
 *
 * @author phamt
 */
public class TblDiscountDAO implements Serializable {

    private List<TblDiscountDTO> discountList;

    public List<TblDiscountDTO> getDiscountList() {
        return discountList;
    }

    public void loadAllDiscounts() throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        TblDiscountDTO dto = null;
        try {
            con = DbHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT discountId, discountPercent, date, status"
                        + " FROM tblDiscount";
                stm = con.prepareStatement(sql);
                rs = stm.executeQuery();
                while (rs.next()) {
                    int discountId = rs.getInt("discountId");
                    int percent = rs.getInt("discountPercent");
                    Date date = rs.getDate("date");
                    boolean status = rs.getBoolean("status");

                    dto = new TblDiscountDTO(discountId, percent, date, status);

                    if (discountList == null) {
                        discountList = new ArrayList<>();
                    }
                    discountList.add(dto);
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

    public int getDisPercentById(int discountId) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DbHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT discountPercent"
                        + " FROM tblDiscount"
                        + " WHERE discountId=? AND status=1";
                stm = con.prepareStatement(sql);
                stm.setInt(1, discountId);
                rs = stm.executeQuery();
                if (rs.next()) {
                    int disPercent = rs.getInt("discountPercent");
                    return disPercent;
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
        return 0;
    }

    public boolean deleteDiscount(int discountId) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DbHelpers.makeConnection();
            if (con != null) {
                String sql = "UPDATE tblDiscount"
                        + " SET status=0"
                        + " WHERE discountId=?";
                stm = con.prepareStatement(sql);
                stm.setInt(1, discountId);
                if (stm.executeUpdate() > 0) {
                    return true;
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
        return false;
    }
}
