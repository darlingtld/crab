package crab.controller;


import com.alibaba.fastjson.JSONObject;
import crab.service.MycaiService;
import crab.util.Sign;
import crab.util.SignUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * Created by darlingtld on 2015/2/10.
 */
@Scope("prototype")
@Controller
@RequestMapping("/wechat")
public class WechatController {

    @Autowired
    private MycaiService mycaiService;

    @RequestMapping(method = RequestMethod.GET)
    public void get(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");

        PrintWriter out = response.getWriter();
        if (SignUtil.checkSignature(signature, timestamp, nonce)) {
            out.print(echostr);
        }
        out.close();
    }


    @RequestMapping(method = RequestMethod.POST)
    public void respond(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String respXml = mycaiService.processRequest(request);
        ServletOutputStream outputStream = response.getOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        printStream.write(respXml.getBytes("utf-8"));
        printStream.close();
    }

    @RequestMapping(value = "test", method = RequestMethod.POST)
    public
    @ResponseBody
    String test(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return "test";
    }

    @RequestMapping(value = "wxconfig", method = RequestMethod.GET)
    public
    @ResponseBody
    JSONObject wxconfig(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return Sign.getWXConfig();
    }

}
