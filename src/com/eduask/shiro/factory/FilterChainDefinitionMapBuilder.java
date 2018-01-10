package com.eduask.shiro.factory;

import java.util.LinkedHashMap;

public class FilterChainDefinitionMapBuilder {
	//返回值必须是LinkedHashMap
	public LinkedHashMap<String, String> buildFilterChainDefinitionMap(){
		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		map.put("/login.jsp", "anon");
		map.put("/shiro/login", "anon");
		map.put("/shiro/logout", "logout");
		map.put("/admin.jsp", "authc,roles[admin]");//必须通过验证才能访问
		map.put("/user.jsp", "authc,roles[user]");	//必须通过验证才能访问
		map.put("/list.jsp", "user");  //通过记住我访问
		map.put("/**", "authc");
		return map;
	}
}
