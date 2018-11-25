/**
 * @author:稀饭
 * @time:下午4:46:07
 * @filename:LoginUserService.java
 */
package cn.springmvc.service;

import java.util.List;

import cn.springmvc.dao.LoginUserDao;
import cn.springmvc.model.LoginUser;

public interface LoginUserService {
	public void saveLoginUser(LoginUser loginUser);
	public List<LoginUser> getLoginUsers();
	
}
