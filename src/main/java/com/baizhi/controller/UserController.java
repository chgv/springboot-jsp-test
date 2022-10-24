package com.baizhi.controller;

import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import com.baizhi.utils.VerifyCodeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.yaml.snakeyaml.util.UriEncoder;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


@Controller
@RequestMapping("user")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    /**
     * 用戶登入
     * @return
     */
    @RequestMapping("login")
    public String login(String username,String password,HttpSession session) throws UnsupportedEncodingException {
        log.debug("接收到用戶名:{}, 接收到密碼:{}",username,password);
        try {
            //1.執行登入業務邏輯
            User user = userService.login(username,password);
            //2.登入成功，保存用戶登記標記
            session.setAttribute("user",user);
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/login.jsp?msg="+URLEncoder.encode(e.getMessage(),"UTF-8");
        }
        return "redirect:/employee/list";
    }

    /**
     * user註冊
     * @return
     */
    @RequestMapping("register")
    public String register(User user, String code,HttpSession session) throws UnsupportedEncodingException {
        log.debug("驗證碼: {}",code);
        log.debug("用戶名:{}, 真實姓名:{}, 密碼:{}, 性別:{}",user.getUsername(),user.getRealname(),user.getPassword(),user.getGender());

        try {
            //1.比較驗證碼是否一致
            String sessionCode = session.getAttribute("code").toString();
            if (!sessionCode.equalsIgnoreCase(code)) throw new RuntimeException("驗證碼輸入錯誤!");
            //2.註冊用戶
            userService.register(user);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return "redirect:/regist.jsp?msg="+ URLEncoder.encode(e.getMessage(),"UTF-8");
        }
        return "redirect:/login.jsp";
    }


    /**
     * 用來生成驗證碼方法
     */
    @RequestMapping("generateImageCode")
    public void generateImageCode(HttpSession session, HttpServletResponse response) throws IOException {
        //1.生成隨機字符串
        String code = VerifyCodeUtils.generateVerifyCode(4);
        //2.保存隨機字符串到Session中
        session.setAttribute("code",code);
        //3.將隨機字符串生成圖片
        //4.通過response回應圖片
        response.setContentType("image/png");//指定回應類型
        ServletOutputStream os = response.getOutputStream();
        VerifyCodeUtils.outputImage(220,80,os,code);
    }
}
