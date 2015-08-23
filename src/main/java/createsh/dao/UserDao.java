package createsh.dao;

import createsh.pojo.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by darlingtld on 2015/5/16.
 */
@Repository
public class UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    public void save(User user) {
        try {
            sessionFactory.getCurrentSession().save(user);
        } catch (Exception e) {
            e.printStackTrace();
            user.setNickname("songda user");
            sessionFactory.getCurrentSession().save(user);
        }
    }

    public List<User> getListByRole(String role) {
        return sessionFactory.getCurrentSession().createQuery(String.format("from User where role = '%s' order by id asc", role)).list();
    }

    public List<User> getAll() {
        return sessionFactory.getCurrentSession().createQuery(String.format("from User user")).list();

    }

    public boolean update(User user) {
        try {
            sessionFactory.getCurrentSession().update(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public User getUserByWechatId(String fromUserName) {
        return (User) sessionFactory.getCurrentSession().createQuery(String.format("from User where openid = '%s'", fromUserName)).uniqueResult();
    }

}
