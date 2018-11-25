package cn.springmvc.model;

import java.io.Serializable;
import java.util.Date;

public class MenuInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String menuId;
	private String menuName;
	private String menuUri;
	private int menuOrder;
	private String menuDesc;
	private String menuType;
	private String menuIcon;
	private String creator;
	private String createName;
	private Date createTime;

	/**
	 * 
	 */
	public MenuInfo() {
		super();
	}

	/**
	 * 
	 * @param menuName
	 * @param menuUri
	 * @param menuOrder
	 * @param menuDesc
	 * @param userId
	 */
	public MenuInfo(String menuName, String menuUri, int menuOrder,
			String menuDesc) {
		super();
		this.menuName = menuName;
		this.menuUri = menuUri;
		this.menuOrder = menuOrder;
		this.menuDesc = menuDesc;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuUri() {
		return menuUri;
	}

	public void setMenuUri(String menuUri) {
		this.menuUri = menuUri;
	}

	public int getMenuOrder() {
		return menuOrder;
	}

	public void setMenuOrder(int menuOrder) {
		this.menuOrder = menuOrder;
	}

	public String getMenuDesc() {
		return menuDesc;
	}

	public void setMenuDesc(String menuDesc) {
		this.menuDesc = menuDesc;
	}

	public String getMenuType() {
		return menuType;
	}

	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}

	public String getMenuIcon() {
		return menuIcon;
	}

	public void setMenuIcon(String menuIcon) {
		this.menuIcon = menuIcon;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}