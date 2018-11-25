
package cn.springmvc.service;

import java.util.List;

import cn.springmvc.model.Dictionary;

public interface DictionaryService {

	public List<Dictionary> queryDictionary(Dictionary dictionary);

	public Dictionary getDictionary(String dictId);

	public int insertDictionary(Dictionary dictionary);

	public int updateDictionary(Dictionary dictionary);
	
	public Integer getDictByCode(String dictCode);

}
