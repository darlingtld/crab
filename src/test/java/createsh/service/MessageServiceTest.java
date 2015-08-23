package createsh.service;

import event.message.pojo.Message;
import event.message.service.MessageService;
import createsh.pojo.User;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by darlingtld on 2015/8/8 0008.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("file:src/main/webapp/WEB-INF/mvc-dispatcher-servlet.xml")
public class MessageServiceTest {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    String openid = "o5Irvtx1HMDEgD18RESTS_tZ89rQ";

    @Test
    public void createMessageTest() {
        for (User user : userService.getAll()) {
            Message message = new Message();
            message.setOpenid(user.getOpenid());
            message.setContent("送达优惠券功能上线啦");
            DateTime ts = new DateTime();
            message.setTs(ts.toDate());
            message.setRead(false);
            messageService.createMessage(message);
        }
    }

    @Test
    public void getMessageList() {
        for(Message message : messageService.getMessageList(openid)){
            System.out.println(message);
        }
    }

}
