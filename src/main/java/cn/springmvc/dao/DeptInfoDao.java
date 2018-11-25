/**
 * @author:稀饭
 * @time:下午11:47:40
 * @filename:DeptInfoDao.java
 */
package cn.springmvc.dao;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import cn.springmvc.model.DeptInfo;

@Component
public interface DeptInfoDao {
	public List<DeptInfo> queryDeptBySuperId(String superId);

	public DeptInfo getDeptByDeptId(String deptId);

	public int insertDept(DeptInfo dept);

	public int updateDept(DeptInfo dept);

	public int deleteDept(String deptId);

	public String getMaxPrimaryKey();

	public String getMaxDeptNo(String superId);

	public List<DeptInfo> getDeptByDeptManager(String userId);

}
