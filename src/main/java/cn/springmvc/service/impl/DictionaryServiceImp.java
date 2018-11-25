package cn.springmvc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.springmvc.dao.DictionaryDao;
import cn.springmvc.model.Dictionary;
import cn.springmvc.service.DictionaryService;

@Service
public class DictionaryServiceImp implements DictionaryService {

	@Autowired
	private DictionaryDao dictionaryDao;

	@Override
	public List<Dictionary> queryDictionary(Dictionary dictionary) {
		return dictionaryDao.queryDictionary(dictionary);
	}

	@Override
	public Dictionary getDictionary(String dictId) {
		return dictionaryDao.getDictionary(dictId);
	}

	@Override
	public int insertDictionary(Dictionary dictionary) {
		return dictionaryDao.insertDictionary(dictionary);
	}

	@Override
	public int updateDictionary(Dictionary dictionary) {
		return dictionaryDao.updateDictionary(dictionary);
	}

	@Override
	public Integer getDictByCode(String dictCode) {
		return dictionaryDao.getDictByCode(dictCode);
	}

}
