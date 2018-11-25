/**
 * @author:稀饭
 * @time:下午1:28:08
 * @filename:UserController.java
 */
package cn.springmvc.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.springmvc.model.DeptInfo;
import cn.springmvc.model.RoleInfo;
import cn.springmvc.model.UserInfo;
import cn.springmvc.service.DeptInfoService;
import cn.springmvc.service.RoleService;
import cn.springmvc.service.UserInfoService;
import cn.springmvc.util.DownloadUtil;
import cn.springmvc.util.ParameterUtil;
import cn.springmvc.util.StringUtil;
import cn.springmvc.utildao.PageInfo;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

	private Logger log = Logger.getLogger(this.getClass());
	@Autowired
	private UserInfoService userService;
	@Autowired
	private DeptInfoService deptInfoService;
	@Autowired
	private RoleService roleService;

	/**
	 * @Title: queryUser
	 * @Description: TODO
	 * @param @param model
	 * @param @param pageInfo
	 * @param @param userInfo 使用modelAttribute进行数据绑定再显示到视图
	 * @param @param dept
	 * @param @return
	 * @return String
	 */
	@RequestMapping(value = "/queryUser")
	public String queryUser(Model model,
			@ModelAttribute(value = "pageInfo") PageInfo<UserInfo> pageInfo,
			@ModelAttribute(value = "user") UserInfo userInfo,
			@ModelAttribute(value = "dept") DeptInfo dept) {
		////log.info("查询用户列表");
		Map<String, String> map = new HashMap<String, String>();
		String trueName = userInfo.getTrueName();
		map.put("mail", userInfo.getMail());
		map.put("deptNo", dept.getDeptNo());
		map.put("userLevel", userInfo.getUserLevel());
		List<UserInfo> list = new ArrayList<UserInfo>();
		if (trueName != null) {
			String[] names = trueName.split(" ");
			for (int i = 0; i < names.length; i++) {
				map.put("trueName", names[i]);
				List<UserInfo> list1 = userService.queryUsers(map, pageInfo);
				for (int j = 0; j < list1.size(); j++) {
					list.add(list1.get(j));
				}
				map.remove("trueName");
			}
		} else {
			map.put("trueName", trueName);
			list = userService.queryUsers(map, pageInfo);
		}
		model.addAttribute("list", list);
		model.addAttribute("own_user", this.getUserInfo());
		model.addAttribute("userLevel", super.getUserInfo().getUserLevel());
		model.addAttribute("userLevelMap", super.userLevelMap());

		return "/user/query";
	}
	/**
	 * @Title: addUser
	 * @Description: 如果是超级管理员则有权限可以添加管理员，否则只能添加用户
	 * @param @param userInfo
	 * @param @param model
	 * @param @return
	 * @return String
	 */
	@RequestMapping("/addUser")
	public String addUser(@ModelAttribute(value = "user") UserInfo userInfo,
			Model model) {
		log.info("目前正在添加用户");
		if (this.getUserInfo().getUserName().equals("system")) {
			model.addAttribute("userLevelMap", super.userLevelMap());
		} else {
			Map<String, String> map = super.userLevelMap();
			map.remove("0");
			model.addAttribute("userLevelMap", map);
		}
		return "user/add";
	}

	/**
	 * @Title: editUser
	 * @Description: 如果不是超级管理员，无法进行系统管理员的权限赋予
	 * @param @param model
	 * @param @param userId
	 * @param @return
	 * @return String
	 */
	@RequestMapping(value = "/editUser")
	public String editUser(Model model, String userId) {
		log.info("进入编辑页面");
		UserInfo userInfo = userService.getUser(userId);
		String deptId = userInfo.getDeptId();
		if (StringUtil.isNotEmpty(deptId)) {
			DeptInfo dept = deptInfoService.getDeptByDeptId(deptId);
			model.addAttribute("deptName", dept.getDeptName());
		}
		model.addAttribute("user", userInfo);
		if (this.getUserInfo().getUserName().equals("system")) {
			model.addAttribute("userLevelMap", super.userLevelMap());
		} else {
			Map<String, String> map = super.userLevelMap();
			map.remove("0");
			model.addAttribute("userLevelMap", map);
		}
		return "/user/update";
	}

	/**
	 * 文件删除
	 * 
	 * @param userId
	 */
	@RequestMapping(value = "/deleteFile", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Integer> deleteFile(String userId) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		int result = userService.deleteFile(userId);
		map.put("result", result);
		return map;
	}

	/**
	 * 权限分配
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/allotUserRole", method = RequestMethod.GET)
	public @ResponseBody
	Map<String, Object> allotUserRole(String userId) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		UserInfo userInfo = userService.getUser(userId);
		List<RoleInfo> roles = roleService.getAllRoles();
		List<RoleInfo> haves = roleService.getRolesByUserId(userId);
		for (RoleInfo role : roles) {
			if (haves != null && haves.size() > 0) {
				for (RoleInfo have : haves) {
					if (role.getRoleId().equals(have.getRoleId())) {
						role.setUserHave(1);
					}
				}
			}
		}
		jsonMap.put("userInfo", userInfo);
		jsonMap.put("roles", roles);
		return jsonMap;
	}

	@RequestMapping("/saveUser")
	public String saveUser(UserInfo userInfo,
			@RequestParam("fileData") MultipartFile fileData) {
		try {
			if (null != userInfo) {
				if (fileData.getSize() != 0) {
					String fileName = fileData.getOriginalFilename();
					byte[] fileByte = fileData.getBytes();
					userInfo.setFileName(fileName);
					userInfo.setFileByte(fileByte);
				}
				userInfo.setCreateTime(new Date());
				userInfo.setUpdateTime(new Date());
				userService.saveUser(userInfo);
				log.info("插入用户信息成功");
			}
		} catch (Exception e) {
			log.info("插入用户信息失败");
			log.info(e.getCause());
			// throw new RuntimeException();
		}
		return "redirect:queryUser";
	}

	@RequestMapping("/checkUserName")
	public @ResponseBody
	Map<String, Integer> checkUserName(Model model, String userName) {
		log.info("目前正在检查用户" + userName);
		Map<String, Integer> map = new HashMap<String, Integer>();
		UserInfo userInfo = userService.checkUserName(userName);
		if (userInfo != null) {
			log.info("检查存在用户名");
			map.put("count", 1);
		} else {
			log.info("检查不存在用户名");
			map.put("count", 0);
		}
		return map;
	}

	/**
	 * 文件下载
	 * 
	 * @param userId
	 * @param response
	 */
	@RequestMapping(value = "/downloadFile", method = RequestMethod.GET)
	public void downloadFile(String userId, final HttpServletResponse response) {
		UserInfo userFile = userService.downloadFile(userId);
		String fileName = userFile.getFileName();
		byte[] fileByte = userFile.getFileByte();
		DownloadUtil.downloadFile(response, fileName, fileByte);
	}

	/**
	 * 删除用户
	 * 
	 * @param model
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
	public String deleteUser(Model model, String userId) {
		if (StringUtil.isNotEmpty(userId)) {
			String[] idItem = userId.split(",");
			log.info("删除用户");
			userService.deleteUser(idItem);
		}
		return "redirect:queryUser";
	}

	/**
	 * 更新用户信息
	 * 
	 * @param userInfo
	 * @param fileData
	 * @return
	 */
	@RequestMapping(value = "/updateUser", method = RequestMethod.POST)
	public String updateUser(UserInfo userInfo,
			@RequestParam("fileData") MultipartFile fileData) {
		try {
			if (StringUtil.isNotEmpty((userInfo.getUserId()))) {
				if (null != fileData && fileData.getSize() != 0) {
					String fileName = fileData.getOriginalFilename();
					byte[] fileByte = fileData.getBytes();
					userInfo.setFileName(fileName);
					userInfo.setFileByte(fileByte);
				}
				userInfo.setUpdateTime(new Date());
				userService.updateUser(userInfo);
				log.info("更新用户信息成功");
			}
		} catch (Exception e) {
			log.info("更新用户信息失败");
			log.info(e.getCause());
		}
		return "redirect:queryUser";
	}

	/**
	 * 保存权限分配
	 * 
	 * @param userId_role
	 * @param roleIds
	 * @return
	 */
	@RequestMapping(value = "/saveUserRole", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Integer> saveUserRole(String userId_role, String roleIds) {
		log.info("此刻正在保存用户角色：" + userId_role + "      " + roleIds);
		Map<String, Integer> jsonMap = new HashMap<String, Integer>();
		int result = userService.saveUserRole(userId_role, roleIds);
		jsonMap.put(ParameterUtil.RESULT, result);
		return jsonMap;
	}
}