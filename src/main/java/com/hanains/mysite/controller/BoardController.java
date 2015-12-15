package com.hanains.mysite.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hanains.mysite.service.BoardService;
import com.hanains.mysite.vo.BoardListVo;
import com.hanains.mysite.vo.BoardVo;
import com.hanains.mysite.vo.UserVo;

@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired
	private BoardService boardService;
	
	 // 전체 리스트
	@RequestMapping("/{page}")
	public String list(
			@PathVariable("page") Long page,
			HttpSession session, Model model
			){
		
		System.out.println("전체리스트");
		
		String kwd=null;
		
		List<BoardListVo> list=boardService.list(model, session, page, kwd);
		model.addAttribute(list);
		return "/board/list";
	}
	
	// 검색리스트
	@RequestMapping("/{page}&{kwd}")
	public String searchList(
			@PathVariable("page") Long page,
			@PathVariable("kwd") String kwd,
			HttpSession session, Model model
			){
		
		System.out.println("검색리스트");
		
		List<BoardListVo> list=boardService.list(model, session, page, kwd);
		model.addAttribute(list);
		return "/board/list";
	}
	
	
	// 글쓰기 폼
	@RequestMapping("/writeform") 
	public String writeform(){
		return "/board/write";
	}
	
	// 글쓰기
	@RequestMapping("/write") 
	public String write(HttpSession session, @ModelAttribute BoardVo vo){
		UserVo authUser=(UserVo) session.getAttribute("authUser");
		if(authUser!=null){
			vo.setMemberNo(authUser.getNo());
			boardService.insert(vo);
		}
		return "redirect:/board/";
	}
	
	// 글보기
	@RequestMapping("/view/{no}") 
	public String view(HttpSession session, @PathVariable("no") Long no, Model model){
		BoardVo writing=boardService.view(session, no);
		model.addAttribute("writing",writing);
		return "/board/view";
	}
	
	//글수정 폼
	@RequestMapping("/modifyform/{no}") 
	public String modifyform(@PathVariable("no") Long no, Model model,HttpSession session){
		
		BoardVo writing=boardService.view(session, no);
		UserVo authUser=(UserVo) session.getAttribute("authUser");
		
		if(authUser==null || authUser.getNo()!=writing.getMemberNo()){
			return "redirect:/board/view/"+no;
		}
		
		model.addAttribute("writing",writing);
		return "/board/modify";
		
	}
	
	// 글수정
	@RequestMapping("/modify") 
	public String modify(@ModelAttribute BoardVo vo, @RequestParam(value = "no", required = true, defaultValue = "") Long no){
		boardService.update(vo);
		return "redirect:/board/view/"+no;
	}
	
	// 삭제
	@RequestMapping("/delete/{no}&{memberNo}") 
	public String delete(HttpSession session, @PathVariable("no") Long no, @PathVariable("memberNo") Long memberNo){
		UserVo authUser=(UserVo) session.getAttribute("authUser");
		if (authUser != null) {
			if (authUser.getNo().equals(memberNo))
				boardService.delete(no, memberNo);

		}
		return "redirect:/board/";
	}
	
}
