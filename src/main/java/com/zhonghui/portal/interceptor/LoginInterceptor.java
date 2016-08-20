package com.zhonghui.portal.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.huizhong.pojo.TbUser;
import com.zhonghui.common.utils.CookieUtils;
import com.zhonghui.portal.service.impl.UserServiceImpl;

public class LoginInterceptor implements HandlerInterceptor {

	@Autowired
	private UserServiceImpl userService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 在handler执行之前处理
		// 1.判断用户是否登录，从cookie中取token
		String token = CookieUtils.getCookieValue(request, "ZH_TOKEN");
		// 2.根据token换取用户信息，调用sso系统的服务
		TbUser user = userService.getUserByToken(token);
		// 取不到用户信息
		if(user == null){
			// 需要跳转到登录页面，把用户请求的url作为参数传递给登录页面，返回false
			response.sendRedirect(userService.SSO_BASE_URL + userService.SSO_PAGE_LOGIN + "?redirect=" + request.getRequestURL());
			return false;
		}
		// 取到用户信息，放行
		return true;
		// 返回值决定Handler是否执行
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// 在handler执行之后，返回ModelAndView之前

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// 返回ModelAndView之后，响应用户之后

	}

}
