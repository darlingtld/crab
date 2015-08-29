package crab.pojo;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by darlingtld on 2015/5/16.
 */
@Entity
@Table(name = "product")
public class Product {
    @Id
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private Type type;
    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private Category category;
    @Column(name = "price")
    private Double price;
    @Column(name = "unit")
    private String unit;
    @Column(name = "picurl")
    private String picurl;
    @Column(name = "onsale")
    private int onsale;
    @Column(name = "data_change_last_time")
    private Timestamp dataChangeLastTime;
    @Transient
    private double procprice;
    @Transient
    private double procindex;
    @Column(name = "order_index")
    private int orderIndex;
    @Column(name = "detail")
    private String detail;

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(int orderIndex) {
        this.orderIndex = orderIndex;
    }

    public double getProcprice() {
        return procprice;
    }

    public void setProcprice(double procprice) {
        this.procprice = procprice;
    }

    public double getProcindex() {
        return procindex;
    }

    public void setProcindex(double procindex) {
        this.procindex = procindex;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", type=" + type +
                ", category=" + category +
                ", price=" + price +
                ", unit='" + unit + '\'' +
                ", picurl='" + picurl + '\'' +
                ", onsale=" + onsale +
                ", dataChangeLastTime=" + dataChangeLastTime +
                '}';
    }

    public int getOnsale() {
        return onsale;
    }

    public void setOnsale(int onsale) {
        this.onsale = onsale;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getPicurl() {
        return picurl;
    }

    public void setPicurl(String picurl) {
        this.picurl = picurl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Timestamp getDataChangeLastTime() {
        return dataChangeLastTime;
    }

    public void setDataChangeLastTime(Timestamp dataChangeLastTime) {
        this.dataChangeLastTime = dataChangeLastTime;
    }

    public String generatePicurlHash() {
        return "pic" + name.hashCode() + +description.toString().hashCode() + type.toString().hashCode() + System.currentTimeMillis();
    }

    @Override
    public boolean equals(Object obj) {
        return obj.toString().equals(this.toString());
    }
}
