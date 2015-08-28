package createsh.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import createsh.dao.ProductDao;
import createsh.pojo.*;
import createsh.util.PathUtil;
import createsh.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by darlingtld on 2015/5/16.
 */
@Service
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ProductDao productDao;

    @Autowired
    private OrderService orderService;

    @Transactional
    public int save(Product product) {
        product.setPrice(Utils.formatDouble(product.getPrice(), 2));
        return productDao.save(product);
    }

    @Transactional
    public List<Product> getList(String type, String category) {
        return productDao.getList(type, category);
    }

    @Transactional
    public List<Product> getList(String category) {
        return productDao.getList(category);
    }

    @Transactional
    public List<Product> getListSortByPinyin(String category, String field, String direction) {
        return productDao.getListSortByPinyin(category, field, direction);
    }

    @Transactional
    public List<Product> getLatest(int limit) {
        return productDao.getLatest(limit);
    }

    @Transactional
    public List<Product> getListByAdminOrder(String category, String wechatId) {
        logger.info("User {} get {}", wechatId, category);
        List<Product> productList = getList(category);

        List<Order> orderList = orderService.getLatestList(wechatId, 5);
        Map<String, AtomicInteger> boughtItemsMap = new HashMap<>();

        for (Order order : orderList) {
            JSONObject jsonObject = JSON.parseObject(order.getBill());
            JSONArray jsonArray = jsonObject.getJSONArray("items");
            for (int i = 0; i < jsonArray.size(); i++) {
                String productName = jsonArray.getJSONObject(i).getString("productName");
                if (boughtItemsMap.containsKey(productName)) {
                    boughtItemsMap.get(productName).incrementAndGet();
                } else {
                    boughtItemsMap.put(productName, new AtomicInteger(1));
                }
            }

        }
        List<Map.Entry<String, AtomicInteger>> sortedFavProductList = new ArrayList<>(boughtItemsMap.entrySet());
        Collections.sort(sortedFavProductList, new Comparator<Map.Entry<String, AtomicInteger>>() {

            @Override
            public int compare(Map.Entry<String, AtomicInteger> o1, Map.Entry<String, AtomicInteger> o2) {
                return o1.getValue().intValue() - o2.getValue().intValue();
            }
        });

        LinkedList<Product> retProductList = new LinkedList<>();
        for (Product product : productList) {
            for (Map.Entry<String, AtomicInteger> entry : sortedFavProductList) {
                if (retProductList.contains(product)) continue;
                if (product.getName().equals(entry.getKey())) {
                    retProductList.addFirst(product);
                }
            }
            if (!retProductList.contains(product))
                retProductList.addLast(product);
        }
        return retProductList;
    }

    @Transactional
    public List<Product> getListByFavourites(String category, String wechatId) {
        logger.info("User {} get {}", wechatId, category);
        List<Product> productList = getList(category);

        List<Order> orderList = orderService.getLatestList(wechatId, 5);
        Map<String, AtomicInteger> boughtItemsMap = new HashMap<>();

        for (Order order : orderList) {
            JSONObject jsonObject = JSON.parseObject(order.getBill());
            JSONArray jsonArray = jsonObject.getJSONArray("items");
            for (int i = 0; i < jsonArray.size(); i++) {
                String productName = jsonArray.getJSONObject(i).getString("productName");
                if (boughtItemsMap.containsKey(productName)) {
                    boughtItemsMap.get(productName).incrementAndGet();
                } else {
                    boughtItemsMap.put(productName, new AtomicInteger(1));
                }
            }

        }
        List<Map.Entry<String, AtomicInteger>> sortedFavProductList = new ArrayList<>(boughtItemsMap.entrySet());
        Collections.sort(sortedFavProductList, new Comparator<Map.Entry<String, AtomicInteger>>() {

            @Override
            public int compare(Map.Entry<String, AtomicInteger> o1, Map.Entry<String, AtomicInteger> o2) {
                return o1.getValue().intValue() - o2.getValue().intValue();
            }
        });

        LinkedList<Product> retProductList = new LinkedList<>();
        for (Product product : productList) {
            for (Map.Entry<String, AtomicInteger> entry : sortedFavProductList) {
                if (retProductList.contains(product)) continue;
                if (product.getName().equals(entry.getKey())) {
                    retProductList.addFirst(product);
                }
            }
            if (!retProductList.contains(product))
                retProductList.addLast(product);
        }
        return retProductList;
    }

    @Transactional
    public List<Product> getOnsaleList(int limit) {
        return productDao.getOnsaleList(limit);
    }

    @Transactional
    public void upsert(Product product) {
        product.setDataChangeLastTime(new Timestamp(System.currentTimeMillis()));
        productDao.saveOrUpdateByName(product);
    }

    @Transactional
    public List<Product> getAll() {
        return productDao.getAll();
    }

    @Transactional
    public Product getById(int id) {
        return productDao.getById(id);
    }

    @Transactional
    public void update(Product product) {
        Product productInDB = productDao.getById(product.getId());
        if (productInDB != null) {
            productInDB.setName(product.getName());
            productInDB.setDescription(product.getDescription());
            productInDB.setType(product.getType());
            productInDB.setCategory(product.getCategory());
            productInDB.setPrice(Utils.formatDouble(product.getPrice(), 2));
            productInDB.setUnit(product.getUnit());
            productInDB.setDataChangeLastTime(new Timestamp(System.currentTimeMillis()));
            if (product.getPicurl() != null) {
                productInDB.setPicurl(product.getPicurl());
            }
        }
        productDao.update(productInDB);
        if (product.getProcindex() < 0.01 && product.getProcprice() < 0.01) {
            return;
        } else {
            Procurement procurement = new Procurement();
            procurement.setProductId(product.getId());
            procurement.setProcprice(product.getProcprice());
            procurement.setProcindex(product.getProcindex());
            procurement.setDate(new Timestamp(System.currentTimeMillis()));
            productDao.saveOrUpdateProcurement(procurement);
        }
    }

    @Transactional
    public void saveOrUpdateProcurement(Procurement procurement) {
        productDao.saveOrUpdateProcurement(procurement);
    }

    public Map<Type, List<Category>> getTypeMap() {
        Map<Type, List<Category>> typeListMap = new TreeMap<>();
        List<Category> cardList = new ArrayList<>();
        cardList.add(Category.LIPINKA);
        typeListMap.put(Type.CARD, cardList);
        List<Category> machineList = new ArrayList<>();
        machineList.add(Category.NIANMIJI);
        typeListMap.put(Type.MACHINE, machineList);
        List<Category> shucaishuiguoList = new ArrayList<>();
        shucaishuiguoList.add(Category.YECAILEI);
        shucaishuiguoList.add(Category.GENJINGLEI);
        shucaishuiguoList.add(Category.QIEGUOLEI);
        shucaishuiguoList.add(Category.DOULEI);
        shucaishuiguoList.add(Category.CONGJIANGSUAN);
        shucaishuiguoList.add(Category.JUNLEI);
        shucaishuiguoList.add(Category.TECAI);
        typeListMap.put(Type.SHUCAISHUIGUO, shucaishuiguoList);
        List<Category> qinroudanleiList = new ArrayList<>();
        qinroudanleiList.add(Category.ZHUROU);
        qinroudanleiList.add(Category.YANGROU);
        qinroudanleiList.add(Category.NIUROU);
        qinroudanleiList.add(Category.JIROU);
        qinroudanleiList.add(Category.YAROU);
        qinroudanleiList.add(Category.QINLEI);
        qinroudanleiList.add(Category.QINDANLEI);
        qinroudanleiList.add(Category.SHUSHIJIAGONG);
        typeListMap.put(Type.QINROUDANLEI, qinroudanleiList);
        List<Category> shuichandonghuoList = new ArrayList<>();
        shuichandonghuoList.add(Category.HAIXIANSHUICHAN);
        shuichandonghuoList.add(Category.YUWANHUOGUO);
        typeListMap.put(Type.SHUICHANDONGHUO, shuichandonghuoList);
        List<Category> mimianliangyouList = new ArrayList<>();
        mimianliangyouList.add(Category.DAMI);
        mimianliangyouList.add(Category.MIANFEIMIANTIAO);
        mimianliangyouList.add(Category.SHIYONGYOU);
        mimianliangyouList.add(Category.ZALIANG);
        mimianliangyouList.add(Category.MIANDIAN);
        mimianliangyouList.add(Category.HONGBEIZUOLIAO);
        typeListMap.put(Type.MIMIANLIANGYOU, mimianliangyouList);
        List<Category> tiaoliaoqitaList = new ArrayList<>();
        tiaoliaoqitaList.add(Category.TIAOWEIPIN);
        tiaoliaoqitaList.add(Category.FENSIFENTIAO);
        tiaoliaoqitaList.add(Category.DOUZHIPIN);
        tiaoliaoqitaList.add(Category.GANHUO);
        tiaoliaoqitaList.add(Category.TIAOWEIZHIJIANG);
        tiaoliaoqitaList.add(Category.JIANGYOUCU);
        tiaoliaoqitaList.add(Category.YANCAIGUANTOU);
        typeListMap.put(Type.TIAOLIAOQITA, tiaoliaoqitaList);
        List<Category> canchuyongpinList = new ArrayList<>();
        canchuyongpinList.add(Category.ZHIPINSHIJIN);
        canchuyongpinList.add(Category.CANYINYONGJU);
        typeListMap.put(Type.CANCHUYONGPIN, canchuyongpinList);
        List<Category> jiushuiyinliaoList = new ArrayList<>();
        jiushuiyinliaoList.add(Category.YINLIAO);
        jiushuiyinliaoList.add(Category.YINYONGSHUI);
        typeListMap.put(Type.JIUSHUIYINLIAO, jiushuiyinliaoList);
        return typeListMap;
    }

    @Transactional
    public List<Product> getMostBought(String wechatid, String type) {
        Calendar now = Calendar.getInstance();
        Calendar then = Calendar.getInstance();
        then.add(Calendar.MONTH, -3);
        return orderService.getListByTimeFrame(wechatid, then, now, type);
    }

    @Transactional
    public List<Procurement> getProcurement() {
        return productDao.getProcurement();
    }

    @Transactional
    public void delete(int productId) {
        Product product = getById(productId);
        File picFile = new File(PathUtil.getWebInfPath() + "/" + product.getPicurl());
        picFile.delete();
        logger.info("delete " + picFile.getAbsolutePath());
        productDao.delete(productId);
    }

    @Transactional
    public void saveProductSortOrder(List<ProductOrder> productOrderList) {
        productDao.saveProductSortOrder(productOrderList);
    }

    @Transactional
    public List<Product> getListByType(String type) {
        return productDao.getByType(type);

    }

    @Transactional
    public void saveCardCode(CardCode cardCode) {
        productDao.saveCardCode(cardCode);
    }

    @Transactional
    public List<CardCode> getCardcodeByOpenid(String openid) {
        return productDao.getCardcodeByOpenid(openid);
    }
}
