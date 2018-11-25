/**
 * @author:稀饭
 * @time:下午4:37:37
 * @filename:LoginUser.java
 */
package cn.springmvc.model;

import java.io.Serializable;

public class LoginUser implements Serializable{
	private static final long serialVersionUID = 1L;
	private String loginuser_id;
	private String loginuser_ip;
	private String loginuser_address;
	private String loginuser_logintime;
	public String getLoginuser_id() {
		return loginuser_id;
	}
	public void setLoginuser_id(String loginuser_id) {
		this.loginuser_id = loginuser_id;
	}
	public String getLoginuser_ip() {
		return loginuser_ip;
	}
	public void setLoginuser_ip(String loginuser_ip) {
		this.loginuser_ip = loginuser_ip;
	}
	public String getLoginuser_address() {
		return loginuser_address;
	}
	public void setLoginuser_address(String loginuser_address) {
		this.loginuser_address = loginuser_address;
	}
	public String getLoginuser_logintime() {
		return loginuser_logintime;
	}
	public void setLoginuser_logintime(String loginuser_logintime) {
		this.loginuser_logintime = loginuser_logintime;
	}
	
}
