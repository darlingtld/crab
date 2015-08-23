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
@DiscriminatorValue("voucher")
public class Voucher extends Coupon {
    @Column(name = "reached_money")
    private double reachedMoney;
    @Column(name = "deducted_money")
    private double deductedMoney;

    public double getReachedMoney() {
        return reachedMoney;
    }

    public void setReachedMoney(double reachedMoney) {
        this.reachedMoney = reachedMoney;
    }

    public double getDeductedMoney() {
        return deductedMoney;
    }

    public void setDeductedMoney(double deductedMoney) {
        this.deductedMoney = deductedMoney;
    }

    @Override
    public String getcType() {
        return "代金券";
    }

    @Override
    public String generateDetailInfo() {
        return String.format("单笔订单满%s减%s",reachedMoney, deductedMoney);
    }

    @Override
    public double deduct(Order order) {
        if (new DateTime(getStartTime()).isBefore(Utils.yyyyMMddHHmmssParse(order.getOrderTs()).getTime()) && new DateTime(getEndTime()).isAfter(Utils.yyyyMMddHHmmssParse(order.getOrderTs()).getTime()) && reachedMoney <= order.evalBillTotalMoney()) {
            logger.info("Order {} reached voucher {} standards", order.getId(), this.getId());
            logger.info("{} >= {}, shall deduct {}, which is {}", order.evalBillTotalMoney(), reachedMoney, deductedMoney, order.evalBillTotalMoney() - deductedMoney);
            return order.evalBillTotalMoney() - deductedMoney;
        } else {
            return order.evalBillTotalMoney();
        }
    }

    @Override
    public boolean isSuitableFor(Order order) {
        if (new DateTime(getStartTime()).isBefore(Utils.yyyyMMddHHmmssParse(order.getOrderTs()).getTime()) && new DateTime(getEndTime()).isAfter(Utils.yyyyMMddHHmmssParse(order.getOrderTs()).getTime()) && reachedMoney <= order.evalBillTotalMoney()) {
            logger.info("Order {} reached voucher {} standards", order.getId(), this.getId());
            return true;
        } else {
            return false;
        }
    }
}
