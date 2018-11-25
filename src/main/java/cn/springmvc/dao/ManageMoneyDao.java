package cn.springmvc.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import cn.springmvc.model.ManageMoney;

@Repository
public interface ManageMoneyDao {

	public int getTotalRows(Map<String, String> map);

	public List<ManageMoney> queryManageMoney(
			 RowBounds rowBounds,Map<String, String> map);

	public List<ManageMoney> queryAllManageMoney(Map<String, Object> map);

	public BigDecimal getSum(Map<String, Object> map);

	public ManageMoney getManageMoneyById(String manageId);

	public void saveManageMoney(ManageMoney manageMoney);

	public void updateManageMoney(ManageMoney manageMoney);

	public void deleteManageMoney(String[] ids);
}
