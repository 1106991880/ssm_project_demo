package cn.springmvc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.springmvc.dao.DictionaryDetailDao;
import cn.springmvc.model.DictionaryDetail;
import cn.springmvc.service.DictionaryDetailService;
import cn.springmvc.util.StringUtil;

@Service(value = "dictDetailService")
public class DictionaryDetailServiceImp implements DictionaryDetailService {

	@Autowired
	private DictionaryDetailDao dictionaryDetailDao;

	@Override
	public List<DictionaryDetail> loadDictCombobox(String dictCode) {
		return dictionaryDetailDao.loadDictCombobox(dictCode);
	}

	@Override
	public List<DictionaryDetail> getDictDetailByDictId(String dictId) {
		return dictionaryDetailDao.getDictDetailByDictId(dictId);
	}

	@Override
	public int insertDictDetail(DictionaryDetail dictionaryDetail) {
		return dictionaryDetailDao.insertDictDetail(dictionaryDetail);
	}

	@Override
	public int updateDictDetail(DictionaryDetail dictionaryDetail) {
		return dictionaryDetailDao.updateDictDetail(dictionaryDetail);
	}

	@Override
	public DictionaryDetail getDictDetailById(String dictDetailId) {
		return dictionaryDetailDao.getDictDetailById(dictDetailId);
	}

	@Override
	public String getNewDictDetailCode(String dictId, String dictCode) {
		String dictDetailCode = dictionaryDetailDao.getDictDetailCodeByDictId(dictId);
		if (StringUtil.isNotEmpty(dictDetailCode)) {
			String index = dictDetailCode.replace(dictCode, "");
			Integer newIndex = Integer.valueOf(index) + 1;
			return dictCode + newIndex;
		} else {
			return dictCode + 0;
		}

	}

}
