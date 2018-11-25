/**
 * @author:稀饭
 * @time:下午4:46:44
 * @filename:LoginUserServiceImpl.java
 */
package cn.springmvc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.springmvc.dao.LoginUserDao;
import cn.springmvc.model.LoginUser;
import cn.springmvc.service.LoginUserService;
@Service
public class LoginUserServiceImpl implements LoginUserService {
	@Autowired
	private LoginUserDao loginUserDao;
	/** @Title: saveLoginUser 
	 * @Description: TODO
	 * @param loginUser  
	 */
	@Override
	public void saveLoginUser(LoginUser loginUser) {
		// TODO Auto-generated method stub
		loginUserDao.saveLoginUser(loginUser);
	}

	/** @Title: getList 
	 * @Description: TODO
	 * @return  
	 */
	@Override
	public List<LoginUser> getLoginUsers() {
		// TODO Auto-generated method stub
		return loginUserDao.getLoginUsers();
	}

}
