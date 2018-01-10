package com.eduask.services;

import java.util.Date;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.subject.Subject;
import org.eclipse.jdt.internal.compiler.tool.EclipseCompiler;

import sun.security.util.ECKeySizeParameterSpec;

public class ShiroService {
	//此时用user登录，访问此方法会报错，可以通过spring的注解异常处理，返回到异常页面
	@RequiresRoles("admin")
	public void testMethod(){
		System.out.println("我是shiro的service层:"+new Date());
		Subject subject = SecurityUtils.getSubject(); 
		Session session = subject.getSession();
		Object val = session.getAttribute("wxl");
		System.out.println(val);
	}
}
