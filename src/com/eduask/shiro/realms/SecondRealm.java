package com.eduask.shiro.realms;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.util.ByteSource;

public class SecondRealm extends AuthenticatingRealm{

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		System.out.println("我是第二个relam");		
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
			credentials = "073d4c3ae812935f23cb3f2a71943f49e082a718";
		}else if("admin".equals(username)){
			credentials = "ce2f6417c7e1d32c1d81a797ee0b499f87c5de06";
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
		String hashAlgorithmName = "SHA1";
		Object credentials = "123456";
		Object salt = ByteSource.Util.bytes("user");
		int hashIterations = 1024;
		Object result = new SimpleHash(hashAlgorithmName, credentials, salt, hashIterations);
		System.out.println(result);
	}

}
