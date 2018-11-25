/**
 * @author:稀饭
 * @time:下午4:15:24
 * @filename:MenuInfoService.java
 */
package cn.springmvc.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cn.springmvc.model.MenuInfo;
import cn.springmvc.utildao.PageInfo;

@Service
public interface MenuInfoService {
	public List<MenuInfo> getUserMenus(String menuType);

	public List<MenuInfo> queryAllMenus(
			 PageInfo<MenuInfo> pageInfo, MenuInfo menuInfo);

	public List<MenuInfo> querySystemMenus(
			/* PageInfo<MenuInfo> pageInfo, */MenuInfo menuInfo);

	public MenuInfo getMenu(String menuId);

	public List<MenuInfo> getAllMenus();

	public List<MenuInfo> getAllSystemMenus();

	public List<MenuInfo> getAllWebMenus();

	public int saveMenu(MenuInfo menuInfo);

	public int updateMenu(MenuInfo menuInfo);

	public int deleteMenu(String menuId);

	public int getMaxMenuOrder();

	public int deleteRoleMenuByMenuId(String menuId);

	public List<String> getMenuTypes();
}
