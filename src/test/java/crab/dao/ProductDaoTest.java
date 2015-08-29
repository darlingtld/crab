package crab.dao;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import crab.crawler.ImageCrawler;
import crab.pojo.*;
import crab.service.OrderService;
import crab.service.ProductService;
import crab.util.Utils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.*;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by darlingtld on 2015/5/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("file:src/main/webapp/WEB-INF/mvc-dispatcher-servlet.xml")
public class ProductDaoTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @Test
    public void testProductSave() {
        Product product = new Product();
        product.setName("青菜");
        product.setDescription("最好吃的青菜");
        product.setCategory(Category.YECAILEI);
        product.setPrice(10.9);
        int id = productService.save(product);
        System.out.println(id);
    }

    @Test
    public void updateProductImages() {
        List<Product> productList = productService.getAll();
        for (Product product : productList) {
            if (null != product.getPicurl() && product.getPicurl().contains("pic")) {
                continue;
            }
            String picUUID = product.generatePicurlHash();
            try {
                ImageCrawler.getProductImg(product, picUUID);
                product.setPicurl("images/" + picUUID + ".jpg");
                productService.upsert(product);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
//            break;
//
        }
    }

    @Test
    public void updateProductImageById() {
//        for (int i = 127; i < 212; i++) {
        try {
            Product product = productService.getById(211);
            String picUUID = product.generatePicurlHash();
            ImageCrawler.getProductImg(product, picUUID);
            product.setPicurl("images/" + picUUID + ".jpg");
            productService.upsert(product);
        } catch (Exception e) {
            e.printStackTrace();
//                continue;
        }
//        }
    }

    @Test
    public void importImages() {
        String imgSrcDir = "D:\\MyProjects\\Archive";
        String dstSrcDir = "C:\\Users\\darlingtld\\IdeaProjects\\mycai_main\\src\\main\\webapp\\product_images";
        File file = new File(imgSrcDir);
        File[] imgFiles = file.listFiles();
        for (File imgFile : imgFiles) {
            System.out.println(imgFile.getName());
            Product product = new Product();
            product.setName(imgFile.getName().substring(0, imgFile.getName().indexOf(".")));
            product.setType(Type.TIAOLIAOQITA);
            product.setCategory(Category.DOUZHIPIN);
            product.setDescription("精选" + imgFile.getName().substring(0, imgFile.getName().indexOf(".")));
            product.setPrice(999.0);
            product.setUnit("斤");
            product.setPicurl("product_images/" + product.generatePicurlHash() + ".jpg");
            product.setDataChangeLastTime(new Timestamp(System.currentTimeMillis()));
//            product.setProcindex(1.15);
//            product.setPrice(product.getPrice() * 1.15);
//            productService.update(product);
            System.out.println(product);
            imgFile.renameTo(new File(dstSrcDir + "\\" + product.generatePicurlHash() + ".jpg"));
            productService.save(product);
//            break;
        }
    }

    @Test
    public void importImages3() {
        String imgSrcDir = "D:\\MyProjects\\Archive";
        String dstSrcDir = "C:\\Users\\darlingtld\\IdeaProjects\\mycai_main\\src\\main\\webapp\\product_images";
        File file = new File(imgSrcDir);
        File[] imgFiles = file.listFiles();
        List<Product> productList = productService.getAll();
        for (File imgFile : imgFiles) {
            System.out.println(imgFile.getName());
            for(Product product : productList){
                if(imgFile.getName().substring(0, imgFile.getName().indexOf(".")).equals(product.getName())){
                    System.out.println(product.getName());
                    product.setPicurl("product_images/" + product.generatePicurlHash() + ".jpg");
                    imgFile.renameTo(new File(dstSrcDir + "\\" + product.generatePicurlHash() + ".jpg"));
                    productService.update(product);
                }
            }
        }
    }

    @Test
    public void getProductByFavourites() {
        String category = "yecailei";
        String wechatId = "o5Irvt5957jQ4xmdHmDp59epk0UU";
        List<Product> productList = productService.getList(category);
        for (Product product : productList) {
            System.out.println(product);
        }
        List<Order> orderList = orderService.getLatestList(wechatId, 5);
        Map<String, AtomicInteger> boughtItemsMap = new HashMap<>();

        for (Order order : orderList) {
            System.out.println(order);
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
        List<Map.Entry<String, AtomicInteger>> sortedProductList = new ArrayList<>(boughtItemsMap.entrySet());
        Collections.sort(sortedProductList, new Comparator<Map.Entry<String, AtomicInteger>>() {

            @Override
            public int compare(Map.Entry<String, AtomicInteger> o1, Map.Entry<String, AtomicInteger> o2) {
                return o1.getValue().intValue() - o2.getValue().intValue();
            }
        });

        for (Map.Entry<String, AtomicInteger> entry : sortedProductList) {
            for (Product product : productList) {
                if (product.getName().equals(entry.getKey())) {
                    productList.set(0, product);
                    break;
                }
            }
        }
        System.out.println(sortedProductList);
        System.out.println(productList);
    }

    @Test
    public void getProductByTimeFrame() {
        int frequency = 6;
        String wechatid = "o5Irvt5957jQ4xmdHmDp59epk0UU";
        Calendar now = Calendar.getInstance();
        Calendar then = Calendar.getInstance();
        then.add(Calendar.MONTH, -frequency);
        List<Product> productList = orderService.getListByTimeFrame(wechatid, then, now, "shucaishuiguo");
        System.out.println(productList.size());
        for (Product product : productList) {
            System.out.println(product);
        }
    }

    @Test
    public void updateProductPrice() throws IOException {
        File file = new File("D:\\MyProjects\\productprice.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while ((line = br.readLine()) != null) {
            String[] strings = line.split("\\s+");
            Double price;
            if (strings.length < 2) {
                continue;
            } else if (strings.length == 3) {
                price = 0.01;
            } else {
                price = Double.parseDouble(strings[2]);
            }
            String name = strings[0];

            System.out.println(name + " >> " + price);

            List<Product> productList = productService.getAll();
            boolean hasFound = false;
            for (Product product : productList) {
                if (product.getName().equals(name)) {
                    product.setPrice(price);
                    productService.update(product);
                    hasFound = true;
                    break;
                }
            }
            if (!hasFound) {
                Product product = new Product();
                product.setName(name);
                product.setDescription("精选" + name);
                product.setPrice(price);
                product.setUnit("斤");
                product.setType(Type.SHUCAISHUIGUO);
                product.setCategory(Category.YECAILEI);
                productService.save(product);
            }
        }
    }

    @Test
    public void importImages2() {
        String imgSrcDir = "C:\\Users\\darlingtld\\IdeaProjects\\mycai_main\\src\\main\\webapp\\product_images";
        File file = new File(imgSrcDir);
        File[] imgFiles = file.listFiles();
        List<Product> productList = productService.getAll();

        for (File imgFile : imgFiles) {
            for (Product product : productList) {
//                System.out.println(product.getName() + "==" + imgFile.getName().substring(0, imgFile.getName().indexOf(".")));
                if (product.generatePicurlHash().equals(imgFile.getName().substring(0, imgFile.getName().indexOf(".")))) {
                    product.setPicurl("product_images/" + product.generatePicurlHash() + ".jpg");
                    productService.update(product);
                    imgFile.renameTo(new File(imgSrcDir + "\\" + product.generatePicurlHash() + ".jpg"));
                    break;
                }
            }

        }
    }

    @Test
    public void updateAllProcurement() {
        List<Product> productList = productService.getAll();
        for (Product product : productList) {
            product.setProcprice(product.getPrice());
            product.setProcindex(1.15);
            product.setPrice(product.getPrice() * 1.15);
            productService.update(product);
        }
    }

    @Test
    public void formatPrice() {
        List<Procurement> procurementList = productService.getProcurement();
        for (Procurement product : procurementList) {
            product.setProcprice(Utils.formatDouble(product.getProcprice(), 2));
            productService.saveOrUpdateProcurement(product);
        }
    }

    @Test
    public void testOverflow() {
        System.out.println(1.15 * 2.3);
        System.out.println(Utils.formatDouble(1.15 * 2.3, 2));

    }
}

