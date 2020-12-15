package longpt.tblrole;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import longpt.dbulti.DbHelpers;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author phamt
 */
public class TblRoleDAO implements Serializable {

    public String getRole(String roleId) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DbHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT roleName"
                        + " FROM tblRole"
                        + " WHERE roleId=?";
                stm = con.prepareStatement(sql);
                stm.setString(1, roleId);
                rs = stm.executeQuery();
                if (rs.next()) {
                    String roleName = rs.getString("roleName");
                    return roleName;
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
        return null;
    }
}
