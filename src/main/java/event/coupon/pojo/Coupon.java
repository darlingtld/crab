package event.coupon.pojo;

import crab.pojo.Order;
import crab.pojo.User;
import crab.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by darlingtld on 2015/8/8 0008.
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "coupon")
@DiscriminatorColumn(name = "coupon_type")
@DiscriminatorValue("coupon")
public abstract class Coupon {
    protected static Logger logger = LoggerFactory.getLogger(Coupon.class);
    @Id
    private int id;
    @Column(name = "openid")
    private String openid;
    @Column(name = "start_time")
    private Date startTime;
    @Column(name = "end_time")
    private Date endTime;
    @Column(name = "used")
    private boolean used;
    @Transient
    private String detailInfo;
    @Transient
    private String timeLimit;
    @Transient
    private double modifiedTotalPrice;
    @Transient
    private User user;
    @Transient
    private String cType;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public abstract String getcType();

    public void setcType(String cType) {
        this.cType = cType;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public double getModifiedTotalPrice() {
        return modifiedTotalPrice;
    }

    public void setModifiedTotalPrice(double modifiedTotalPrice) {
        this.modifiedTotalPrice = modifiedTotalPrice;
    }

    public String getDetailInfo() {
        return detailInfo;
    }

    public void setDetailInfo(String detailInfo) {
        this.detailInfo = detailInfo;
    }

    public String getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(String timeLimit) {
        this.timeLimit = timeLimit;
    }

    public abstract String generateDetailInfo();

    public String generateTimeLimit() {
        return Utils.chineseDateFormat(getStartTime()) + "—" + Utils.chineseDateFormat(getEndTime());
    }

    @Override
    public String toString() {
        return "Coupon{" +
                "id=" + id +
                ", openid='" + openid + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
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

    public abstract double deduct(Order order);

    public abstract boolean isSuitableFor(Order order);
}
