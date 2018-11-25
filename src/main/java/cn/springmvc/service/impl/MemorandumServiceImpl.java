/**
 * @author:稀饭
 * @time:下午8:37:34
 * @filename:MemorandumServiceImpl.java
 */
package cn.springmvc.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.springmvc.dao.MemorandumDao;
import cn.springmvc.model.Memorandum;
import cn.springmvc.service.MemorandumService;

@Service
public class MemorandumServiceImpl implements MemorandumService {
	Logger log = Logger.getLogger(this.getClass());

	@Autowired
	private MemorandumDao memorandumDao;

	/**
	 * @Title: getAllMemorandum
	 * @Description: TODO
	 * @param userId
	 * @return
	 */
	@Override
	public List<Memorandum> getAllMemorandum(String userId) {
		// TODO Auto-generated method stub
		log.info("查询用户备忘录");
		return memorandumDao.getAllMemorandum(userId);
	}

	/**
	 * @Title: saveMemorandum
	 * @Description: TODO
	 * @param memorandum
	 */
	@Override
	public void saveMemorandum(Memorandum memorandum) {
		// TODO Auto-generated method stub
		log.info("保存用户备忘录");
		memorandumDao.saveMemorandum(memorandum);
	}

	/**
	 * @Title: updateMemorandum
	 * @Description: TODO
	 * @param memorandum
	 */
	@Override
	public void updateMemorandum(Memorandum memorandum) {
		// TODO Auto-generated method stub
		log.info("修改用户备忘录");
		memorandumDao.updateMemorandum(memorandum);
	}

	/**
	 * @Title: queryMemorandum
	 * @Description: TODO
	 * @param map
	 * @return
	 */
	@Override
	public List<Memorandum> queryMemorandum(Map<String, Object> map) {
		// TODO Auto-generated method stub
		log.info("查询用户备忘录");
		return memorandumDao.queryMemorandum(map);
	}

	/**
	 * @Title: getMemorandumById
	 * @Description: TODO
	 * @param memorandumId
	 * @return
	 */
	@Override
	public Memorandum getMemorandumById(String memorandumId) {
		// TODO Auto-generated method stub
		log.info("根据ID查询用户备忘录");
		return memorandumDao.getMemorandumById(memorandumId);
	}

	/**
	 * @Title: delMemorandumById
	 * @Description: TODO
	 * @param memorandumId
	 * @return
	 */
	@Override
	public int delMemorandumById(String memorandumId) {
		// TODO Auto-generated method stub
		log.info("根据ID删除用户备忘录");
		return memorandumDao.delMemorandumById(memorandumId);
	}

}
