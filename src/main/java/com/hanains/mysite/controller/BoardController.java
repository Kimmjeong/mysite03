package com.hanains.mysite.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hanains.mysite.service.BoardService;
import com.hanains.mysite.vo.BoardVo;
import com.hanains.mysite.vo.UserVo;

@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired
	private BoardService boardService;

	// 리스트
	@RequestMapping("")
	public String list(
			@RequestParam(value="kwd", required=true, defaultValue="") String kwd,
			@RequestParam(value="p", required=true, defaultValue="1") Long page,
			Model model
			){
		
		Map<String, Object> map=boardService.list(page, kwd);
		model.addAttribute("listData",map);
		
		return "/board/list";
	}
	
	
	// 글쓰기 폼
	@RequestMapping("/writeform") 
	public String writeform(HttpSession session){
		UserVo authUser=(UserVo) session.getAttribute("authUser");
		if(authUser==null){
			return "redirect:/user/loginform";
		}
		return "/board/write";
	}
	
	// 글쓰기
	@RequestMapping("/write") 
	public String write(HttpSession session, @ModelAttribute BoardVo vo){
		UserVo authUser=(UserVo) session.getAttribute("authUser");
		vo.setMemberNo(authUser.getNo());
		
		boardService.insert(vo);
		return "redirect:/board";
	}
	
	// 답글달기 폼
	@RequestMapping("/reply/{no}")
	public String reply(HttpSession session, @PathVariable("no") Long no, Model model){
		UserVo authUser=(UserVo) session.getAttribute("authUser");
		if(authUser==null){
			return "redirect:/user/loginform";
		}
		
		BoardVo vo = boardService.writeInfo(no);
		model.addAttribute("vo",vo);
		return "/board/write";
	}
	
	// 글보기
	@RequestMapping("/view/{no}") 
	public String view(@PathVariable("no") Long no, Model model){
		BoardVo vo=boardService.view(no);
		model.addAttribute("vo",vo);
		return "/board/view";
	}
	
	//글수정 폼
	@RequestMapping("/modifyform/{no}") 
	public String modifyform(@PathVariable("no") Long no, Model model,HttpSession session){
		
		
		UserVo authUser=(UserVo) session.getAttribute("authUser");
		
		if(authUser==null){
			return "redirect:/user/loginform";
		}
		
		BoardVo vo=boardService.view(no);
		model.addAttribute("vo",vo);
		return "/board/modify";
		
	}
	
	// 글수정
	@RequestMapping("/modify") 
	public String modify(HttpSession session, @ModelAttribute BoardVo vo){
		// 로그인 사용자 체크
		UserVo authUser = (UserVo)session.getAttribute( "authUser" );
		if( authUser == null ) {
			return "redirect:/user/loginform";
		}

		vo.setMemberNo( authUser.getNo() );
		
		boardService.update(vo);
		return "redirect:/board/";
	}
	
	// 삭제
	@RequestMapping("/delete/{no}") 
	public String delete(HttpSession session, @PathVariable("no") Long no){
		
		// 로그인 사용자 체크
		UserVo authUser = (UserVo)session.getAttribute( "authUser" );
		if( authUser == null ) {
			return "redirect:/user/loginform";
		}

		boardService.delete( no, authUser.getNo() );
		return "redirect:/board";
	}	
		
	
}
