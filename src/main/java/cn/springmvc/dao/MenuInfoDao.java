package cn.springmvc.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import cn.springmvc.model.MenuInfo;

@Repository
public interface MenuInfoDao {

	public List<MenuInfo> getUserMenus(String userId);

	public List<MenuInfo> queryAllMenus(RowBounds rb, MenuInfo menuMenuInfo);

	public int getTotalRecords(MenuInfo menuMenuInfo);

	public List<MenuInfo> querySystemMenus(
			/* RowBounds rb, */MenuInfo menuMenuInfo);

	public int getSystemRecords(MenuInfo menuMenuInfo);

	public MenuInfo getMenu(String menuId);

	public List<MenuInfo> getMenusByMenuType(@Param("menuType") String menuType);

	public int saveMenu(MenuInfo menu);

	public int updateMenu(MenuInfo menu);

	public int deleteMenu(String menuId);

	public int getMaxMenuOrder();

	public List<String> getMenuTypes();

}