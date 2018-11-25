/**
 * @author:稀饭
 * @time:下午11:46:16
 * @filename:DeptInfoServiceImpl.java
 */
package cn.springmvc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.springmvc.dao.DeptInfoDao;
import cn.springmvc.dao.UserInfoDao;
import cn.springmvc.model.DeptInfo;
import cn.springmvc.model.UserInfo;
import cn.springmvc.service.DeptInfoService;
import cn.springmvc.util.StringUtil;

@Service
public class DeptInfoServiceImpl implements DeptInfoService {

	@Autowired
	private DeptInfoDao deptDao;
	@Autowired
	private UserInfoDao userInfoDao;

	/**
	 * @Title: queryDeptBySuperId
	 * @Description: TODO
	 * @param superId
	 * @return
	 */
	@Override
	public List<DeptInfo> queryDeptBySuperId(String superId) {
		// TODO Auto-generated method stub
		return deptDao.queryDeptBySuperId(superId);
	}

	/**
	 * @Title: getDeptByDeptId
	 * @Description: TODO
	 * @param deptId
	 * @return
	 */
	@Override
	public DeptInfo getDeptByDeptId(String deptId) {
		// TODO Auto-generated method stub
		return deptDao.getDeptByDeptId(deptId);
	}

	/**
	 * @Title: insertDept
	 * @Description: TODO
	 * @param dept
	 * @return
	 */
	@Override
	public int insertDept(DeptInfo dept) {
		// 插入节点前，修改父级节点为非叶子节点
		DeptInfo father = deptDao.getDeptByDeptId(dept.getSuperId());
		if (father.getIfLeaf().equals("1")) {
			father.setIfLeaf("0");
			deptDao.updateDept(father);
		}
		// 如果设置为部门经理，同时修改用户的所在部门
		if (StringUtil.isNotEmpty(dept.getDeptManager())) {
			UserInfo userInfo = userInfoDao.getUser(dept.getDeptManager());
			userInfo.setDeptId(dept.getDeptId());
			userInfoDao.updateUser(userInfo);
		}
		return deptDao.insertDept(dept);
	}

	/**
	 * @Title: updateDept
	 * @Description: TODO
	 * @param dept
	 * @return
	 */
	@Override
	public int updateDept(DeptInfo dept) {
		// TODO Auto-generated method stub
		if (StringUtil.isNotEmpty(dept.getDeptManager())) {
			UserInfo userInfo = userInfoDao.getUser(dept.getDeptManager());
			userInfo.setDeptId(dept.getDeptId());
			userInfoDao.updateUser(userInfo);
		}
		return deptDao.updateDept(dept);
	}

	/**
	 * @Title: deleteDept
	 * @Description: TODO
	 * @param deptId
	 * @return
	 */
	@Override
	public int deleteDept(String deptId) {
		// TODO Auto-generated method stub
		int result = 0;
		// 检测该节点是否有子节点
		List<DeptInfo> list = this.queryDeptBySuperId(deptId);
		if (list != null && list.size() > 0) {
			result = 2;
		} else {
			DeptInfo dept = deptDao.getDeptByDeptId(deptId);
			DeptInfo father = deptDao.getDeptByDeptId(dept.getSuperId());
			result = deptDao.deleteDept(deptId);
			// 删除后，检测父级节点是否还有子节点
			List<DeptInfo> child = this.queryDeptBySuperId(father.getDeptId());
			if (child == null || child.size() == 0) {
				// 如果没有子节点，则设置为叶子节点
				father.setIfLeaf("1");
				deptDao.updateDept(father);
			}
			// 同时删除用户的所在部门
			userInfoDao.deleteDeptId(deptId);
		}
		return result;
	}

	/**
	 * @Title: getMaxPrimaryKey
	 * @Description: TODO
	 * @return
	 */
	@Override
	public String getMaxPrimaryKey() {
		// TODO Auto-generated method stub
		String MaxId = deptDao.getMaxPrimaryKey();
		int num = Integer.parseInt(MaxId.replaceFirst("d", ""));
		int newNum = num + 1;
		StringBuffer deptId = new StringBuffer("d");
		deptId.append(newNum);
		return deptId.toString();
	}

	/**
	 * @Title: getMaxDeptNo
	 * @Description: TODO
	 * @param superId
	 * @return
	 */
	@Override
	public String getMaxDeptNo(String superId) {
		// TODO Auto-generated method stub
		DeptInfo superDept = this.getDeptByDeptId(superId);
		StringBuffer deptNo = new StringBuffer(superDept.getDeptNo());
		// 检测上级节点是否有子节点
		List<DeptInfo> list = this.queryDeptBySuperId(superId);
		if (list != null && list.size() > 0) {
			String MaxDeptNo = deptDao.getMaxDeptNo(superId);
			String num = MaxDeptNo.substring(superDept.getDeptNo().length());
			int newNum = Integer.parseInt(num) + 1;
			deptNo.append(newNum);
		} else {
			deptNo.append("01");
			// 子节点最多99个
		}
		return deptNo.toString();
	}

	/**
	 * @Title: getDeptByDeptManager
	 * @Description: TODO
	 * @param userId
	 * @return
	 */
	@Override
	public List<DeptInfo> getDeptByDeptManager(String userId) {
		// TODO Auto-generated method stub
		return deptDao.getDeptByDeptManager(userId);
	}

}
