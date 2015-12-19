package com.hanains.mysite.controller;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.hanains.mysite.annotation.Auth;
import com.hanains.mysite.annotation.AuthUser;
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
	@Auth
	@RequestMapping("/writeform") 
	public String writeform(){
		return "/board/write";
	}
	
	// 글쓰기
	@Auth
	@RequestMapping("/write") 
	public String write(@AuthUser UserVo authUser, @ModelAttribute BoardVo vo, @RequestParam( "uploadFile" ) MultipartFile multipartFile){
		vo.setMemberNo(authUser.getNo());
		boardService.insert(vo, multipartFile);
		return "redirect:/board";
	}
	
	// 답글달기 폼
	@Auth
	@RequestMapping("/reply/{no}")
	public String reply( @PathVariable("no") Long no, Model model){
		BoardVo vo = boardService.writeData(no);
		model.addAttribute("vo",vo);
		return "/board/write";
	}
	
	// 글보기
	@RequestMapping("/view/{no}") 
	public String view(@PathVariable("no") Long no, Model model){
		Map<String, Object> map=boardService.view(no);
		model.addAttribute("writeView",map);
		return "/board/view";
	}
	
	//글수정 폼
	@Auth
	@RequestMapping("/modifyform/{no}") 
	public String modifyform(@PathVariable("no") Long no, Model model){
		Map<String, Object> map=boardService.getModifyWriteInfo(no);
		model.addAttribute("writeView",map);
		return "/board/modify";
		
	}
	
	// 글수정
	@Auth
	@RequestMapping("/modify") 
	public String modify(@AuthUser UserVo authUser, @ModelAttribute BoardVo vo, @RequestParam( "uploadFile" ) MultipartFile multipartFile){
		vo.setMemberNo( authUser.getNo() );
		boardService.update(vo, multipartFile);
		return "redirect:/board";
	}
	
	// 삭제
	@Auth
	@RequestMapping("/delete/{no}") 
	public String delete(@AuthUser UserVo authUser, @PathVariable("no") Long no){
		boardService.delete( no, authUser.getNo() );
		return "redirect:/board";
	}	
		
	
}
