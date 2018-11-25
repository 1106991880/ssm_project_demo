/**
 * @author:稀饭
 * @time:下午4:39:46
 * @filename:LoginUserDao.java
 */
package cn.springmvc.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import cn.springmvc.model.LoginUser;
@Component
public interface LoginUserDao {
	public void saveLoginUser(LoginUser loginUser);
	public List<LoginUser> getLoginUsers();
}
