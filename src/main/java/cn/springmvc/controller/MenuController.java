package cn.springmvc.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.springmvc.model.MenuInfo;
import cn.springmvc.service.MenuInfoService;
import cn.springmvc.util.StringUtil;
import cn.springmvc.utildao.PageInfo;

@Controller
@RequestMapping("/menu")
public class MenuController extends BaseController {

	private Logger log = Logger.getLogger(MenuController.class);

	@Autowired
	private MenuInfoService menuService;

	/**
	 * @Title: queryMenu
	 * @Description: 查询菜单
	 * @param @param model
	 * @param @param menu
	 * @param @return
	 * @return String
	 */
	@RequestMapping(value = "/queryMenu")
	public String queryMenu(Model model, PageInfo<MenuInfo> pageInfo,
			MenuInfo menu) {
		log.info("查询菜单记录");
		List<MenuInfo> menuList = menuService.queryAllMenus(pageInfo, menu);
		model.addAttribute("menuList", menuList);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("menu", menu);
		return "/menu/query";
	}

	/**
	 * @Title: add
	 * @Description: 跳转到添加菜单页面
	 * @param @param model
	 * @param @return
	 * @return String
	 */
	@RequestMapping(value = "/addMenu", method = RequestMethod.POST)
	public String add(Model model) {
		log.info("进入添加页面");
		MenuInfo menu = new MenuInfo();
		menu.setMenuOrder(menuService.getMaxMenuOrder() + 1);
		model.addAttribute("menu", menu);
		model.addAttribute("menuTypes", super.menuTypeMap());
		return "/menu/add";
	}

	/**
	 * 保存菜单信息
	 * 
	 * @param menu
	 * @return
	 */
	@RequestMapping(value = "/saveMenu", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Integer> saveMenu(MenuInfo menu) {
		int result = menuService.saveMenu(menu);
		Map<String, Integer> jsonMap = new HashMap<String, Integer>();
		jsonMap.put("result", result);
		return jsonMap;
	}

	/**
	 * 跳转修改菜单页面
	 * 
	 * @param model
	 * @param menuId
	 * @return
	 */
	@RequestMapping(value = "/editMenu", method = RequestMethod.POST)
	public String editMenu(Model model, String menuId) {
		log.info("进入编辑页面");
		MenuInfo menu = menuService.getMenu(menuId);
		if (null != menu) {
			model.addAttribute("menu", menu);
		}
		model.addAttribute("menuTypes", super.menuTypeMap());
		return "/menu/update";
	}

	/**
	 * 更新菜单信息
	 * 
	 * @param menu
	 * @return
	 */
	@RequestMapping(value = "/updateMenu", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Integer> updateMenu(MenuInfo menu) {
		int result = menuService.updateMenu(menu);
		Map<String, Integer> jsonMap = new HashMap<String, Integer>();
		jsonMap.put("result", result);
		return jsonMap;
	}

	/**
	 * 删除菜单
	 * 
	 * @param model
	 * @param menuId
	 * @return
	 */
	@RequestMapping(value = "/deleteMenu", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Integer> deleteMenu(Model model, String menuId) {
		Map<String, Integer> jsonMap = new HashMap<String, Integer>();
		if (StringUtil.isNotEmpty(menuId)) {
			menuService.deleteRoleMenuByMenuId(menuId);// 删除菜单前，删除所有拥有此菜单的角色菜单关联
			int result = menuService.deleteMenu(menuId);
			jsonMap.put("result", result);
			log.info("删除菜单成功");
		}
		return jsonMap;
	}

	/**
	 * 获取所有菜单
	 * 
	 * @param page
	 * @param rows
	 * @param menuName
	 * @return
	 */
	@RequestMapping(value = "/getAllMenus", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> allotMenu(String page, String rows,
			String menuName) {
		log.info("查询菜单");
		// 当前页
		int currentPage = Integer.parseInt((page == null || page == "0") ? "1"
				: page);
		// 每页显示条数
		/*
		 * int pageSize = Integer.parseInt((rows == null || rows == "0") ? "10"
		 * : rows); PageInfo<MenuInfo> pageInfo = new PageInfo<MenuInfo>();
		 * pageInfo.setCurrentPage(currentPage); pageInfo.setPageSize(pageSize);
		 */
		MenuInfo menu = new MenuInfo();
		if (StringUtil.isNotEmpty(menuName)) {
			menu.setMenuName(menuName);
		}
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		List<MenuInfo> menuList = menuService
				.querySystemMenus(/* pageInfo, */menu);
		jsonMap.put("rows", menuList);
		/* jsonMap.put("total", pageInfo.getTotalRecords()); */
		return jsonMap;
	}

	/**
	 * @Title: queryMenus
	 * @Description: 分页查询菜单
	 * @param @param page 前台需要显示的页数
	 * @param @param rows 一次性要显示的多少行
	 * @param @param menu
	 * @param @return
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = "/queryMenus")
	@ResponseBody
	public Map<String, Object> queryMenus(Integer page, Integer rows,
			MenuInfo menu) {
		log.info("查询菜单记录");
		int currentPage = (page == null || page == 0) ? 1 : page;// 当前页 int
		int pageSize = (rows == null || rows == 0) ? 10 : rows;// 每页显示条数
		PageInfo<MenuInfo> pageInfo = new PageInfo<MenuInfo>();
		// 设置好当前要现实的页数以及一页要显示几条记录
		pageInfo.setCurrentPage(currentPage);
		pageInfo.setPageSize(pageSize);
		List<MenuInfo> menuList = menuService.queryAllMenus(pageInfo, menu);
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("rows", menuList);// rows键 存放每页记录 list
		jsonMap.put("total", pageInfo.getTotalRecords());// 总记录数
		return jsonMap;
	}

	/**
	 * 保存或修改菜单信息
	 * 
	 * @param menu
	 * @return
	 */

	@RequestMapping(value = "/saveOrUpdateMenu", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveOrUpdateMenu(MenuInfo menuInfo) {
		int result = 0;
		Map<String, Object> map = new HashMap<String, Object>();
		// 保存新的菜单
		if (StringUtil.isEmpty(menuInfo.getMenuId())) {
			menuInfo.setMenuOrder(menuService.getMaxMenuOrder() + 1);
			menuInfo.setCreator(getUserInfo().getUserId());
			menuInfo.setCreateTime(new Date());
			result = menuService.saveMenu(menuInfo);
		}
		// 更新菜单
		else {
			result = menuService.updateMenu(menuInfo);
		}
		log.info("result=================" + result);
		if (result == 1) {
			map.put("status", 0);
		} else {
			map.put("status", 1);
		}
		return map;
	}

	@RequestMapping(value = "/deleteMenuById", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMenuById(String menuId) {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		if (StringUtil.isNotEmpty(menuId)) {
			menuService.deleteRoleMenuByMenuId(menuId);// 删除菜单前，删除所有拥有此菜单的角色菜单关联
			result = menuService.deleteMenu(menuId);
			log.info("删除菜单成功");
		}
		log.info("result=================" + result);
		if (result == 1) {
			map.put("status", 1);
		} else {
			map.put("status", 0);
		}
		return map;
	}
}