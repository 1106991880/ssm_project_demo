package cn.springmvc.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.springmvc.model.RoleMenu;

@Repository
public interface RoleMenuDao {

	int saveRoleMenu(List<RoleMenu> list);

	int deleteRoleMenuByMenuId(String menuId);

	int deleteRoleMenuByRoleId(String roleId);

}