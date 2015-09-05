package crab.controller;

import com.alibaba.fastjson.JSONArray;
import crab.pojo.CardCode;
import crab.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/card")
public class CardcodeController {

    private static final Logger logger = LoggerFactory.getLogger(CardcodeController.class);

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/check/{cardcode}/{password}", method = RequestMethod.POST, headers = "Content-Type=application/json")
    public
    @ResponseBody
    void checkCard(@PathVariable("cardcode") String cardcode, @PathVariable("password") String password, HttpServletResponse response) {
        productService.checkCardExistence(cardcode, password);
    }

    @RequestMapping(value = "/expense", method = RequestMethod.POST, headers = "Content-Type=application/json")
    public
    @ResponseBody
    void expenseCard(@RequestBody CardCode cardCode, HttpServletResponse response) {
        productService.expenseCard(cardCode);
    }

    @RequestMapping(value = "/get/{cardcode}", method = RequestMethod.GET)
    public
    @ResponseBody
    CardCode getCard(@PathVariable("cardcode") String cardCode) {
        return productService.getCardByCode(cardCode);
    }

    @RequestMapping(value = "/get/all", method = RequestMethod.GET)
    public
    @ResponseBody
    List<CardCode> getAllCard() {
        return productService.getCardAll();
    }

    @RequestMapping(value = "/presenturl/{presenturl}", method = RequestMethod.POST, headers = "Content-Type=application/json")
    public
    @ResponseBody
    void expenseCard(@PathVariable("presenturl") String presentUrl, HttpServletResponse response) {
        productService.givePresent(presentUrl);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST, headers = "Content-Type=application/json")
    public
    @ResponseBody
    void createCard(@RequestBody CardCode cardCode) {
        productService.saveCardCode(cardCode);
    }

    @RequestMapping(value = "/delete/{cardId}", method = RequestMethod.POST, headers = "Content-Type=application/json")
    public
    @ResponseBody
    void deleteCard(@PathVariable("cardId") Integer cardId) {
        productService.deleteCardcode(cardId);
    }

    @RequestMapping(value = "/productMap", method = RequestMethod.GET)
    public
    @ResponseBody
    JSONArray getProductMap() {
        return productService.getProductMap();
    }

}