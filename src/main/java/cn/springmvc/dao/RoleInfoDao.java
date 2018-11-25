package cn.springmvc.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import cn.springmvc.model.RoleInfo;

@Repository
public interface RoleInfoDao {

	List<RoleInfo> queryRoles(/* RowBounds rowBounds, */RoleInfo role);

	int getTotalRows(RoleInfo role);

	int saveRole(RoleInfo role);

	RoleInfo getRoleByRoleId(String roleId);

	int updateRole(RoleInfo role);

	int deleteRole(String roleId);

	List<RoleInfo> getAllRoles();

	List<RoleInfo> getRolesByUserId(String userId);

}