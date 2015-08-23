package event.coupon.service;

import com.google.common.base.Function;
import com.google.common.collect.Maps;
import createsh.dao.UserDao;
import createsh.pojo.Order;
import createsh.pojo.User;
import createsh.util.Utils;
import event.coupon.dao.CouponDao;
import event.coupon.pojo.Coupon;
import event.coupon.pojo.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by darlingtld on 2015/8/8 0008.
 */
@Service
public class CouponService {

    private static Logger logger = LoggerFactory.getLogger(CouponService.class);

    @Autowired
    private CouponDao couponDao;

    @Autowired
    private UserDao userDao;

    @Transactional
    public void createCoupon(Coupon coupon) {
        logger.info("Create coupon {}", coupon.toString());
        List<User> userList = userDao.getAll();
        for (User user : userList) {
            coupon.setOpenid(user.getOpenid());
            couponDao.createCoupon(coupon);
        }
    }

    @Transactional
    public Coupon getCoupon(int id) {
        logger.info("Get coupon {}", id);
        return couponDao.getCoupon(id);
    }

    @Transactional
    public List<Coupon> getCouponList(String openid) {
        logger.info("Get coupon list of {}", openid);
        return couponDao.getCouponList(openid);
    }

    @Transactional
    public List<Voucher> getCouponList(String wechatid, Class<Voucher> voucherClass) {
        logger.info("Get voucher list of {}", wechatid);
        return couponDao.getCouponList(wechatid, voucherClass);
    }

    @Transactional
    public List<Coupon> getCalculatedCouponList(String wechatid, String billJson) {
        logger.info("Get calculated coupon list of {}", wechatid);
        Order order = transformOrderFromBill(billJson);
        List<Coupon> couponList = couponDao.getCouponList(wechatid);
        List<Coupon> suitableCouponList = new ArrayList<>();
        for (Coupon coupon : couponList) {
            if (coupon.isSuitableFor(order)) {
                coupon.setModifiedTotalPrice(coupon.deduct(order));
                suitableCouponList.add(coupon);
            }
        }
        return suitableCouponList;
    }

    private Order transformOrderFromBill(String billJson) {
        Order order = new Order();
        order.setBill(billJson);
        order.setOrderTs(Utils.yyyyMMddHHmmssFormat(new Date()));
        return order;
    }

    @Transactional
    public List<Coupon> getAllCouponList() {
        List<User> userList = userDao.getAll();
        Map<String, User> userMap = Maps.uniqueIndex(userList, new Function<User, String>() {
            public String apply(User from) {
                return from.getOpenid();
            }
        });
        List<Coupon> couponList = couponDao.getAll();
        for (Coupon coupon : couponList) {
            coupon.setDetailInfo(coupon.generateDetailInfo());
            coupon.setTimeLimit(coupon.generateTimeLimit());
            coupon.setUser(userMap.get(coupon.getOpenid()));
        }
        return couponList;
    }
}
