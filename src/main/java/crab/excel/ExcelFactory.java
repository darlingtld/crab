package crab.excel;


import crab.pojo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by tangl9 on 2015-07-23.
 */
public class ExcelFactory {

    private static final Logger logger = LoggerFactory.getLogger(ExcelFactory.class);

    public static void exportProducts(String fileName, List<Product> productList, Map<Type, List<Category>> typeMap, List<Procurement> procurementList) {
        logger.info("Export product to excel");
        ExcelGenerator generator = new ExcelGenerator(fileName);
        List<String[]> headerList = new ArrayList<>();
        List<String> sheetNames = new ArrayList<>();
        for (Type type : typeMap.keySet()) {
            sheetNames.add(type.getType());
            headerList.add(new String[]{"名字", "描述", "大类", "小类", "采购价", "系数", "销售价", "单位"});
        }
        List<List<String[]>> contents = new ArrayList<>();
        for (Type type : typeMap.keySet()) {
            List<Category> categoryList = typeMap.get(type);
            List<String[]> content = new ArrayList<>();
            for (Category category : categoryList) {
                List<Product> subProductList = getProductListByCategory(productList, category);
                for (Product product : subProductList) {
                    Procurement procurement = getProcurement(procurementList, product.getId());
                    double procprice = product.getPrice();
                    double procindex = 1.0;
                    if (procurement != null) {
                        procprice = procurement.getProcprice();
                        procindex = procurement.getProcindex();
                    }
                    content.add(new String[]{product.getName(), product.getDescription(), product.getType().getType(), product.getCategory().getCategory(), String.valueOf(procprice), String.valueOf(procindex), product.getPrice().toString(), product.getUnit()});
                }
            }
            contents.add(content);
        }
        try {
            generator.generate(sheetNames, headerList, contents);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }


    }

    private static Procurement getProcurement(List<Procurement> procurementList, int productId) {
        for (Procurement procurement : procurementList) {
            if (procurement.getProductId() == productId) {
                return procurement;
            }
        }
        return null;
    }

    private static List<Product> getProductListByCategory(List<Product> productList, Category category) {
        List<Product> products = new ArrayList<>();
        for (Product product : productList) {
            if (product.getCategory().toString().equals(category.toString())) {
                products.add(product);
            }
        }
        return products;
    }

    public static void exportOrders(String fileName, List<Order> orderList, List<String> orderStatusList) {
        logger.info("Export Orders to excel");
        ExcelGenerator generator = new ExcelGenerator(fileName);
        List<String[]> headerList = new ArrayList<>();
        List<String> sheetNames = new ArrayList<>();
        for (String status : orderStatusList) {
            sheetNames.add(status);
            headerList.add(new String[]{"用户名", "订单时间", "配送时间", "门店信息", "收货人", "联系方式", "状态"});
        }
        List<List<String[]>> contents = new ArrayList<>();
        for (String status : orderStatusList) {
            List<String[]> content = new ArrayList<>();
            List<Order> subOrderList = getOrderListByStatus(orderList, status);
            for (Order order : subOrderList) {
                content.add(new String[]{order.getUserId(), order.getOrderTs(), order.getDeliveryTs(), order.getBuyerInfo(), order.getConsignee(), order.getConsigneeContact(), order.getStatus()});
            }
            contents.add(content);
        }
        try {
            generator.generate(sheetNames, headerList, contents);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

    }

    private static List<Order> getOrderListByStatus(List<Order> orderList, String status) {
        List<Order> subOrderList = new ArrayList<>();
        for (Order order : orderList) {
            if (order.getStatus().equals(status)) {
                subOrderList.add(order);
            }
        }
        return subOrderList;
    }

    public static void exportDispatches(String fileName, List<Dispatch> dispatchList) {
        logger.info("Export Dispatches to excel");
        ExcelGenerator generator = new ExcelGenerator(fileName);
        List<String[]> headerList = new ArrayList<>();
        List<String> sheetNames = new ArrayList<>();
        sheetNames.add("菜品配送");
        headerList.add(new String[]{"菜品", "总量", "单位", "配送信息"});
        List<List<String[]>> contents = new ArrayList<>();
        List<String[]> content = new ArrayList<>();
        for (Dispatch dispatch : dispatchList) {
            content.add(new String[]{dispatch.getProduct().getName(), String.valueOf(dispatch.getQuantity()), dispatch.getUnit(), formatDispatchList(dispatch.getOrderInfoList())});
        }
        contents.add(content);
        try {
            generator.generate4Dispatches(sheetNames, headerList, contents);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }

    private static String formatDispatchList(List<String> orderInfoList) {
        StringBuffer sb = new StringBuffer();
        for (String orderInfo : orderInfoList) {
            sb.append(orderInfo).append("\r\n");
        }
        return sb.toString();
    }
}
