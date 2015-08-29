package crab.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import event.coupon.dao.CouponDao;
import event.coupon.pojo.Coupon;
import event.coupon.pojo.Discount;
import event.coupon.pojo.Voucher;
import crab.dao.OrderDao;
import crab.dao.ProductDao;
import crab.dao.UserDao;
import crab.pojo.*;
import crab.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by darlingtld on 2015/5/16.
 */
@Service
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private CouponDao couponDao;

    @Transactional
    public void save(Order order) {
        User user = userDao.getUserByWechatId(order.getWechatId());
        if (user != null) {
            user.setConsignee(order.getConsignee());
            user.setConsigneeContact(order.getConsigneeContact());
            user.setBuyerInfo(order.getBuyerInfo());
            user.setBuyerAddress(order.getBuyerAddress());
            userDao.update(user);
        } else {
            String unknownUser = "songda user";
            order.setUserId(unknownUser);
            user = new User();
            user.setOpenid(order.getWechatId());
            user.setNickname(unknownUser);
            user.setConsignee(order.getConsignee());
            user.setConsigneeContact(order.getConsigneeContact());
            user.setBuyerInfo(order.getBuyerInfo());
            user.setBuyerAddress(order.getBuyerAddress());
            userDao.save(user);
        }
        String code = generateConfirmCode();
        while (isConfirmCodeExisted(code)) {
            code = generateConfirmCode();
        }
        order.setConfirmCode(code);

        JSONObject jsonObject = JSON.parseObject(order.getBill());
        double totalPrice = jsonObject.getDouble("totalPrice");
        jsonObject.put("totalPrice", Utils.formatDouble(totalPrice, 2));
        order.setBill(jsonObject.toJSONString());
        markUsedCoupon(jsonObject.getString("usedCoupon"));
        saveCrabCardIfAny(jsonObject.getJSONArray("items"), user.getOpenid());
        orderDao.save(order);
    }

    private void saveCrabCardIfAny(JSONArray items, String openid) {
        for (int i = 0; i < items.size(); i++) {
            int productId = items.getJSONObject(i).getInteger("productId");
            if (isItemTypeOfCard(productId)) {
                CardCode cardCode = productDao.getUnusedCardCode(productId);
                if (cardCode == null) {
                    throw new IllegalStateException("存货不足");
                }
                int id = cardCode.getId();
                productDao.markUnusedCardCode4User(id, openid);
            }
        }
    }

    private boolean isItemTypeOfCard(Integer productId) {
        Product product = productDao.getById(productId);
        if (product.getType().equals(Type.CARD)) {
            return true;
        } else {
            return false;
        }
    }

    private void markUsedCoupon(String couponJson) {
        Coupon coupon = JSON.parseObject(couponJson, Voucher.class);
        if (coupon == null) {
            coupon = JSON.parseObject(couponJson, Discount.class);
        }

        if (coupon == null) {
            return;
        } else {
            coupon.setUsed(true);
            couponDao.updateCoupon(coupon);
        }
    }

    @Transactional
    private boolean isConfirmCodeExisted(String code) {
        return orderDao.isConfirmCodeExisted(code);
    }

    private String generateConfirmCode() {
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());
        String value = String.valueOf(random.nextInt(1000000000));
        StringBuffer code = new StringBuffer(value);
        for (int i = 0; i < 9 - value.length(); i++) {
            code.insert(0, "0");
        }
        return code.toString();
    }

    @Transactional
    public List<Order> getList(String wechatid) {
        return orderDao.getList(wechatid);
    }

    @Transactional
    public Order getById(int orderId) {
        return orderDao.getById(orderId);
    }

    @Transactional
    public List<Order> getAll() {
        return orderDao.getAll();
    }

    @Transactional
    public boolean update(Order order) {
//        Order orderInDb = getById(order.getId());
//        orderInDb.setDeliveryTs(order.getDeliveryTs());
//        orderInDb.setStatus(order.getStatus());
        return orderDao.update(order);
    }

    @Transactional
    public List<Order> getLatestList(String wechatId, int count) {
        return orderDao.getLatestList(wechatId, count);
    }

    @Transactional
    public Order getOrderByConfirmCode(String confirmCode) {
        logger.info("Get order using confirm code {}", confirmCode);
        return orderDao.getByConfirmCode(confirmCode);
    }

    @Transactional
    public List<Product> getListByTimeFrame(String wechatid, Calendar then, Calendar now, String type) {
        logger.info("Get order of {} from {} to {} of type {}", wechatid, then.getTime().toString(), now.getTime().toString(), type);
        List<Order> orderList = orderDao.getListByTimeFrame(wechatid, then, now);
        List<Product> productList = new ArrayList<>();
        List<Product> allProductList = productDao.getByType(type);
        HashMap<Integer, Product> allProductMap = new HashMap<>();
        for (Product product : allProductList) {
            allProductMap.put(product.getId(), product);
        }
        for (Order order : orderList) {
            JSONObject jsonObject = JSON.parseObject(order.getBill());
            JSONArray jsonArray = jsonObject.getJSONArray("items");
            for (int i = 0; i < jsonArray.size(); i++) {
                Integer productId = jsonArray.getJSONObject(i).getInteger("productId");
                Product product = allProductMap.get(productId);
                if (!productList.contains(product)) {
                    if (product != null)
                        productList.add(product);
                }
            }
        }
        return productList;
    }

    @Transactional
    public List<Dispatch> getDispatchList() {
        List<Order> orderList = orderDao.getOrderListByStatus(OrderStatus.NOT_DELIVERED);
        List<Product> allProductList = productDao.getAll();
        HashMap<Integer, Product> allProductMap = new HashMap<>();
        for (Product product : allProductList) {
            allProductMap.put(product.getId(), product);
        }
        Map<Integer, Dispatch> dispatchMap = new HashMap<>();
        for (Order order : orderList) {
            JSONObject jsonObject = JSON.parseObject(order.getBill());
            JSONArray jsonArray = jsonObject.getJSONArray("items");
            for (int i = 0; i < jsonArray.size(); i++) {
                Integer productId = jsonArray.getJSONObject(i).getInteger("productId");
                Integer amount = jsonArray.getJSONObject(i).getInteger("amount");
                String unit = jsonArray.getJSONObject(i).getString("productUnit");
                Product product = allProductMap.get(productId);
                if (product == null) {
                    continue;
                }
                if (dispatchMap.containsKey(productId)) {
                    dispatchMap.get(productId).setQuantity(dispatchMap.get(productId).getQuantity() + amount);
                    dispatchMap.get(productId).getOrderInfoList().add(getKeyInfo(order, amount, unit));
                } else {
                    Dispatch dispatch = new Dispatch();
                    dispatch.setProduct(product);
                    dispatch.setQuantity(amount);
                    List<String> orders = new ArrayList<>();
                    orders.add(getKeyInfo(order, amount, unit));
                    dispatch.setOrderInfoList(orders);
                    dispatch.setUnit(unit);
                    dispatchMap.put(productId, dispatch);
                }

            }
        }
        return new ArrayList<>(dispatchMap.values());
    }

    private String getKeyInfo(Order order, Integer amount, String unit) {
        return String.format("[数量]%s%s [送货时间]%s [收件人]%s %s [收货地址]%s [电话]%s [下单时间]%s", amount, unit, order.getDeliveryTs(), order.getConsignee(), order.getBuyerInfo(), order.getBuyerAddress(), order.getConsigneeContact(), order.getOrderTs());
    }

    public List<String> getStatusList() {
        return Arrays.asList(OrderStatus.NOT_DELIVERED, OrderStatus.IN_DELIVERY, OrderStatus.DELIVERED_NOT_PAID, OrderStatus.DELIVERED_PAID);
    }

    @Transactional
    public void deleteOrder(int orderId) {
        Order order = getById(orderId);
        if (order.getStatus().equals(OrderStatus.NOT_DELIVERED.toString())) {
            orderDao.deleteOrder(orderId);
        } else {
            throw new IllegalStateException(String.format("无法删除[订单状态为%s]", order.getStatus()));
        }
    }

    @Transactional
    public List<Order> getByStatus(String notDelivered) {
        return orderDao.getOrderListByStatus(notDelivered);
    }
}
