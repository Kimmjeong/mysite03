package com.hanains.mysite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hanains.mysite.service.GuestbookService;
import com.hanains.mysite.vo.GuestBookVo;

@Controller
@RequestMapping("/guestbook")
public class GuestbookController {

	@Autowired
	private GuestbookService guestbookService;
	
	@RequestMapping("/")
	public String list(Model model){
		List<GuestBookVo> list=guestbookService.list();
		model.addAttribute("list",list);
		
		return "/guestbook/list";
	}
	
	@RequestMapping("/insert")
	public String insert(@ModelAttribute GuestBookVo vo){ // vo의 필드명과 jsp의 name이 같아야 자동 매핑된다
		guestbookService.insert(vo);
		return "redirect:/guestbook/";
	}
	
	@RequestMapping("/deleteform/{no}")
	public String deleteform(@PathVariable("no") Long no, Model model){
		model.addAttribute(no);
		return "/guestbook/deleteform";
	}
	
	@RequestMapping("/delete")
	public String delete(@RequestParam(value = "no", required = true, defaultValue = "") Long no,
			@RequestParam(value = "password", required = true, defaultValue = "") String password) {
		guestbookService.delete(no, password);
		return "redirect:/guestbook/";
	}
}
