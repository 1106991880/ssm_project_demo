package cn.springmvc.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.springmvc.model.Dictionary;

@Repository
public interface DictionaryDao {

	public List<Dictionary> queryDictionary(Dictionary dictionary);

	public Dictionary getDictionary(String dictId);

	public int insertDictionary(Dictionary dictionary);

	public int updateDictionary(Dictionary dictionary);

	public Integer getDictByCode(String dictCode);

}
