package createsh.pojo;

/**
 * Created by tangl9 on 2015-07-24.
 */

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "procurement")
public class Procurement {
    @Id
    private int id;
    @Column(name = "product_id")
    private int productId;
    @Column(name = "procprice")
    private double procprice;
    @Column(name = "procindex")
    private double procindex;
    @Column(name = "date")
    private Timestamp date;

    @Override
    public String toString() {
        return "Procurement{" +
                "id=" + id +
                ", productId=" + productId +
                ", procprice=" + procprice +
                ", procindex=" + procindex +
                ", date=" + date +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
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

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }
}
