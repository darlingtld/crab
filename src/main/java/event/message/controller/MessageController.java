package event.message.controller;

import event.message.pojo.Message;
import event.message.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by darlingtld on 2015/8/9 0009.
 */
@Controller
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @RequestMapping(value = "/wechatid/{wechatid}", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Message> getCouponList(@PathVariable("wechatid") String wechatid) {
        return messageService.getMessageList(wechatid);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST, headers = "Content-Type=application/json")
    public
    @ResponseBody
    void sendMessage(@RequestBody Message message) {
        messageService.createMessage(message);
    }

}
