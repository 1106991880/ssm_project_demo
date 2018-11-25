package cn.springmvc.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.springmvc.model.Memorandum;

@Repository
public interface MemorandumDao {

	public List<Memorandum> getAllMemorandum(String userId);

	public int saveMemorandum(Memorandum memorandum);

	public int updateMemorandum(Memorandum memorandum);

	public List<Memorandum> queryMemorandum(Map<String, Object> map);

	public Memorandum getMemorandumById(String memorandumId);

	public int delMemorandumById(String memorandumId);
	
}
