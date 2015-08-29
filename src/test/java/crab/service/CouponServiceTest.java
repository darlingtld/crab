package crab.service;

import event.coupon.pojo.Coupon;
import event.coupon.pojo.Discount;
import event.coupon.pojo.Voucher;
import event.coupon.service.CouponService;
import crab.pojo.Order;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

/**
 * Created by darlingtld on 2015/8/8 0008.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("file:src/main/webapp/WEB-INF/mvc-dispatcher-servlet.xml")
public class CouponServiceTest {

    @Autowired
    private CouponService couponService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    String openid = "o5Irvt3tty06NYT8W-nJa0gPERvM";

    @Test
    public void createCouponTest() {
//        for (User user : userService.getAll()) {
//            if (!user.getRole().equals(Role.DELIVERYMAN.toString())) {
//                continue;
//            }
//            openid = user.getOpenid();
            // 12 - 1
            Voucher voucher = new Voucher();
            voucher.setOpenid(openid);
            DateTime startTime = new DateTime(2015, 7, 30, 0, 0, 0);
            DateTime endTime = new DateTime(2015, 8, 31, 0, 0, 0);
            voucher.setStartTime(startTime.toDate());
            voucher.setEndTime(endTime.toDate());
            voucher.setReachedMoney(100);
            voucher.setDeductedMoney(8);
            voucher.setUsed(false);
            couponService.createCoupon(voucher);

            // 0.95
            Discount discount = new Discount();
            discount.setOpenid(openid);
            discount.setStartTime(startTime.toDate());
            discount.setEndTime(endTime.toDate());
            discount.setDiscountFactor(0.95);
            discount.setUsed(false);
            couponService.createCoupon(discount);
//        }

    }

    @Test
    public void runCouponRules() {
        Order order = orderService.getList(openid).get(1);
        System.out.println(order.evalBillTotalMoney());
        // get coupons
        List<Coupon> couponList = couponService.getCouponList(openid);

        for (Coupon coupon : couponList) {
            double preferredMoney = coupon.deduct(order);
            System.out.print(preferredMoney);
        }

    }
}
