package cn.springmvc.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.springmvc.model.DictionaryDetail;

@Repository
public interface DictionaryDetailDao {

	public List<DictionaryDetail> loadDictCombobox(String dictCode);

	public List<DictionaryDetail> getDictDetailByDictId(String dictId);

	public int insertDictDetail(DictionaryDetail dictionaryDetail);

	public int updateDictDetail(DictionaryDetail dictionaryDetail);

	public DictionaryDetail getDictDetailById(String dictDetailId);

	public String getDictDetailCodeByDictId(String dictId);

}
