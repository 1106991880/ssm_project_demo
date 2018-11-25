package cn.springmvc.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.springmvc.dao.DeptInfoDao;
import cn.springmvc.dao.UserInfoDao;
import cn.springmvc.dao.UserRoleDao;
import cn.springmvc.model.DeptInfo;
import cn.springmvc.model.UserInfo;
import cn.springmvc.model.UserRole;
import cn.springmvc.service.UserInfoService;
import cn.springmvc.util.ParameterUtil;
import cn.springmvc.util.StringUtil;
import cn.springmvc.utildao.PageInfo;

@Service
public class UserInfoServiceImpl implements UserInfoService {

	private Logger log = Logger.getLogger(this.getClass());
	@Autowired
	private DeptInfoDao deptDao;
	@Autowired
	private UserInfoDao userInfoDao;
	@Autowired
	private UserRoleDao userRoleDao;

	/**
	 * @Title: login
	 * @Description: TODO
	 * @param user
	 * @return
	 */
	@Override
	public UserInfo login(UserInfo user) {
		// TODO Auto-generated method stub
		UserInfo userInfo = userInfoDao.login(user.getUserName(),
				user.getPassword());
		return userInfo;
	}

	/**
	 * @Title: getPasswordByUserId
	 * @Description: TODO
	 * @param user_id
	 * @return
	 */
	@Override
	public String updatePwd(String user_id, String newpwd) {
		// TODO Auto-generated method stub
		userInfoDao.updatePwd(user_id, newpwd);
		return null;
	}

	/**
	 * @Title: queryUsers
	 * @Description: TODO
	 * @param pageInfo
	 * @param map
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public ArrayList<UserInfo> queryUsers(Map<String, String> map,
			PageInfo<UserInfo> pageInfo) {
		// TODO Auto-generated method stub
		if (pageInfo != null) {
			log.info("设置分页");
			pageInfo.setTotalRecords(userInfoDao.getTotalRows(map));
		}
		ArrayList<UserInfo> list = userInfoDao.queryUsers(new RowBounds(
				pageInfo.getFromRecord(), pageInfo.getPageSize()), map);
		log.info("获取记录，共" + list.size() + "条");
		return list;
	}

	/**
	 * @Title: checkUserName
	 * @Description: TODO
	 * @param userName
	 * @return
	 */
	@Override
	public UserInfo checkUserName(String userName) {
		// TODO Auto-generated method stub
		return userInfoDao.getUserInfoByUserName(userName);
	}

	/**
	 * @Title: queryForJson
	 * @Description: TODO
	 * @return
	 */
	@Override
	public List<UserInfo> queryForJson() {
		// TODO Auto-generated method stub
		log.info("获取记录");
		return userInfoDao.queryUsers();
	}

	/**
	 * @Title: getUser
	 * @Description: TODO
	 * @param userId
	 * @return
	 */
	@Override
	public UserInfo getUser(String userId) {
		// TODO Auto-generated method stub
		return userInfoDao.getUser(userId);
	}

	/**
	 * @Title: saveUser
	 * @Description: TODO
	 * @param userInfo
	 */
	@Override
	public void saveUser(UserInfo userInfo) {
		// TODO Auto-generated method stub
		userInfoDao.saveUser(userInfo);
	}

	/**
	 * @Title: updateUser
	 * @Description: TODO
	 * @param userInfo
	 */
	@Override
	public void updateUser(UserInfo userInfo) {
		// TODO Auto-generated method stub
		userInfoDao.updateUser(userInfo);
	}

	/**
	 * @Title: deleteUser
	 * @Description: TODO
	 * @param idItem
	 */
	@Override
	public void deleteUser(String[] idItem) {
		// TODO Auto-generated method stub
		List<DeptInfo> depts = null;
		for (String userId : idItem) {
			log.info("删除用户前，删除用户所有的角色");
			this.deleteUserRoleByUserId(userId);
			log.info("检测是否是部门经理，如果是，这设置所在部门部门经理为空");
			depts = deptDao.getDeptByDeptManager(userId);
			if (depts != null && depts.size() > 0) {
				for (DeptInfo dept : depts) {
					dept.setDeptManager("");
					deptDao.updateDept(dept);
				}
			}
		}
		userInfoDao.deleteUser(idItem);
	}

	/**
	 * @Title: getAllUsers
	 * @Description: TODO
	 * @return
	 */
	@Override
	public List<UserInfo> getAllUsers() {
		// TODO Auto-generated method stub
		return userInfoDao.getAllUsers();
	}

	/**
	 * @Title: downloadFile
	 * @Description: TODO
	 * @param userId
	 * @return
	 */
	@Override
	public UserInfo downloadFile(String userId) {
		// TODO Auto-generated method stub
		return userInfoDao.downloadFile(userId);
	}

	/**
	 * @Title: deleteFile
	 * @Description: TODO
	 * @param userId
	 * @return
	 */
	@Override
	public int deleteFile(String userId) {
		// TODO Auto-generated method stub
		return userInfoDao.deleteFile(userId);
	}

	/**
	 * @Title: saveUserRole
	 * @Description: TODO
	 * @param userId_role
	 * @param roleIds
	 * @return
	 */
	@Override
	public int saveUserRole(String userId_role, String roleIds) {
		// TODO Auto-generated method stub
		try {
			log.info("分配权限");
			if (StringUtil.isNotEmpty(userId_role)
					&& StringUtil.isNotEmpty(roleIds)) {
				userRoleDao.deleteUserRoleByUserId(userId_role);
				List<UserRole> list = new ArrayList<UserRole>();
				String[] roleId = roleIds.split(",");
				for (String temp : roleId) {
					UserRole userRole = new UserRole();
					userRole.setUserId(userId_role);
					userRole.setRoleId(temp);
					list.add(userRole);
				}
				userRoleDao.saveUserRole(list);
			} else if (StringUtil.isNotEmpty(userId_role)
					&& StringUtil.isEmpty(roleIds)) {
				userRoleDao.deleteUserRoleByUserId(userId_role);
			}
			return ParameterUtil.SUCCESS;
		} catch (Exception e) {
			log.info("权限分配失败");
			log.info(e.getMessage());
			return ParameterUtil.FAILURE;
		}
	}

	/**
	 * @Title: deleteUserRoleByUserId
	 * @Description: TODO
	 * @param userId
	 */
	@Override
	public void deleteUserRoleByUserId(String userId) {
		// TODO Auto-generated method stub
		userRoleDao.deleteUserRoleByUserId(userId);
	}

	/**
	 * @Title: queryUsersInManyName
	 * @Description: TODO
	 * @param map
	 * @param pageInfo
	 * @return
	 */
	@Override
	public ArrayList<UserInfo> queryUsersInManyName(
			Map<String, ArrayList<String>> map) {
		return userInfoDao.queryUsersInManyName(map);
	}
}
