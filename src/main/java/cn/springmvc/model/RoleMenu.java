package cn.springmvc.model;

import java.io.Serializable;

public class RoleMenu implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 450960236138742302L;

	private String id;

	private String roleId;

	private String menuId;

	private MenuInfo menuInfo;

	public RoleMenu() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public MenuInfo getMenu() {
		return menuInfo;
	}

	public void setMenu(MenuInfo menuInfo) {
		this.menuInfo = menuInfo;
	}

}