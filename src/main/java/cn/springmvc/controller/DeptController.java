/**
 * @author:稀饭
 * @time:下午11:41:36
 * @filename:DeptController.java
 */
package cn.springmvc.controller;

import java.util.ArrayList;
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

import cn.springmvc.model.DeptInfo;
import cn.springmvc.model.UserInfo;
import cn.springmvc.service.DeptInfoService;
import cn.springmvc.service.UserInfoService;
import cn.springmvc.util.StringUtil;
import cn.springmvc.utildao.TreeData;

@Controller
@RequestMapping("/dept")
public class DeptController extends BaseController {
	private Logger log = Logger.getLogger(this.getClass());
	@Autowired
	private DeptInfoService deptService;
	@Autowired
	private UserInfoService userService;

	/**
	 * @Title: turnToDeptTree
	 * @Description: TODO
	 * @param @return
	 * @return String
	 */
	@RequestMapping(value = "/turnToDeptTree")
	public String turnToDeptTree() {
		return "/dept/dept";
	}

	/**
	 * 组织机构树
	 * 
	 * @param superId
	 * @return
	 */
	@RequestMapping(value = "/getDeptTree", method = RequestMethod.POST)
	@ResponseBody
	public List<TreeData> getDeptTree(String superId) {
		if (StringUtil.isEmpty(superId)) {
			superId = "0";
		}
		log.info("查询部门" + superId + "下所有部门");
		/* 查询到所有部门信息 */
		List<DeptInfo> rootList = deptService.queryDeptBySuperId(superId);
		List<TreeData> treeData = new ArrayList<TreeData>();
		/* 将查询到的部门信息进行构造 */
		for (DeptInfo dept : rootList) {
			TreeData rootData = new TreeData();
			rootData.setId(dept.getDeptId());
			rootData.setText(dept.getDeptName());
			rootData.setIconCls("icon-organisation");
			rootData.setChecked(false);
			Map<String, String> attributes = new HashMap<String, String>();
			attributes.put("deptNo", dept.getDeptNo());
			attributes.put("ifLeaf", dept.getIfLeaf());
			rootData.setAttributes(attributes);
			List<DeptInfo> childList = deptService.queryDeptBySuperId(dept
					.getDeptId());
			if (childList.size() == 0 || childList == null) {
				rootData.setState("open");
			} else {
				rootData.setState("closed");
			}

			treeData.add(rootData);
		}
		return treeData;
	}

	/**
	 * 根据部门id获取部门信息
	 * 
	 * @param model
	 * @param deptId
	 * @return
	 */
	@RequestMapping(value = "/getDeptByDeptId", method = RequestMethod.GET)
	public String getDeptBydeptId(Model model, String deptId) {
		DeptInfo dept = null;
		if (StringUtil.isNotEmpty(deptId)) {
			dept = deptService.getDeptByDeptId(deptId);
		}
		DeptInfo superDept = new DeptInfo();
		if (StringUtil.isNotEmpty(dept.getSuperId())
				&& !dept.getSuperId().equals("0")) {
			superDept = deptService.getDeptByDeptId(dept.getSuperId());
		}
		UserInfo user = new UserInfo();
		if (StringUtil.isNotEmpty(dept.getDeptManager())) {
			user = userService.getUser(dept.getDeptManager());
		}
		if (user != null) {
			model.addAttribute("trueName", user.getTrueName());
		}
		model.addAttribute("dept", dept);
		model.addAttribute("superDeptName", superDept.getDeptName());
		return "/dept/view";
	}

	/**
	 * 跳转添加页面
	 * 
	 * @param model
	 * @param superId
	 * @return
	 */
	@RequestMapping(value = "/addDept", method = RequestMethod.GET)
	public String addDept(Model model, String superId) {
		DeptInfo dept = new DeptInfo();
		String deptId = deptService.getMaxPrimaryKey();
		dept.setDeptId(deptId);
		String deptNo = deptService.getMaxDeptNo(superId);
		dept.setDeptNo(deptNo);
		DeptInfo superDept = new DeptInfo();
		if (StringUtil.isNotEmpty(superId) && !superId.equals("0")) {
			dept.setSuperId(superId);
			superDept = deptService.getDeptByDeptId(superId);
		}
		List<UserInfo> userList = userService.getAllUsers();
		model.addAttribute("dept", dept);
		model.addAttribute("superDeptName", superDept.getDeptName());
		model.addAttribute("userList", userList);
		return "dept/add";
	}

	/**
	 * 新增部门信息
	 * 
	 * @param dept
	 * @return
	 */
	@RequestMapping(value = "/saveDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Integer> saveDept(DeptInfo dept) {
		int result = deptService.insertDept(dept);
		Map<String, Integer> jsonMap = new HashMap<String, Integer>();
		jsonMap.put("result", result);
		return jsonMap;
	}

	/**
	 * 根据部门id编辑部门信息
	 * 
	 * @param model
	 * @param deptId
	 * @return
	 */
	@RequestMapping(value = "/editDept", method = RequestMethod.GET)
	public String editDept(Model model, String deptId) {
		DeptInfo dept = null;
		if (StringUtil.isNotEmpty(deptId)) {
			dept = deptService.getDeptByDeptId(deptId);
		}
		DeptInfo superDept = new DeptInfo();
		if (StringUtil.isNotEmpty(dept.getSuperId())
				&& !dept.getSuperId().equals("0")) {
			superDept = deptService.getDeptByDeptId(dept.getSuperId());
		}
		List<UserInfo> userList = userService.getAllUsers();
		model.addAttribute("dept", dept);
		model.addAttribute("superDeptName", superDept.getDeptName());
		model.addAttribute("userList", userList);
		return "/dept/update";
	}

	/**
	 * 更新部门信息
	 * 
	 * @param dept
	 * @return
	 */
	@RequestMapping(value = "/updateDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Integer> updateDept(DeptInfo dept) {
		int result = deptService.updateDept(dept);
		Map<String, Integer> jsonMap = new HashMap<String, Integer>();
		jsonMap.put("result", result);
		return jsonMap;
	}

	/**
	 * 删除部门信息
	 * 
	 * @param deptId
	 * @return
	 */
	@RequestMapping(value = "/deleteDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Integer> deleteDept(String deptId) {
		int result = deptService.deleteDept(deptId);
		Map<String, Integer> jsonMap = new HashMap<String, Integer>();
		jsonMap.put("result", result);
		return jsonMap;
	}

	/**
	 * 解决异步提交表单，数据类型不自动转换问题 可以通过注解修饰属性，自动完成转换，详见pojo类
	 * 
	 * @param binder
	 * @param request
	 */
	/*
	 * @InitBinder public void initBinder(WebDataBinder binder, WebRequest
	 * request) { SimpleDateFormat dateFormat = new
	 * SimpleDateFormat("yyyy-MM-dd"); dateFormat.setLenient(false);
	 * CustomDateEditor customDateEditor = new CustomDateEditor(dateFormat,
	 * true); binder.registerCustomEditor(Date.class, customDateEditor); }
	 */
}
