package createsh.service;


import createsh.pojo.Order;
import createsh.pojo.OrderStatus;
import createsh.pojo.message.req.TextMessage;
import createsh.pojo.message.resp.Article;
import createsh.pojo.message.resp.NewsMessage;
import createsh.util.MessageUtil;
import createsh.util.PropertyHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * Created by darlingtld on 2015/4/26.
 */
@Service
public class EventService {

    @Autowired
    private OrderService orderService;

    private static final String ZUIXINDINGDAN = "zxdd";
    private static final String XIUGAIDINGDAN = "xgdd";

    public Set<String> getCodeSet() {
        return codeSet;
    }

    private Set<String> codeSet = new HashSet<>();

    @PostConstruct
    private void init() {
        codeSet.add(ZUIXINDINGDAN);
        codeSet.add(XIUGAIDINGDAN);
    }

    public String doAboutUs(String fromUserName, String toUserName) {
        NewsMessage newsMessage = new NewsMessage();
        newsMessage.setToUserName(fromUserName);
        newsMessage.setFromUserName(toUserName);
        newsMessage.setCreateTime(new Date().getTime());
        newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
        newsMessage.setFuncFlag(0);
        List<Article> articleList = new ArrayList<>();
        Article article = new Article();
        article.setTitle("公司简介");
        article.setDescription("点击查看店铺地址、联系方式、营业时间");
        article.setPicUrl(PropertyHolder.SERVER);
        article.setUrl(PropertyHolder.SERVER);
        articleList.add(article);
        newsMessage.setArticleCount(articleList.size());
        newsMessage.setArticles(articleList);
        return MessageUtil.messageToXml(newsMessage);
    }

    public String doProductInquiry(String fromUserName, String toUserName) {
        NewsMessage newsMessage = new NewsMessage();
        newsMessage.setToUserName(fromUserName);
        newsMessage.setFromUserName(toUserName);
        newsMessage.setCreateTime(new Date().getTime());
        newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
        newsMessage.setFuncFlag(0);
        List<Article> articleList = new ArrayList<>();
        Article article = new Article();
        article.setTitle("商品查询");
        article.setDescription("点击查询商品");
        article.setPicUrl(PropertyHolder.SERVER);
        article.setUrl(PropertyHolder.SERVER);
        articleList.add(article);
        newsMessage.setArticleCount(articleList.size());
        newsMessage.setArticles(articleList);
        return MessageUtil.messageToXml(newsMessage);
    }

    public String doPostSaleService(String fromUserName, String toUserName) {
        TextMessage textMessage = new TextMessage();
        textMessage.setToUserName(fromUserName);
        textMessage.setFromUserName(toUserName);
        textMessage.setCreateTime(new Date().getTime());
        textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
        StringBuffer sb = new StringBuffer();
        sb.append("田园到餐桌零距离，为您提供最高品质的主食").append("\n");
        sb.append("如果您有任何问题，请拨打售后服务热线").append("\n");
        sb.append("400-000-0000").append("\n");
        textMessage.setContent(sb.toString());
        return MessageUtil.messageToXml(textMessage);
    }

    public String doGetNewOrders(String fromUserName, String toUserName) {
        List<Order> orderList = orderService.getByStatus(OrderStatus.NOT_DELIVERED);
        NewsMessage newsMessage = new NewsMessage();
        newsMessage.setToUserName(fromUserName);
        newsMessage.setFromUserName(toUserName);
        newsMessage.setCreateTime(new Date().getTime());
        newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
        newsMessage.setFuncFlag(0);
        List<Article> articleList = new ArrayList<>();
        Article article = new Article();
        article.setTitle("最新订单——共有" + orderList.size() + "笔新订单");
//        article.setDescription("送达最新订单查看——目前共有" + orderList.size() + "笔新订单");
        article.setPicUrl(PropertyHolder.SERVER + "/images/latest_order_inquiry.jpg");
        articleList.add(article);
        if (orderList.isEmpty()) {
            Article oArticle = new Article();
            oArticle.setTitle("暂无新订单");
            articleList.add(oArticle);
        } else {
            for (int i = 0; i < Math.min(5, orderList.size()); i++) {
                Order order = orderList.get(i);
                Article oArticle = new Article();
                oArticle.setTitle(String.format("下单时间:%s\n用户信息:%s", order.getOrderTs(), order.getBuyerInfo()));
                oArticle.setUrl(PropertyHolder.SERVER + "/checkorder.html#/order/details/" + order.getId());
                articleList.add(oArticle);
            }
        }
        newsMessage.setArticleCount(articleList.size());
        newsMessage.setArticles(articleList);
        return MessageUtil.messageToXml(newsMessage);
    }

    public String respond(String content, String fromUserName, String toUserName) {
        String msg = null;
        switch (content) {
            case ZUIXINDINGDAN:
                msg = doGetNewOrders(fromUserName, toUserName);
                break;
            case XIUGAIDINGDAN:
                msg = doModifyOrders(fromUserName, toUserName);
                break;
        }
        return msg;
    }

    private String doModifyOrders(String fromUserName, String toUserName) {
        List<Order> orderList = orderService.getByStatus(OrderStatus.NOT_DELIVERED);
        NewsMessage newsMessage = new NewsMessage();
        newsMessage.setToUserName(fromUserName);
        newsMessage.setFromUserName(toUserName);
        newsMessage.setCreateTime(new Date().getTime());
        newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
        newsMessage.setFuncFlag(0);
        List<Article> articleList = new ArrayList<>();
        Article article = new Article();
        article.setTitle("修改订单——修改未配送订单重量");
        article.setPicUrl(PropertyHolder.SERVER + "/images/modify_order.png");
        articleList.add(article);
        if (orderList.isEmpty()) {
            Article oArticle = new Article();
            oArticle.setTitle("暂无新订单");
            articleList.add(oArticle);
        } else {
            article.setUrl(PropertyHolder.SERVER+"/modifyorder.html");
        }
        newsMessage.setArticleCount(articleList.size());
        newsMessage.setArticles(articleList);
        return MessageUtil.messageToXml(newsMessage);
    }

    public String doCodeIntro(String fromUserName, String toUserName) {
        TextMessage textMessage = new TextMessage();
        textMessage.setToUserName(fromUserName);
        textMessage.setFromUserName(toUserName);
        textMessage.setCreateTime(new Date().getTime());
        textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
        StringBuffer sb = new StringBuffer();
        sb.append("代码介绍").append("\n");
        sb.append(ZUIXINDINGDAN).append(" : ").append("最新订单").append("\n");
        sb.append(XIUGAIDINGDAN).append(" : ").append("修改订单").append("\n");
        textMessage.setContent(sb.toString());
        return MessageUtil.messageToXml(textMessage);
    }
}
