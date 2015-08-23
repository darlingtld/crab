package event.message.dao;

import event.message.pojo.Message;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by tangl9 on 2015-08-10.
 */
@Repository
public class MessageDao {
    @Autowired
    private SessionFactory sessionFactory;

    public List<Message> getMessageList(String openid) {
        return sessionFactory.getCurrentSession().createQuery(String.format("from Message where openid='%s' order by ts desc", openid)).list();
    }

    public void createMessage(Message message) {
        sessionFactory.getCurrentSession().save(message);
    }
}
