/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longpt.tblroomtype;

import java.io.Serializable;

/**
 *
 * @author phamt
 */
public class TblRoomTypeDTO implements Serializable{
    private String typeId;
    private String typeName;

    public TblRoomTypeDTO() {
    }

    public TblRoomTypeDTO(String typeId, String typeName) {
        this.typeId = typeId;
        this.typeName = typeName;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
    
    
}
