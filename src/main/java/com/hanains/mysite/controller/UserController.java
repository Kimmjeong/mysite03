package com.hanains.mysite.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hanains.mysite.service.UserService;
import com.hanains.mysite.vo.UserVo;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/joinform")
	public String joinform(){
		
		return "/user/joinform"; // joinform.jsp
	}
	
	@RequestMapping("/join")
	public String join(@ModelAttribute UserVo vo){
		userService.join(vo);
		return "redirect:/user/joinsuccess"; // /joinsuccess로 가도록
	}
	
	@RequestMapping("/joinsuccess")
	public String joinSuccess(){
		return "/user/joinsuccess"; // joinsucces.jsp
	}
	
	@RequestMapping("/loginform")
	public String loginform(){
		
		return "/user/loginform"; // loginform.jsp
	}
	
	@RequestMapping("/login")
	public String login(HttpSession session, @ModelAttribute UserVo vo){
		UserVo authUser=userService.login(vo);
		
		if(authUser==null){
			return "/user/loginform_retry";
		}
		session.setAttribute("authUser", authUser);
		return "redirect:/"; // main으로 가도록
	}
	
	
	@RequestMapping("/logout")
	public String logout(HttpSession session){
		session.removeAttribute("authUser");
		session.invalidate();
		return "redirect:/"; // main으로 가도록
	}
}
