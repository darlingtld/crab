package crab.dao;

import crab.pojo.*;
import crab.service.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.*;

/**
 * Created by darlingtld on 2015/5/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("file:src/main/webapp/WEB-INF/mvc-dispatcher-servlet.xml")
public class CardDaoTest {

    @Autowired
    private ProductService productService;

    @Test
    public void saveCardcode() {
        String openId = "o5Irvt5957jQ4xmdHmDp59epk0UU";
        List<Product> productList = productService.getListByType(Type.CARD.toString());
        for (Product product : productList) {
            CardCode cardCode = new CardCode();
            cardCode.setCode(String.valueOf(System.currentTimeMillis()));
            cardCode.setStartTime(new Date());
            cardCode.setEndTime(new Date());
            cardCode.setUsed(false);
            cardCode.setProduct(product);
            productService.saveCardCode(cardCode);

        }
    }

    @Test
    public void getCardcode() {
        String openId = "o5Irvt5957jQ4xmdHmDp59epk0UU";
        List<CardCode> cardCodeList = productService.getCardcodeByOpenid(openId);
        for (CardCode cardCode : cardCodeList) {
            System.out.println(cardCode.getProduct());
        }
    }

}

