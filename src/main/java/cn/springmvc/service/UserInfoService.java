package cn.springmvc.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.springmvc.model.UserInfo;
import cn.springmvc.utildao.PageInfo;

public interface UserInfoService {

	public UserInfo login(UserInfo user);

	public String updatePwd(String user_id, String newpwd);

	public ArrayList<UserInfo> queryUsers(Map<String, String> map,
			PageInfo<UserInfo> pageInfo);

	public List<UserInfo> queryForJson();

	public UserInfo getUser(String userId);

	public void saveUser(UserInfo userInfo);

	public void updateUser(UserInfo userInfo);

	public void deleteUser(String[] idItem);

	public UserInfo checkUserName(String userName);

	public List<UserInfo> getAllUsers();

	public UserInfo downloadFile(String userId);

	public int deleteFile(String userId);

	public int saveUserRole(String userId_role, String roleIds);

	public void deleteUserRoleByUserId(String userId);

	public ArrayList<UserInfo> queryUsersInManyName(
			Map<String, ArrayList<String>> map);
}
