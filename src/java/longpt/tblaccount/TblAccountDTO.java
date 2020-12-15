/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longpt.tblaccount;

import java.io.Serializable;

/**
 *
 * @author phamt
 */
public class TblAccountDTO implements Serializable {

    private String username;
    private String password;
    private String fullName;
    private String roleId;
    private int statusId;

    public TblAccountDTO() {
    }

    public TblAccountDTO(String username, String password, String fullName, String roleId, int statusId) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.roleId = roleId;
        this.statusId = statusId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }
}
