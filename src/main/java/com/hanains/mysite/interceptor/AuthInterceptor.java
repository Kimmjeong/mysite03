package com.hanains.mysite.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.hanains.mysite.annotation.Auth;
import com.hanains.mysite.vo.UserVo;

public class AuthInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		System.out.println("AuthInterceptor.preHandle Called");
		
		if(handler instanceof HandlerMethod == false){ // 이미지같은 요청일 경우 Controller Handler가 아니라 default Servlet Handler이기 때문에 HanlderMethod로 캐스팅하면 오류가 난다.
			return true;
		}
		Auth auth=((HandlerMethod) handler).getMethodAnnotation(Auth.class);
		
		if(auth==null){ // 메소드에 Auth 어노테이션이 안달려 있을 때
			return true;
		}
		
		HttpSession session=request.getSession();
		
		if(session==null){
			response.sendRedirect(request.getContextPath()+"/user/loginform");
			return false;
		}
		
		UserVo vo=(UserVo) session.getAttribute("authUser");
		if(vo==null){
			response.sendRedirect(request.getContextPath()+"/user/loginform");
			return false;
		}
		
		return true;
	}
	
}
