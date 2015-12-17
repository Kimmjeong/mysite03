package com.hanains.mysite.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.hanains.mysite.service.UserService;
import com.hanains.mysite.vo.UserVo;

public class AuthLoginInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		String email=request.getParameter("email");
		String password=request.getParameter("password");
		UserVo vo=new UserVo();
		vo.setEmail(email);
		vo.setPassword(password);
		
		ApplicationContext applicationContext=WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
		// Root ApplicationContext가 참조하는 인터페이스이므로 그 아래의 모든거 가져올 수 있음
		// ApplicationContext(인터페이스) - Root WebApplication Context(비즈니스-dao) - Servlet WebApplication Context(controller)
		
		UserService userService = applicationContext.getBean(UserService.class); // UserService 클래스 가져옴
		UserVo authUser=userService.login(vo); // userService에 접근해서 login 메소드 바로 쓸 수 있음
		
		if(authUser==null){ // 로그인 정보가 없을 때
			response.sendRedirect(request.getContextPath()+"/user/loginform");
			return false;
		}
		
		// 로그인 처리
		HttpSession session=request.getSession(true);
		session.setAttribute("authUser", authUser);
		
		response.sendRedirect(request.getContextPath()); // main으로 가기
		
		return false;
	}
	
}
