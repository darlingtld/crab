package createsh.dao;

import createsh.pojo.Order;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by darlingtld on 2015/5/16.
 */
@Repository
public class OrderDao {

    @Autowired
    private SessionFactory sessionFactory;

    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public void save(Order order) {
        try {
            sessionFactory.getCurrentSession().save(order);
        } catch (Exception e) {
            e.printStackTrace();
            order.setUserId("songda user");
            sessionFactory.getCurrentSession().save(order);
        }
    }

    public List<Order> getList(String wechatid) {
        return sessionFactory.getCurrentSession().createQuery(String.format("from Order where wechatId = '%s' order by id desc", wechatid)).list();
    }

    public Order getById(int orderId) {
        return (Order) sessionFactory.getCurrentSession().get(Order.class, orderId);
    }

    public List<Order> getAll() {
        return sessionFactory.getCurrentSession().createQuery(String.format("from Order order by orderTs desc")).list();

    }

    public boolean update(Order order) {
        try {
            sessionFactory.getCurrentSession().update(order);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Order> getLatestList(String wechatId, int count) {
        return sessionFactory.getCurrentSession().createQuery(String.format("from Order where wechatId = '%s' order by orderTs desc", wechatId)).setMaxResults(count).list();
    }


    public boolean isConfirmCodeExisted(String code) {
        return sessionFactory.getCurrentSession().createQuery(String.format("from Order where confirmCode = '%s'", code)).uniqueResult() == null ? false : true;
    }

    public Order getByConfirmCode(String confirmCode) {
        return (Order) sessionFactory.getCurrentSession().createQuery(String.format("from Order where confirmCode = '%s'", confirmCode)).uniqueResult();
    }

    public List<Order> getListByTimeFrame(String wechatid, Calendar then, Calendar now) {
        return (List<Order>) sessionFactory.getCurrentSession().createQuery(String.format("from Order where wechatId = '%s' and orderTs >= '%s' and orderTs <= '%s'", wechatid, formatter.format(then.getTime()), formatter.format(now.getTime()))).list();
    }

    public List<Order> getOrderListByStatus(String notDelivered) {
        return sessionFactory.getCurrentSession().createQuery(String.format("from Order where status='%s' order by orderTs desc", notDelivered)).list();
    }

    public void deleteOrder(int orderId) {
        sessionFactory.getCurrentSession().createQuery(String.format("delete Order where id=%d", orderId)).executeUpdate();
    }
}
