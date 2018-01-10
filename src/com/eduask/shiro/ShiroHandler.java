package com.eduask.shiro;

import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.eduask.services.ShiroService;

@Controller
@RequestMapping("/shiro")
public class ShiroHandler {
	@Autowired
	private ShiroService shiroService;
	@RequestMapping("/testShiroAnnotation")
	public String testShiroAnnotation(HttpSession session){
		session.setAttribute("wxl", "今天我没有昨天困");
		shiroService.testMethod();
		return "redirect:/list.jsp";
	}
	
	@RequestMapping("/login")
	public String login(@RequestParam("username")String username,@RequestParam("password")String password){
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.isAuthenticated()) {
        	//我们把用户名和密码封装为UsernamePasswordToken对象
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);

           //记住我
            token.setRememberMe(true);
            try {
            	 //执行登陆
                currentUser.login(token);
            }  
			//抛出认证时异常的父类
            catch (AuthenticationException ae) {
               System.out.println("登录失败:"+ae.getMessage());
            }
        }
		return "redirect:/list.jsp";
	}
}
