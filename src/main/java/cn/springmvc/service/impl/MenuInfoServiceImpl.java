/**
 * @author:稀饭
 * @time:下午4:17:06
 * @filename:MenuInfoServiceImpl.java
 */
package cn.springmvc.service.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.springmvc.dao.MenuInfoDao;
import cn.springmvc.dao.RoleMenuDao;
import cn.springmvc.model.MenuInfo;
import cn.springmvc.service.MenuInfoService;
import cn.springmvc.utildao.PageInfo;

@Service
public class MenuInfoServiceImpl implements MenuInfoService {

	Logger log = Logger.getLogger(this.getClass());
	@Autowired
	private RoleMenuDao roleMenuDao;
	@Autowired
	private MenuInfoDao menuInfoDao;

	/**
	 * @Title: getUserMenus
	 * @Description: TODO
	 * @param userId
	 * @return
	 */
	@Override
	public List<MenuInfo> getUserMenus(String menuType) {
		// TODO Auto-generated method stub
		List<MenuInfo> menuInfos = menuInfoDao.getMenusByMenuType(menuType);
		return menuInfos;
	}

	/**
	 * @Title: queryAllMenus
	 * @Description: TODO
	 * @param menuInfo
	 * @return
	 */
	@Override
	public List<MenuInfo> queryAllMenus( PageInfo<MenuInfo> pageInfo, 
	MenuInfo menuInfo) {
		// TODO Auto-generated method stub
		log.info("菜单查询");
		if (null != pageInfo)
		{	//获取当前数据库中多少条数据
			pageInfo.setTotalRecords(menuInfoDao.getTotalRecords(menuInfo)); 
		}
		//封裝成RowBounds对象，此刻RowBounds携带分页信息
		RowBounds rb = new RowBounds(pageInfo.getFromRecord(),
		pageInfo.getPageSize());
		return menuInfoDao.queryAllMenus(rb, menuInfo);
	}

	/**
	 * @Title: querySystemMenus
	 * @Description: TODO
	 * @param pageInfo
	 * @param menuInfo
	 * @return
	 */
	@Override
	public List<MenuInfo> querySystemMenus(/* PageInfo<MenuInfo> pageInfo, */
	MenuInfo menuInfo) {
		// TODO Auto-generated method stub
		log.info("菜单查询");
		/*
		 * if (null != pageInfo) {
		 * pageInfo.setTotalRecords(menuInfoDao.getSystemRecords(menu)); }
		 * RowBounds rb = new RowBounds(pageInfo.getFromRecord(),
		 * pageInfo.getPageSize());
		 */
		return menuInfoDao.querySystemMenus(/* rb, */menuInfo);
	}

	/**
	 * @Title: getMenu
	 * @Description: TODO
	 * @param menuId
	 * @return
	 */
	@Override
	public MenuInfo getMenu(String menuId) {
		// TODO Auto-generated method stub
		log.info("获取菜单信息");
		return menuInfoDao.getMenu(menuId);
	}

	/**
	 * @Title: getAllMenus
	 * @Description: TODO
	 * @return
	 */
	@Override
	public List<MenuInfo> getAllMenus() {
		// TODO Auto-generated method stub
		return menuInfoDao.getMenusByMenuType(null);
	}

	/**
	 * @Title: getAllSystemMenus
	 * @Description: TODO
	 * @return
	 */
	@Override
	public List<MenuInfo> getAllSystemMenus() {
		// TODO Auto-generated method stub
		return menuInfoDao.getMenusByMenuType("system");
	}

	/**
	 * @Title: getAllWebMenus
	 * @Description: TODO
	 * @return
	 */
	@Override
	public List<MenuInfo> getAllWebMenus() {
		// TODO Auto-generated method stub
		return menuInfoDao.getMenusByMenuType("web");
	}

	/**
	 * @Title: saveMenu
	 * @Description: TODO
	 * @param menuInfo
	 * @return
	 */
	@Override
	public int saveMenu(MenuInfo menuInfo) {
		// TODO Auto-generated method stub
		log.info("添加菜单信息");
		return menuInfoDao.saveMenu(menuInfo);
	}

	/**
	 * @Title: updateMenu
	 * @Description: TODO
	 * @param menuInfo
	 * @return
	 */
	@Override
	public int updateMenu(MenuInfo menuInfo) {
		// TODO Auto-generated method stub
		log.info("更新菜单信息");
		return menuInfoDao.updateMenu(menuInfo);
	}

	/**
	 * @Title: deleteMenu
	 * @Description: TODO
	 * @param menuId
	 * @return
	 */
	@Override
	public int deleteMenu(String menuId) {
		// TODO Auto-generated method stub
		log.info("删除菜单信息");
		return menuInfoDao.deleteMenu(menuId);
	}

	/**
	 * @Title: getMaxMenuOrder
	 * @Description: TODO
	 * @return
	 */
	@Override
	public int getMaxMenuOrder() {
		// TODO Auto-generated method stub
		return menuInfoDao.getMaxMenuOrder();
	}

	/**
	 * @Title: deleteRoleMenuByMenuId
	 * @Description: TODO
	 * @param menuId
	 * @return
	 */
	@Override
	public int deleteRoleMenuByMenuId(String menuId) {
		// TODO Auto-generated method stub
		return roleMenuDao.deleteRoleMenuByMenuId(menuId);
	}

	/**
	 * @Title: getMenuTypes
	 * @Description: TODO
	 * @return
	 */
	@Override
	public List<String> getMenuTypes() {
		// TODO Auto-generated method stub
		return menuInfoDao.getMenuTypes();
	}

}
