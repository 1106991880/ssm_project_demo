/**
 * @author:稀饭
 * @time:下午8:39:52
 * @filename:RoleServiceImpl.java
 */
package cn.springmvc.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.springmvc.dao.RoleInfoDao;
import cn.springmvc.dao.RoleMenuDao;
import cn.springmvc.dao.UserRoleDao;
import cn.springmvc.model.RoleInfo;
import cn.springmvc.model.RoleMenu;
import cn.springmvc.service.RoleService;
import cn.springmvc.util.ParameterUtil;
import cn.springmvc.util.StringUtil;

@Service
public class RoleServiceImpl implements RoleService {
	Logger log = Logger.getLogger(this.getClass());

	@Autowired
	private RoleInfoDao roleDao;
	@Autowired
	private UserRoleDao userRoleDao;
	@Autowired
	private RoleMenuDao roleMenuDao;

	/**
	 * @Title: queryRoles
	 * @Description: TODO
	 * @param pageInfo
	 * @param role
	 * @return
	 */
	@Override
	public List<RoleInfo> queryRoles(
	/* PageInfo<RoleInfo> pageInfo, */RoleInfo role) {
		// TODO Auto-generated method stub
		/*
		 * if (pageInfo != null) {
		 * pageInfo.setTotalRecords(roleDao.getTotalRows(role)); } RowBounds
		 * rowBounds = new RowBounds(pageInfo.getFromRecord(),
		 * pageInfo.getPageSize());
		 */
		return roleDao.queryRoles(/* rowBounds, */role);
	}

	/**
	 * @Title: saveRole
	 * @Description: TODO
	 * @param role
	 * @param menuIds
	 * @return
	 */
	@Override
	public int saveRole(RoleInfo role, String menuIds) {
		// TODO Auto-generated method stub
		try {
			log.info("保存角色");
			roleDao.saveRole(role);
			if (StringUtil.isNotEmpty(role.getRoleId())
					&& StringUtil.isNotEmpty(menuIds)) {
				log.info("保存角色菜单关系");
				List<RoleMenu> rmList = new ArrayList<RoleMenu>();
				if (StringUtil.isNotEmpty(menuIds)) {
					String[] menuId = menuIds.split(",");
					for (String temp : menuId) {
						RoleMenu roleMenu = new RoleMenu();
						roleMenu.setRoleId(role.getRoleId());
						roleMenu.setMenuId(temp);
						rmList.add(roleMenu);
					}
				}
				roleMenuDao.saveRoleMenu(rmList);
			}
			return ParameterUtil.SUCCESS;
		} catch (Exception e) {
			log.info("角色保存失败");
			log.info(e.getMessage());
			return ParameterUtil.FAILURE;
		}
	}

	/**
	 * @Title: getRoleByRoleId
	 * @Description: TODO
	 * @param roleId
	 * @return
	 */
	@Override
	public RoleInfo getRoleByRoleId(String roleId) {
		// TODO Auto-generated method stub
		return roleDao.getRoleByRoleId(roleId);
	}

	/**
	 * @Title: updateRole
	 * @Description: TODO
	 * @param role
	 * @param menuIds
	 * @return
	 */
	@Override
	public int updateRole(RoleInfo role, String menuIds) {
		// TODO Auto-generated method stub
		try {
			log.info("更新角色");
			roleDao.updateRole(role);
			roleMenuDao.deleteRoleMenuByRoleId(role.getRoleId());
			if (StringUtil.isNotEmpty(role.getRoleId())
					&& StringUtil.isNotEmpty(menuIds)) {
				log.info("保存角色菜单关系");
				List<RoleMenu> rmList = new ArrayList<RoleMenu>();
				if (StringUtil.isNotEmpty(menuIds)) {
					String[] menuId = menuIds.split(",");
					for (String temp : menuId) {
						RoleMenu roleMenu = new RoleMenu();
						roleMenu.setRoleId(role.getRoleId());
						roleMenu.setMenuId(temp);
						rmList.add(roleMenu);
					}
				}
				roleMenuDao.saveRoleMenu(rmList);
			}
			return ParameterUtil.SUCCESS;
		} catch (Exception e) {
			log.info("角色更新失败");
			log.info(e.getMessage());
			return ParameterUtil.FAILURE;
		}
	}

	/**
	 * @Title: deleteRole
	 * @Description: TODO
	 * @param roleId
	 * @return
	 */
	@Override
	public int deleteRole(String roleId) {
		// TODO Auto-generated method stub
		try {
			log.info("删除用户角色关联关系");
			userRoleDao.deleteUserRoleByRoleId(roleId);
			log.info("删除角色菜单关联关系");
			roleMenuDao.deleteRoleMenuByRoleId(roleId);
			log.info("删除角色");
			roleDao.deleteRole(roleId);
			return ParameterUtil.SUCCESS;
		} catch (Exception e) {
			log.info("角色删除失败");
			log.info(e.getMessage());
			return ParameterUtil.FAILURE;
		}
	}

	/**
	 * @Title: getAllRoles
	 * @Description: TODO
	 * @return
	 */
	@Override
	public List<RoleInfo> getAllRoles() {
		// TODO Auto-generated method stub
		return roleDao.getAllRoles();
	}

	/**
	 * @Title: getRolesByUserId
	 * @Description: TODO
	 * @param userId
	 * @return
	 */
	@Override
	public List<RoleInfo> getRolesByUserId(String userId) {
		// TODO Auto-generated method stub
		return roleDao.getRolesByUserId(userId);
	}

}
