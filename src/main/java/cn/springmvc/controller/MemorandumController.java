/**
 * @author:稀饭
 * @time:下午10:20:28
 * @filename:MemorandumController.java
 */
package cn.springmvc.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.springmvc.model.Memorandum;
import cn.springmvc.model.UserInfo;
import cn.springmvc.service.MemorandumService;
import cn.springmvc.util.StringUtil;

@Controller
@RequestMapping("/memorandum")
public class MemorandumController extends BaseController {
	@Autowired
	private MemorandumService memorandumService;
	private Logger logger = Logger.getLogger(this.getClass());

	@RequestMapping(value = "/getMemorandum", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getMemorandum(String memorandumId) {
		Memorandum memorandum = memorandumService
				.getMemorandumById(memorandumId);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memorandum", memorandum);
		return map;
	}

	/**
	 * @Title: loadMenorandum
	 * @Description: TODO
	 * @param @return
	 * @return List<Memorandum>
	 */
	@RequestMapping(value = "/loadMemorandum", method = RequestMethod.POST)
	@ResponseBody
	public List<Memorandum> loadMenorandum() {
		UserInfo userInfo = getUserInfo();
		List<Memorandum> memorandumList = memorandumService
				.getAllMemorandum(userInfo.getUserId());
		return memorandumList;
	}

	/**
	 * @Title: saveMenorandum
	 * @Description: TODO
	 * @param @param memorandum
	 * @param @return
	 * @return JSONMsg
	 */
	@RequestMapping(value = "/saveMemorandum", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveMemorandum(Memorandum memorandum) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (null != memorandum) {
			if (StringUtil.isEmpty(memorandum.getMemorandumId())) {
				memorandum.setCreateUser(getUserInfo().getUserId());
				memorandum.setCreateTime(new Date());
				memorandumService.saveMemorandum(memorandum);
			} else {
				memorandumService.updateMemorandum(memorandum);
			}
			map.put("status", 0);
		} else {
			map.put("status", 1);
		}
		return map;
	}

	/**
	 * @Title: initMemorandum
	 * @Description: TODO
	 * @param @return
	 * @return String
	 */
	@RequestMapping(value = "/initMemorandum", method = RequestMethod.GET)
	public String initMemorandum() {
		return "memorandum/memorandum";
	}

	/**
	 * @Title: queryMemorandum
	 * @Description: TODO
	 * @param @param memorandum
	 * @param @return
	 * @return List<Memorandum>
	 */
	@RequestMapping(value = "/queryMemorandum", method = RequestMethod.POST)
	@ResponseBody
	public List<Memorandum> queryMemorandum(Memorandum memorandum) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (!getUserInfo().getUserName().equals("system")) {
			map.put("userId", getUserInfo().getUserId());
		}
		if (StringUtil.isNotEmpty(memorandum.getMemorandumDate())) {
			map.put("memorandumDate", memorandum.getMemorandumDate());
		}
		if (StringUtil.isNotEmpty(memorandum.getMemorandumComplete())) {
			map.put("memorandumComplete", memorandum.getMemorandumComplete());
		}
		List<Memorandum> memorandumList = memorandumService
				.queryMemorandum(map);

		return memorandumList;
	}

	/**
	 * @Title: getTodayMemorandum
	 * @Description: TODO
	 * @param @return
	 * @return List<Memorandum>
	 */
	@RequestMapping(value = "/getTodayMemorandum", method = RequestMethod.POST)
	@ResponseBody
	public List<Memorandum> getTodayMemorandum() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", getUserInfo().getUserId());
		map.put("memorandumDate",
				new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		map.put("memorandumComplete", "memorStatus0");
		List<Memorandum> memorandumList = memorandumService
				.queryMemorandum(map);
		return memorandumList;
	}
	
	@RequestMapping(value = "/deleteMemorandum", method = RequestMethod.POST)
	@ResponseBody
	public Map deleteMemorandum(String memorandumId) {
		Map<String, Object> map = new HashMap<String, Object>();
		Integer result = memorandumService.delMemorandumById(memorandumId);
		if (result == 1) {
			map.put("ststus", 1);
		} else {
			map.put("ststus", 0);
		}
		return map;
	}
}
