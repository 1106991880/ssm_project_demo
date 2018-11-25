package cn.springmvc.service;

import java.util.List;

import cn.springmvc.model.DictionaryDetail;

public interface DictionaryDetailService {

	public List<DictionaryDetail> loadDictCombobox(String dictCode);

	public List<DictionaryDetail> getDictDetailByDictId(String dictId);

	public int insertDictDetail(DictionaryDetail dictionaryDetail);

	public int updateDictDetail(DictionaryDetail dictionaryDetail);

	public DictionaryDetail getDictDetailById(String dictDetailId);

	public String getNewDictDetailCode(String dictId, String dictCode);

}
