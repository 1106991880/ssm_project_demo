/**
 * @author:稀饭
 * @time:下午8:43:26
 * @filename:CommonController.java
 */
package cn.springmvc.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.springmvc.model.DictionaryDetail;
import cn.springmvc.model.LoginUser;
import cn.springmvc.model.MenuInfo;
import cn.springmvc.model.UserInfo;
import cn.springmvc.service.DictionaryDetailService;
import cn.springmvc.service.LoginUserService;
import cn.springmvc.service.MenuInfoService;
import cn.springmvc.service.UserInfoService;
import cn.springmvc.util.IPAddressUtil;
import cn.springmvc.util.StringUtil;
import cn.springmvc.utildao.TreeData;

@Controller
@RequestMapping(value = "/common")
public class CommonController extends BaseController {

	private Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private MenuInfoService menuInfoService;
	@Autowired
	private DictionaryDetailService dictDetailService;
	@Autowired
	private LoginUserService loginUserService;

	/**
	 * @Title: saveLoginUser
	 * @Description: TODO
	 * @param @param request
	 * @return void
	 */
	public void saveLoginUser(String ip, String address) {
		Date now = new Date(); 
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//可以方便地修改日期格式
    	String time = dateFormat.format( now ); 
		LoginUser loginUser = new LoginUser();
		loginUser.setLoginuser_ip(ip);
		loginUser.setLoginuser_logintime(time);
		loginUser.setLoginuser_address(address);
		loginUserService.saveLoginUser(loginUser);
	}

	@RequestMapping(value = "/login")
	public @ResponseBody
	Map<String, Object> login(HttpServletRequest request, UserInfo user) {

		Map<String, Object> map = new HashMap<String, Object>();
		UserInfo userInfo = userInfoService.login(user);
		if (userInfo != null) {
			// @SuppressWarnings("unchecked")
			// ArrayList<String> list = (ArrayList<String>) getContext()
			// .getAttribute("userId");
			// if (list == null) {
			// list = new ArrayList<String>();
			// getContext().setAttribute("userId", list);
			// }
			/* 判断是否已经被登录，但是最后因为无法解决不正常关闭浏览器时监听session，只能取消掉该功能 */
			// if (list.contains(userInfo.getUserId())) {
			// System.out.println("該用戶已經登錄了哦");
			// map.put("result", "already");
			// } else {
			// String ip = IPAddressUtil.getIPAddr(request);
			// String address = IPAddressUtil.getPosition(ip, "utf-8");
			// if (address == null) {
			// address = new String("网络较差，查询不到地理位置");
			// }
			// saveLoginUser(ip, address);
			// super.setUserInfo(userInfo);
			// list.add(userInfo.getUserId());
			// map.put("result", "success");
			// }
			// list.add(userInfo.getUserId());
			String ip = IPAddressUtil.getIPAddr(request);
			String address = IPAddressUtil.getPosition(ip, "utf-8");
			if (address == null) {
				address = new String("网络较差，查询不到地理位置");
			}
			saveLoginUser(ip, address);
			super.setUserInfo(userInfo);
			map.put("result", "success");
		} else {
			logger.info("登錄失敗。。。。。。。。。。。。。。。。。。");
			map.put("result", "failed");
		}

		return map;
	}

	/**
	 * @Title: toMain
	 * @Description: 進入主頁面
	 * @param @param model
	 * @param @return
	 * @return String
	 */
	@RequestMapping("/toMain")
	public String toMain(Model model) {
		UserInfo userInfo = getUserInfo();
		model.addAttribute(userInfo);
		return "/main";
	}

	/**
	 * @Title: updatePwd
	 * @Description: TODO修改密碼
	 * @param @param oldpwd
	 * @param @param newpwd
	 * @param @return
	 * @return Map<String,Object>
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/updatePwd")
	public @ResponseBody
	Map<String, Object> updatePwd(String oldpwd, String newpwd) {
		Map<String, Object> map = new HashMap<String, Object>();
		UserInfo user = super.getUserInfo();
		if (!user.getPassword().equals(oldpwd)) {
			map.put("result", "error");
			return map;
		}
		try {
			logger.info("======================修改后的密碼是：" + newpwd);
			logger.info("======================getUserId是：" + user.getUserId());
			userInfoService.updatePwd(user.getUserId(), newpwd);
			user.setPassword(newpwd);
			map.put("result", "success");
		} catch (Exception e) {
			// TODO: handle exception
			map.put("result", "fail");
		} finally {
			return map;
		}
	}

	/**
	 * @Title: exitSystem
	 * @Description: TODO退出系统
	 * @param @return
	 * @return String
	 */
	@RequestMapping("exitSystem")
	@ResponseBody
	public String exitSystem(UserInfo user) {
		try {
			super.clearSession();
			return "success";
		} catch (Exception e) {
			// TODO: handle exception
			return "fail";
		}
	}

	@RequestMapping("getSystemMenu")
	@ResponseBody
	public List<TreeData> getSystemMenu() {
		List<TreeData> menuTree = new ArrayList<TreeData>();
		TreeData rootData = new TreeData();
		rootData.setId("");
		rootData.setText("系统菜单");
		rootData.setState("open");
		rootData.setChecked(false);
		rootData.setIconCls("icon-home");
		UserInfo userInfo = super.getUserInfo();
		String user_name = userInfo.getUserName();
		List<MenuInfo> menuInfos = null;
		if (user_name.equals("system")) {
			menuInfos = menuInfoService.getUserMenus("system");
		} else {
			menuInfos = menuInfoService.getUserMenus(null);
		}
		List<TreeData> childMenu = new ArrayList<TreeData>();
		for (MenuInfo menu : menuInfos) {
			TreeData childData = new TreeData();
			Map<String, String> attributes = new HashMap<String, String>();
			attributes.put("url", menu.getMenuUri());
			childData.setId(menu.getMenuId());
			childData.setText(menu.getMenuName());
			childData.setState("open");
			childData.setChecked(false);
			childData.setChildren(new ArrayList<TreeData>());
			childData.setIconCls(menu.getMenuIcon());
			childData.setAttributes(attributes);
			childMenu.add(childData);
		}
		rootData.setChildren(childMenu);
		menuTree.add(rootData);
		return menuTree;
	}

	/**
	 * @Title: getWebMenu
	 * @Description: TODO
	 * @param @return
	 * @return List<TreeData>
	 */
	@RequestMapping(value = "/getWebMenu", method = RequestMethod.POST)
	@ResponseBody
	public List<TreeData> getWebMenu() {
		List<TreeData> menuTree = new ArrayList<TreeData>();
		TreeData rootData = new TreeData();
		rootData.setId("");
		rootData.setText("快速链接");
		rootData.setState("open");
		rootData.setChecked(false);
		rootData.setIconCls("icon-cabin");
		List<MenuInfo> webMenus = menuInfoService.getAllWebMenus();
		List<TreeData> childMenu = new ArrayList<TreeData>();
		for (MenuInfo menu : webMenus) {
			TreeData childData = new TreeData();
			Map<String, String> attributes = new HashMap<String, String>();
			attributes.put("url", menu.getMenuUri());
			childData.setId(menu.getMenuId());
			childData.setText(menu.getMenuName());
			childData.setState("open");
			childData.setChecked(false);
			childData.setChildren(new ArrayList<TreeData>());
			childData.setIconCls(menu.getMenuIcon());
			childData.setAttributes(attributes);
			childMenu.add(childData);
		}
		rootData.setChildren(childMenu);
		menuTree.add(rootData);
		return menuTree;
	}

	/**
	 * @Title: loadDictCombobox
	 * @Description: TODO
	 * @param @param dictCode
	 * @param @return
	 * @return List<DictionaryDetail>
	 */
	@RequestMapping(value = "/loadDictCombobox", method = RequestMethod.GET)
	@ResponseBody
	public List<DictionaryDetail> loadDictCombobox(String dictCode) {
		List<DictionaryDetail> list = new ArrayList<DictionaryDetail>();
		if (StringUtil.isNotEmpty(dictCode)) {
			list.add(new DictionaryDetail());
			list.addAll(dictDetailService.loadDictCombobox(dictCode));
		}
		return list;
	}
}
