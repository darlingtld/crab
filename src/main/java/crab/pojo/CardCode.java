package crab.pojo;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by tangl9 on 2015-08-26.
 */
@Entity
@Table(name = "card_code")
public class CardCode {
    @Id
    private int id;
    @Column(name = "code")
    private String code;
    @Column(name = "password")
    private String password;
    @Column(name = "start_time")
    private Date startTime;
    @Column(name = "end_time")
    private Date endTime;
    @Column(name = "used")
    private boolean used;
    @Column(name = "owner_id")
    private String openid;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    @Column(name = "consignee")
    private String consignee;
    @Column(name = "consignee_contact")
    private String consigneeContact;
    @Column(name = "consignee_datetime")
    private Date consigneeDatetime;
    @Column(name = "buyer_address")
    private String buyerAddress;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getConsigneeContact() {
        return consigneeContact;
    }

    public void setConsigneeContact(String consigneeContact) {
        this.consigneeContact = consigneeContact;
    }

    public Date getConsigneeDatetime() {
        return consigneeDatetime;
    }

    public void setConsigneeDatetime(Date consigneeDatetime) {
        this.consigneeDatetime = consigneeDatetime;
    }

    public String getBuyerAddress() {
        return buyerAddress;
    }

    public void setBuyerAddress(String buyerAddress) {
        this.buyerAddress = buyerAddress;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }
}
