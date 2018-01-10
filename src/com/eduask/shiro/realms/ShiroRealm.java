package com.eduask.shiro.realms;

import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

public class ShiroRealm extends AuthorizingRealm{

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		System.out.println("我是第一个relam");
		//1.把AuthenticationToken转化为UsernamePasswordToken
		UsernamePasswordToken upToken = (UsernamePasswordToken)token;
		//2.从UsernamePasswordToken中获取username
		String username = upToken.getUsername();
		//3.调用数据库的方法，从数据库中查询user的对应用户信息
		System.out.println("从数据库获取的username："+username+"所对应的数据");
		//4.若用户不存在，则抛出异常UnknownAccountException
		if("unknow".equals(username)){
			throw new UnknownAccountException("用户不存在");
		}
		//5.根据用户信息的情况，决定是否需要抛出其他异常
		if("yl".equals(username)){
			throw new LockedAccountException("用户被锁定");
		}
		//6.根据用户的情况，来构建AuthenticationInfo对象并返回,通常使用的实现类SimpleAuthenticationInfo
		//以下信息是从数据库中获取的
		//1)principals：认证的实体信息，可以是username，也可以是数据表中对应的用户的实体类对象
		Object principal = username;
		//2)credentials：密码
		Object credentials = null;
		if("user".equals(username)){
			credentials = "098d2c478e9c11555ce2823231e02ec1";
		}else if("admin".equals(username)){
			credentials = "038bdaf98f2037b31f1e75b5b4c9b26e";
		}
		//3)realmName：当前realm对象的name，调用父类的getName方法获取
		String realmName = getName();
		//4)盐值
		ByteSource credentialsSalt = ByteSource.Util.bytes(username);
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(principal, credentials, credentialsSalt, realmName);
		//SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(principal, credentials, realmName);
		return info;
	}
	//测试加密和盐值加密
	public static void main(String[] args) {
		//这里的加密加的是数据库查询出来的密码
		String hashAlgorithmName = "MD5";
		Object credentials = "123456";
		Object salt = ByteSource.Util.bytes("admin");
		int hashIterations = 1024;
		Object result = new SimpleHash(hashAlgorithmName, credentials, salt, hashIterations);
		System.out.println(result);
	}
	//授权会被shiro回调的方法
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		//1.从PrincipalCollection中获取用户登录的信息  principals是有顺序的，根据realm来的
		Object principal = principals.getPrimaryPrincipal();
		//2.利用登录的用户的信息来给用户赋予权限和角色
		Set<String> roles = new HashSet<String>();
		//如果是user登录，只能访问user页面，如果是admin登录，则能访问user和admin页面
		roles.add("user");
		if("admin".equals(principal)){
			roles.add("admin");    
		}
		//3.创建SimpleAuthorizationInfo，并设置reles属性
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roles);
		//返回SimpleAuthorizationInfo对象
		return info;
	}

}
