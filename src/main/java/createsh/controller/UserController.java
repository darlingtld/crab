package createsh.controller;


import createsh.pojo.User;
import createsh.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by darlingtld on 2015/6/24 0024.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/code/{code}", method = RequestMethod.GET)
    public
    @ResponseBody
    User getUserInformation(@PathVariable("code") String code, HttpServletResponse response) {
        logger.info("Get user information with code {}", code);

        User user = userService.getUserInformation(code);
        if (user == null) {
            response.setStatus(HttpStatus.EXPECTATION_FAILED.value());
            return null;
        }
        logger.info(user.toString());
        return user;
    }

    @RequestMapping(value = "/save_or_update", method = RequestMethod.POST, headers = "Content-Type=application/json")
    public
    @ResponseBody
    User saveUser(@RequestBody User user) {
        logger.info("Save user information {}", user.toString());
        return userService.saveOrUpdate(user);
    }

    @RequestMapping(value = "/wechatId/{wechatId}", method = RequestMethod.GET)
    public
    @ResponseBody
    User getUser(@PathVariable("wechatId") String wechatId) {
        logger.info("Get user information with wechatId {}", wechatId);
        return userService.getUserByWechatId(wechatId);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public
    @ResponseBody
    List<User> getAllUser() {
        logger.info("Get all users");
        return userService.getAll();
    }
}
