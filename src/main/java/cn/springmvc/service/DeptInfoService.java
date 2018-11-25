/**
 * @author:稀饭
 * @time:下午11:46:33
 * @filename:DeptInfoService.java
 */
package cn.springmvc.service;

import java.util.List;

import cn.springmvc.model.DeptInfo;

public interface DeptInfoService {
	public List<DeptInfo> queryDeptBySuperId(String superId);

	public DeptInfo getDeptByDeptId(String deptId);

	public int insertDept(DeptInfo dept);

	public int updateDept(DeptInfo dept);

	public int deleteDept(String deptId);

	public String getMaxPrimaryKey();

	public String getMaxDeptNo(String superId);

	public List<DeptInfo> getDeptByDeptManager(String userId);

}
