/**
 * @author:稀饭
 * @time:下午11:46:33
 * @filename:ManageMoneyService.java
 */
package cn.springmvc.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import cn.springmvc.model.ManageMoney;
import cn.springmvc.utildao.PageInfo;

public interface ManageMoneyService {

	public List<ManageMoney> queryManageMoney(
			PageInfo<ManageMoney> pageInfo,Map<String, String> map);

	public List<ManageMoney> queryAllManageMoney(Map<String, Object> map);

	public BigDecimal getSum(Map<String, Object> map);

	public ManageMoney getManageMoneyById(String manageId);

	public void saveManageMoney(ManageMoney manageMoney);

	public void updateManageMoney(ManageMoney manageMoney);

	public void deleteManageMoney(String[] ids);

}
