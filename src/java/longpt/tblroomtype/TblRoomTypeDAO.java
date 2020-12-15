/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longpt.tblroomtype;

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
public class TblRoomTypeDAO implements Serializable {

    private List<TblRoomTypeDTO> listRoomType;

    public List<TblRoomTypeDTO> getListRoomType() {
        return listRoomType;
    }

    public void loadAllRoomType() throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DbHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT typeId, typeName "
                        + " FROM tblRoomType";
                stm = con.prepareStatement(sql);
                rs = stm.executeQuery();
                while (rs.next()) {
                    String typeId = rs.getString("typeId");
                    String typeName = rs.getString("typeName");
                    TblRoomTypeDTO dto = new TblRoomTypeDTO(typeId, typeName);
                    if (listRoomType == null) {
                        listRoomType = new ArrayList<>();
                    }
                    listRoomType.add(dto);
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
