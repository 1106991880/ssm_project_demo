package cn.springmvc.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class RoleInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7152233360598908858L;

	private String roleId;

	private String roleName;

	private String roleDesc;

	private Date createTime;

	private String creator;

	private List<RoleMenu> menus;

	private int userHave = 0;// ���ڱ�ʶ�û��Ƿ�ӵ�д˽�ɫȨ��

	public RoleInfo() {
		super();
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public List<RoleMenu> getMenus() {
		return menus;
	}

	public void setMenus(List<RoleMenu> menus) {
		this.menus = menus;
	}

	public int getUserHave() {
		return userHave;
	}

	public void setUserHave(int userHave) {
		this.userHave = userHave;
	}

}