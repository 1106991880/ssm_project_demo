/**
 * @author:稀饭
 * @time:下午8:34:04
 * @filename:ManageMoneyServiceImpl.java
 */
package cn.springmvc.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.springmvc.dao.ManageMoneyDao;
import cn.springmvc.model.ManageMoney;
import cn.springmvc.service.ManageMoneyService;
import cn.springmvc.utildao.PageInfo;

@Service
public class ManageMoneyServiceImpl implements ManageMoneyService {

	@Autowired
	private ManageMoneyDao manageMoneyDao;

	/**
	 * @Title: queryManageMoney
	 * @Description: TODO
	 * @param pageInfo
	 * @param map
	 * @return
	 */
	@Override
	public List<ManageMoney> queryManageMoney( PageInfo<ManageMoney> pageInfo,
	Map<String, String> map) {
		// TODO Auto-generated method stub
		
		 if (null != pageInfo) {
		 pageInfo.setTotalRecords(manageMoneyDao.getTotalRows(map)); } //
		 // 设置从第几条开始获取记录和每页显示条数 
		 RowBounds rowBounds = new RowBounds(pageInfo.getFromRecord(), pageInfo.getPageSize());
		 return manageMoneyDao.queryManageMoney(rowBounds, map);
	}

	/**
	 * @Title: queryAllManageMoney
	 * @Description: TODO
	 * @param map
	 * @return
	 */
	@Override
	public List<ManageMoney> queryAllManageMoney(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return manageMoneyDao.queryAllManageMoney(map);
	}

	/**
	 * @Title: getSum
	 * @Description: TODO
	 * @param map
	 * @return
	 */
	@Override
	public BigDecimal getSum(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return manageMoneyDao.getSum(map);
	}

	/**
	 * @Title: getManageMoneyById
	 * @Description: TODO
	 * @param manageId
	 * @return
	 */
	@Override
	public ManageMoney getManageMoneyById(String manageId) {
		// TODO Auto-generated method stub
		return manageMoneyDao.getManageMoneyById(manageId);
	}

	/**
	 * @Title: saveManageMoney
	 * @Description: TODO
	 * @param manageMoney
	 */
	@Override
	public void saveManageMoney(ManageMoney manageMoney) {
		// TODO Auto-generated method stub
		manageMoneyDao.saveManageMoney(manageMoney);
	}

	/**
	 * @Title: updateManageMoney
	 * @Description: TODO
	 * @param manageMoney
	 */
	@Override
	public void updateManageMoney(ManageMoney manageMoney) {
		// TODO Auto-generated method stub
		manageMoneyDao.updateManageMoney(manageMoney);
	}

	/**
	 * @Title: deleteManageMoney
	 * @Description: TODO
	 * @param ids
	 */
	@Override
	public void deleteManageMoney(String[] ids) {
		// TODO Auto-generated method stub
		manageMoneyDao.deleteManageMoney(ids);
	}

}
