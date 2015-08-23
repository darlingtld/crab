package event.message.service;

import event.message.dao.MessageDao;
import event.message.pojo.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by tangl9 on 2015-08-10.
 */
@Service
public class MessageService {
    private Logger logger = LoggerFactory.getLogger(MessageService.class);

    @Autowired
    private MessageDao messageDao;
    @Transactional
    public List<Message> getMessageList(String openid){
        logger.info("Get message of {}", openid);
        return messageDao.getMessageList(openid);
    }

    @Transactional
    public void createMessage(Message message) {
        logger.info("Create message {}", message.toString());
        messageDao.createMessage(message);
    }
}
