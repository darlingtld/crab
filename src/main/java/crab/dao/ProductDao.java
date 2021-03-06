package crab.dao;

import crab.pojo.CardCode;
import crab.pojo.Procurement;
import crab.pojo.Product;
import crab.pojo.ProductOrder;
import crab.util.Utils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by darlingtld on 2015/5/16.
 */
@Repository
public class ProductDao {

    @Autowired
    private SessionFactory sessionFactory;

    public int save(Product product) {
        int id = (int) sessionFactory.getCurrentSession().save(product);
        return id;
    }

    public Product getById(int id) {
        return (Product) sessionFactory.getCurrentSession().get(Product.class, id);
    }

    public Product getByName(String name) {
        return (Product) sessionFactory.getCurrentSession().createQuery(String.format("from Product where name = '%s'", name)).uniqueResult();
    }

    public void saveOrUpdateByName(Product product) {
        Product productInDB = getByName(product.getName());
        if (productInDB != null) {
            productInDB.setPrice(product.getPrice());
            productInDB.setPicurl(product.getPicurl());
            productInDB.setUnit(product.getUnit());
            productInDB.setDataChangeLastTime(product.getDataChangeLastTime());
            sessionFactory.getCurrentSession().saveOrUpdate(productInDB);
        } else {
            sessionFactory.getCurrentSession().save(product);
        }
    }

    public List<Product> getList(String type, String category) {
        return sessionFactory.getCurrentSession().createQuery(String.format("from Product where type = '%s' and category='%s'", type.toUpperCase(), category.toUpperCase())).list();
    }

    public List<Product> getListSortByPinyin(String category, String field, String direction) {
        return sessionFactory.getCurrentSession().createQuery(String.format("from Product where category='%s' order by convert(%s, gbk)", category.toUpperCase(), field)).list();
    }

    public List<Product> getLatest(int limit) {
        return sessionFactory.getCurrentSession().createQuery(String.format("from Product p order by p.dataChangeLastTime desc")).setMaxResults(limit).list();

    }

    public List<Product> getOnsaleList(int limit) {
        return sessionFactory.getCurrentSession().createQuery(String.format("from Product p where p.onsale = 1 order by p.dataChangeLastTime desc")).setMaxResults(limit).list();
    }

    public List<Product> getAll() {
        return sessionFactory.getCurrentSession().createQuery(String.format("from Product")).list();
    }

    public void update(Product product) {
        sessionFactory.getCurrentSession().update(product);
    }

    public List<Product> getList(String category) {
        return sessionFactory.getCurrentSession().createQuery(String.format("from Product where category='%s' order by orderIndex", category.toUpperCase())).list();
    }

    public List<Procurement> getProcurement() {
        return sessionFactory.getCurrentSession().createQuery(String.format("from Procurement")).list();
    }

    public void saveOrUpdateProcurement(Procurement procurement) {
        synchronized (ProductDao.class) {
            Session session = sessionFactory.getCurrentSession();
            if (session.createQuery(String.format("from Procurement where productId = %s", procurement.getProductId())).uniqueResult() == null) {
                session.save(procurement);
            } else {
                int id = Integer.parseInt(session.createQuery(String.format("select id from Procurement where productId = %s", procurement.getProductId())).uniqueResult().toString());
                procurement.setId(id);
                procurement = (Procurement) session.merge(procurement);
                session.update(procurement);
            }
        }
    }

    public void delete(int productId) {
        sessionFactory.getCurrentSession().createQuery(String.format("delete Product where id=%d", productId)).executeUpdate();
    }

    public void saveProductSortOrder(List<ProductOrder> productOrderList) {
        Session session = sessionFactory.getCurrentSession();
        for (ProductOrder po : productOrderList) {
            session.createQuery(String.format("update Product set orderIndex=%d where id=%d", po.getOrderIndex(), po.getProductId())).executeUpdate();
        }
    }

    public List<Product> getByType(String type) {
        return sessionFactory.getCurrentSession().createQuery(String.format("from Product where type='%s' order by orderIndex asc", type.toUpperCase())).list();
    }

    public void saveCardCode(CardCode cardCode) {
        Session session = sessionFactory.getCurrentSession();
        session.save(cardCode);
        session.flush();
        session.clear();
    }

    public CardCode getUnusedCardCode(Integer productId) {
        List<CardCode> cardCodeList = sessionFactory.getCurrentSession().createQuery(String.format("from CardCode where product.id = %d and used=false and openid is null", productId)).list();
        if (cardCodeList.isEmpty()) {
            return null;
        } else {
            return cardCodeList.get(0);
        }
    }

    public void markUnusedCardCode4User(Integer id, String openid) {
        sessionFactory.getCurrentSession().createQuery(String.format("update CardCode set openid = '%s' where id = %d", openid, id)).executeUpdate();
    }

    public List<CardCode> getCardcodeByOpenid(String openid) {
        return sessionFactory.getCurrentSession().createQuery(String.format("from CardCode where openid='%s' order by startTime desc", openid)).list();
    }

    public CardCode getCardByCode(String cardcode) {
        return (CardCode) sessionFactory.getCurrentSession().createQuery(String.format("from CardCode where code='%s'", cardcode)).uniqueResult();
    }

    public String generateCardCode() {
        String cardcode = Utils.generateCardCode();
        while (getCardByCode(cardcode) != null) {
            cardcode = Utils.generateCardCode();
        }
        return cardcode;

    }

    public List<CardCode> getCardcodeAll() {
        return sessionFactory.getCurrentSession().createQuery(String.format("from CardCode order by startTime desc")).list();
    }

    public void changeCardcodeOwner(String code, String fromOpenid, String toOpenid) {
        sessionFactory.getCurrentSession().createQuery(String.format("update CardCode set openid='%s' where code='%s' and openid='%s'", toOpenid, code, fromOpenid)).executeUpdate();
    }

    public CardCode getCardByCodeAndPassword(String cardcode, String password) {
        return (CardCode) sessionFactory.getCurrentSession().createQuery(String.format("from CardCode where code='%s' and password='%s'", cardcode, password)).uniqueResult();

    }

    public void deleteCardcode(Integer cardId) {
        sessionFactory.getCurrentSession().createQuery(String.format("delete CardCode where id=%d", cardId)).executeUpdate();
    }
}