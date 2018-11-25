package cn.springmvc.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.springmvc.model.UserRole;

@Repository
public interface UserRoleDao {

	public void saveUserRole(List<UserRole> list);

	public void deleteUserRoleByUserId(String userId);

	public void deleteUserRoleByRoleId(String roleId);

}