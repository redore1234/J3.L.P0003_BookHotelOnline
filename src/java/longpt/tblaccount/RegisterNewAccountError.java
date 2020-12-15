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
public class RegisterNewAccountError implements Serializable {

    private String usernameIsExisted;
    private String passwordIsNotMatch;

    public RegisterNewAccountError() {
    }

    public String getUsernameIsExisted() {
        return usernameIsExisted;
    }

    public void setUsernameIsExisted(String usernameIsExisted) {
        this.usernameIsExisted = usernameIsExisted;
    }

    public String getPasswordIsNotMatch() {
        return passwordIsNotMatch;
    }

    public void setPasswordIsNotMatch(String passwordIsNotMatch) {
        this.passwordIsNotMatch = passwordIsNotMatch;
    }

}
