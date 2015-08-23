package event.coupon.pojo;

import createsh.pojo.Order;
import createsh.util.Utils;
import org.joda.time.DateTime;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by darlingtld on 2015/8/8 0008.
 */
@Entity
@DiscriminatorValue("discount")
public class Discount extends Coupon {
    @Column(name = "discount_factor")
    private double discountFactor;

    public double getDiscountFactor() {
        return discountFactor;
    }

    public void setDiscountFactor(double discountFactor) {
        this.discountFactor = discountFactor;
    }

    @Override
    public String getcType() {
        return "打折券";
    }

    @Override
    public String generateDetailInfo() {
        return String.format("单笔订单%s折优惠", discountFactor * 10);
    }

    @Override
    public double deduct(Order order) {
        if (new DateTime(getStartTime().getTime()).isBefore(Utils.yyyyMMddHHmmssParse(order.getOrderTs()).getTime()) && new DateTime(getEndTime().getTime()).isAfter(Utils.yyyyMMddHHmmssParse(order.getOrderTs()).getTime())) {
            logger.info("Order {} reached discount {} standards", order.getId(), this.getId());
            logger.info("discount is {}, which is {}", this.getDiscountFactor(), Utils.formatDouble(order.evalBillTotalMoney() * discountFactor, 2));
            return Utils.formatDouble(order.evalBillTotalMoney() * discountFactor, 2);
        } else {
            return order.evalBillTotalMoney();
        }
    }

    @Override
    public boolean isSuitableFor(Order order) {
        if (new DateTime(getStartTime().getTime()).isBefore(Utils.yyyyMMddHHmmssParse(order.getOrderTs()).getTime()) && new DateTime(getEndTime().getTime()).isAfter(Utils.yyyyMMddHHmmssParse(order.getOrderTs()).getTime())) {
            logger.info("Order {} reached discount {} standards", order.getId(), this.getId());
            return true;
        } else {
            return false;
        }
    }
}
