/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longpt.tblaccount;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import longpt.dbulti.DbHelpers;

/**
 *
 * @author phamt
 */
public class TblAccountDAO implements Serializable {

    public TblAccountDTO checkLogin(String username, String password) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        TblAccountDTO dto = null;
        try {
            con = DbHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT fullName, roleId, statusId"
                        + " FROM tblAccount"
                        + " WHERE username=? AND password=?";
                stm = con.prepareStatement(sql);
                stm.setString(1, username);
                stm.setString(2, password);
                rs = stm.executeQuery();
                if (rs.next()) {
                    String fullName = rs.getString("fullName");
                    String roleId = rs.getString("roleId");
                    int statusId = rs.getInt("statusId");
                    dto = new TblAccountDTO(username, password, fullName, roleId, statusId);
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
        return dto;
    }

    public TblAccountDTO checkLoginGoogle(String gmail) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        TblAccountDTO dto = null;
        try {
            con = DbHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT password, fullName, roleId, statusId"
                        + " FROM tblAccount"
                        + " WHERE username=?";
                stm = con.prepareStatement(sql);
                stm.setString(1, gmail);
                rs = stm.executeQuery();
                if (rs.next()) {
                    String password = rs.getString("password");
                    String fullName = rs.getString("fullName");
                    String roleId = rs.getString("roleId");
                    int statusId = rs.getInt("statusId");
                    dto = new TblAccountDTO(gmail, password, fullName, roleId, statusId);
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
        return dto;
    }

    public boolean createNewAccount(String username, String password, String fullName, String address, String phone) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DbHelpers.makeConnection();
            if (con != null) {
                String sql = "INSERT INTO tblAccount(username, password, fullName, address, phone)"
                        + " VALUES (?,?,?,?,?)";
                stm = con.prepareStatement(sql);
                stm.setString(1, username);
                stm.setString(2, password);
                stm.setString(3, fullName);
                stm.setString(4, address);
                stm.setString(5, phone);

                int row = stm.executeUpdate();
                if (row > 0) {
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
    
    public boolean createNewAccountGoogle(String username, String password, String fullName) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DbHelpers.makeConnection();
            if (con != null) {
                String sql = "INSERT INTO tblAccount(username, password, fullName)"
                        + " VALUES (?,?,?)";
                stm = con.prepareStatement(sql);
                stm.setString(1, username);
                stm.setString(2, password);
                stm.setString(3, fullName);

                int row = stm.executeUpdate();
                if (row > 0) {
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

    public String searchNameByUsername(String username) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        String name = null;
        try {
            con = DbHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT fullName"
                        + " FROM tblAccount"
                        + " WHERE username=? AND statusId=1";
                stm = con.prepareStatement(sql);
                stm.setString(1, username);
                rs = stm.executeQuery();
                if (rs.next()) {
                    name = rs.getString("fullName");
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
        return name;
    }
}
