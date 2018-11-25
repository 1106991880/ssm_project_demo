/**
 * @author:稀饭
 * @time:下午9:11:48
 * @filename:DictionaryController.java
 */
package cn.springmvc.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.springmvc.model.Dictionary;
import cn.springmvc.model.DictionaryDetail;
import cn.springmvc.service.DictionaryDetailService;
import cn.springmvc.service.DictionaryService;
import cn.springmvc.util.StringUtil;

@Controller
@RequestMapping(value = "/dictionary")
public class DictionaryController extends BaseController {
	private Logger log = Logger.getLogger(this.getClass());
	@Autowired
	private DictionaryService dictionaryService;

	@Autowired
	private DictionaryDetailService dictDetailService;

	@RequestMapping(value = "/initDictionary", method = RequestMethod.GET)
	public String loadDictionary() {
		return "/dictionary/dictionary";
	}

	@RequestMapping(value = "/queryDictionary", method = RequestMethod.POST)
	@ResponseBody
	public List<Dictionary> queryDictionary(Dictionary dictionary) {
		List<Dictionary> dictList = dictionaryService
				.queryDictionary(dictionary);
		for (int i = 0; i < dictList.size(); i++) {
			Dictionary memorandum2 = dictList.get(i);
			log.info("============" + memorandum2.getDictName());
		}
		return dictList;
	}

	/*
	 * @RequestMapping(value = "/getDict", method = RequestMethod.GET)
	 * 
	 * @ResponseBody public JSONMsg getDict(String dictId) { Dictionary
	 * dictionary = dictionaryService.getDictionary(dictId); return
	 * JSONMsg.SUCCESS(dictionary); }
	 */
	@RequestMapping(value = "/initDictionaryDetail", method = RequestMethod.GET)
	public String initDictionaryDetail(Model model, String dictId,
			String dictCode) {
		model.addAttribute("dictId", dictId);
		model.addAttribute("dictCode", dictCode);
		return "/dictionary/dictionaryDetail";
	}

	@RequestMapping(value = "/getDictDetailByDictId", method = RequestMethod.POST)
	@ResponseBody
	public List<DictionaryDetail> getDictDetailByDictId(String dictId) {
		List<DictionaryDetail> dictDetailList = dictDetailService
				.getDictDetailByDictId(dictId);

		return dictDetailList;
	}

	/**
	 * @Title: updateDictStatus
	 * @Description: TODO
	 * @param @param dictionary
	 * @param @return
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = "/updateDictStatus", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateDictStatus(Dictionary dictionary) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			dictionaryService.updateDictionary(dictionary);
			map.put("status", 0);
		} catch (Exception e) {
			log.error(e.getMessage());
			map.put("status", 1);
		}
		return map;
	}

	/**
	 * @Title: saveOrUpdateDictDetail
	 * @Description: TODO
	 * @param @param dictionaryDetail
	 * @param @param dictCode
	 * @param @return
	 * @return JSONMsg
	 */
	@RequestMapping(value = "/saveOrUpdateDictDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveOrUpdateDictDetail(
			DictionaryDetail dictionaryDetail, String dictCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (StringUtil.isNotEmpty(dictionaryDetail.getDictDetailId())) {
				dictDetailService.updateDictDetail(dictionaryDetail);
			} else {
				String dictDetailCode = dictDetailService.getNewDictDetailCode(
						dictionaryDetail.getDictId(), dictCode);
				dictionaryDetail.setDictDetailCode(dictDetailCode);
				dictionaryDetail.setCreateTime(new Date());
				dictionaryDetail.setCreator(getUserInfo().getUserId());
				dictDetailService.insertDictDetail(dictionaryDetail);
			}
			map.put("status", 0);
		} catch (Exception e) {
			log.error(e.getMessage());
			map.put("status", 1);
		}
		return map;
	}

	/**
	 * @Title: getDictByCode
	 * @Description: TODO
	 * @param @param dictCode
	 * @param @return
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = "/getDictByCode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getDictByCode(String dictCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		Integer count = dictionaryService.getDictByCode(dictCode);
		map.put("data", count);
		return map;
	}

	@RequestMapping(value = "/saveOrUpdateDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveOrUpdateDict(Dictionary dictionary) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (StringUtil.isNotEmpty(dictionary.getDictId())) {
				dictionaryService.updateDictionary(dictionary);
			} else {
				dictionary.setCreateTime(new Date());
				dictionary.setCreator(getUserInfo().getUserId());
				dictionaryService.insertDictionary(dictionary);
			}
			map.put("status", 0);
		} catch (Exception e) {
			log.error(e.getMessage());
			map.put("status", 1);
		}
		return map;
	}
}