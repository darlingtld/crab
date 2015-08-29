package crab.controller;

import crab.pojo.CardCode;
import crab.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/card")
public class CardcodeController {

    private static final Logger logger = LoggerFactory.getLogger(CardcodeController.class);

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/check/{cardcode}", method = RequestMethod.POST, headers = "Content-Type=application/json")
    public
    @ResponseBody
    void checkCard(@PathVariable("cardcode") String cardcode, HttpServletResponse response) {
        productService.checkCardExistence(cardcode);
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

}