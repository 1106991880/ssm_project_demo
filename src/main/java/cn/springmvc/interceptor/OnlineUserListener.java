/**
 * @author:稀饭
 * @time:下午8:43:26
 * @filename:OnlineUserListener.java
 */
package cn.springmvc.interceptor;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import cn.springmvc.model.UserInfo;
/*session监控器*/
public class OnlineUserListener implements HttpSessionListener {
	/*生成session對象時就会激发方法*/
	public void sessionCreated(HttpSessionEvent event) {
		System.out.println("新建session:" + event.getSession().getId());
	}
	/*session触发了invalidate方法就会激发该方法*/
	public void sessionDestroyed(HttpSessionEvent event) {
		HttpSession session = event.getSession();
		UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
		ServletContext context = session.getServletContext();
		// 取得登录的用户id
		String userId = userInfo.getUserId();
		// 从在线列表中删除用户名
		ArrayList<String> list = (ArrayList<String>) context
				.getAttribute("userId");
		list.remove(userId);
		System.out.println(userId + "已经退出！");
	}
}