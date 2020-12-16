/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longpt.tbldiscount;

import java.io.Serializable;
import java.sql.Date;

/**
 *
 * @author phamt
 */
public class TblDiscountDTO implements Serializable {

    private int discountId;
    private int discountPer;
    private Date date;
    private boolean status;

    public TblDiscountDTO() {
    }

    public TblDiscountDTO(int discountId, int discountPer, Date date, boolean status) {
        this.discountId = discountId;
        this.discountPer = discountPer;
        this.date = date;
        this.status = status;
    }

    public int getDiscountId() {
        return discountId;
    }

    public void setDiscountId(int discountId) {
        this.discountId = discountId;
    }

    public int getDiscountPer() {
        return discountPer;
    }

    public void setDiscountPer(int discountPer) {
        this.discountPer = discountPer;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

}
