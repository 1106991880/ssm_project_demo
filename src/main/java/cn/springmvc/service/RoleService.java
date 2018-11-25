/**
 * @author:稀饭
 * @time:下午4:15:24
 * @filename:RoleService.java
 */
package cn.springmvc.service;

import java.util.List;

import cn.springmvc.model.RoleInfo;
import cn.springmvc.utildao.PageInfo;

public interface RoleService {

	public List<RoleInfo> queryRoles(
			/* PageInfo<RoleInfo> pageInfo, */RoleInfo role);

	public int saveRole(RoleInfo role, String menuIds);

	public RoleInfo getRoleByRoleId(String roleId);

	public int updateRole(RoleInfo role, String menuIds);

	public int deleteRole(String roleId);

	public List<RoleInfo> getAllRoles();

	public List<RoleInfo> getRolesByUserId(String userId);

}
