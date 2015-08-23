package createsh.pojo;

import java.util.List;

/**
 * Created by tangl9 on 2015-07-21.
 */
public class Dispatch {
    private Product product;
    private double quantity;
    private String unit;
    private List<String> orderInfoList;

    @Override
    public String toString() {
        return "Dispatch{" +
                "product=" + product +
                ", quantity=" + quantity +
                ", unit='" + unit + '\'' +
                ", orderInfoList=" + orderInfoList +
                '}';
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public List<String> getOrderInfoList() {
        return orderInfoList;
    }

    public void setOrderInfoList(List<String> orderInfoList) {
        this.orderInfoList = orderInfoList;
    }
}
