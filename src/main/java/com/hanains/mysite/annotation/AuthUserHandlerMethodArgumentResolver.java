package com.hanains.mysite.annotation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.hanains.mysite.vo.UserVo;

public class AuthUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer arg1, NativeWebRequest webRequest,
			WebDataBinderFactory arg3) throws Exception {
		
		if(this.supportsParameter(parameter)==false){ // 어노테이션을 쓰는 파라미터가 없을때
			return WebArgumentResolver.UNRESOLVED; // 끝냄
		}
		
		HttpServletRequest request=webRequest.getNativeRequest(HttpServletRequest.class); // webRequest -> HttpServletRequest 로 변환
		HttpSession session=request.getSession();
		
		if(session==null){
			return WebArgumentResolver.UNRESOLVED;
		}
		
		UserVo authUser=(UserVo) session.getAttribute("authUser");
		
		return authUser;
	}

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return (parameter.getParameterAnnotation(AuthUser.class)!=null) && 
			(parameter.getParameterType().equals((UserVo.class))==true);
		
	}

}
