package cn.springmvc.interceptor;

import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class MyInterceptor implements HandlerInterceptor {
	/* 最后执行！！！一般用于释放资源！！ */
	@Override
	public void afterCompletion(HttpServletRequest request,

	HttpServletResponse response, Object handler, Exception ex)

	throws Exception {

		System.out.println("zxs- 最后执行！！！一般用于释放资源！！ ");

	}

	/* action 执行之后,生成视图之前执行 */
	@Override
	public void postHandle(HttpServletRequest request,

	HttpServletResponse response, Object handler,

	ModelAndView modelAndView) throws Exception {
		// Enumeration<String> arg= request.getParameterNames();
		// while (arg.hasMoreElements()) {
		// String key = (String) arg.nextElement();
		// String value = request.getParameter(key);
		// System.out.println("key=>"+key+"   value=>"+value);
		// }
		// request.setCharacterEncoding("UTF-8");
		// response.setContentType("text/html;charset=utf-8");
		// System.out.println("zxs-Action 执行之后，生成视图之前执行！！ ");

	}

	/* action 之前执行！！！ */
	@Override
	public boolean preHandle(HttpServletRequest request,

	HttpServletResponse response, Object handler) throws Exception {
		ServletContext application = request.getServletContext();
		Enumeration<String> arg = request.getParameterNames();
		while (arg.hasMoreElements()) {
			String key = (String) arg.nextElement();
			String value = request.getParameter(key);
			System.out.println("key=>" + key + "   value=>" + value);
		}
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		// 继续执行 action
		return true;
	}

}