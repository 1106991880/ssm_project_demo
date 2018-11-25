/**
 * @author:稀饭
 * @time:下午8:42:04
 * @filename:UserInfoDao.java
 */
package cn.springmvc.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import cn.springmvc.model.UserInfo;

@Component
public interface UserInfoDao {

	public UserInfo login(@Param("userName") String userName,
			@Param("password") String password);

	public List<UserInfo> queryUsers();

	public int getTotalRows(Map<String, String> map);

	public UserInfo getUser(String userId);

	public void saveUser(UserInfo userInfo);

	public void updateUser(UserInfo userInfo);

	public void deleteUser(String[] idItem);

	public int checkUserName(String userName);

	public List<UserInfo> getAllUsers();

	public UserInfo downloadFile(String userId);

	public int deleteFile(String userId);

	public int deleteDeptId(String deptId);

	public String getUserPassword(String userId);

	public void updatePwd(@Param("user_id") String user_id,
			@Param("newpwd") String newpwd);

	public ArrayList<UserInfo> queryUsers(RowBounds rowBounds,
			Map<String, String> map);

	public UserInfo getUserInfoByUserName(@Param("userName") String userName);

	public ArrayList<UserInfo> queryUsersInManyName(
			Map<String, ArrayList<String>> map);
}
