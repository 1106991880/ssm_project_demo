/**
 * @author:稀饭
 * @time:下午11:46:33
 * @filename:MemorandumService.java
 */
package cn.springmvc.service;

import java.util.List;
import java.util.Map;

import cn.springmvc.model.Memorandum;

public interface MemorandumService {

	public List<Memorandum> getAllMemorandum(String userId);

	public void saveMemorandum(Memorandum memorandum);

	public void updateMemorandum(Memorandum memorandum);

	public List<Memorandum> queryMemorandum(Map<String, Object> map);

	public Memorandum getMemorandumById(String memorandumId);

	public int delMemorandumById(String memorandumId);

}
