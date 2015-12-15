package com.hanains.mysite.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.hanains.mysite.dao.BoardDao;
import com.hanains.mysite.vo.BoardListVo;
import com.hanains.mysite.vo.BoardVo;

@Service
public class BoardService {

	@Autowired
	private BoardDao boardDao;
	
	public List<BoardListVo> list(Model model, HttpSession session, Long page, String kwd){
		Long totalCount = 0L;
		//페이징 처리
		Long totalPage = 0L; // 총 게시물 수, 총 페이지 수
		Long pageSize = 5L;// 한페이지의 게시물수
		Long blockSize = 3L; // 페이지 바의 페이지 수
		Long temp = 0L; // 페이지번호
		
		Long nowPage=page; // 현재 페이지
		
		System.out.println("현재페이지: "+page);
		System.out.println("검색어: "+kwd);
		
		// 페이징 범위 계산
		Long start = ((nowPage - 1) * pageSize) + 1;
		Long end = start + pageSize - 1;

		//페이지바의 페이지 번호 역할
		temp = ((nowPage - 1) / blockSize) * blockSize + 1;
				
		boolean search = false;
		if (kwd != null)
			search = true;
		else
			kwd="";
		
		List<BoardListVo> list=boardDao.getList(search, kwd, start, end);
		
		System.out.println(list);
		
		// 전체 글 갯수
		if(list.size()==0)
			totalCount=0L;
		else
			totalCount=list.get(0).getTotcnt();
		
		// 페이지 수
		totalPage = (totalCount % pageSize)==0? (totalCount/pageSize) : (totalCount/pageSize)+1;
		System.out.println("페이지수: "+totalPage);
		
		// 값 넘겨주기
		
		model.addAttribute("temp", temp);
		model.addAttribute("nowPage", nowPage);
		model.addAttribute("totalCount", totalCount); 
		model.addAttribute("totalPage", totalPage); 
		model.addAttribute("blockSize", blockSize);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("kwd", kwd);
				
		// 조회수 중복 증가 막기
		session.setAttribute("read", "no");
		
		return list;
	}
	
	public void insert(BoardVo vo){
		
		Long groupNo=0L;
		Long orderNo=0L;
		Long depth=0L;
		
		// 새글
		if (vo.getGroupNo().equals("") && vo.getOrderNo().equals("") && vo.getDepth().equals("")) {
			groupNo = boardDao.getGroupNo()+1;
			orderNo = 1L;
			depth = 0L;
			
		} else { // 답글
			
			Long parentOrderNo = vo.getOrderNo();
			Long parentDepth = vo.getDepth();
			
			groupNo = vo.getGroupNo();
			orderNo = parentOrderNo + 1;
			depth = parentDepth + 1;
			
			boardDao.updateOrderNo(orderNo);
		}
		
		vo.setGroupNo(groupNo);
		vo.setOrderNo(orderNo);
		vo.setDepth(depth);
		
		boardDao.insert(vo);
	}
	
	public BoardVo view(HttpSession session, Long no){
		
		BoardVo vo=boardDao.view(no);
		
		//조회수 업데이트
		if (session.getAttribute("read") != null && session.getAttribute("read").toString().equals("no")) {
			boardDao.viewCount(no);
			session.setAttribute("read", "yes");
		}

		return vo;
	}
	
	public void delete(Long no, Long memberNo){
		boardDao.delete(no, memberNo);
	}
	
	public void update(BoardVo vo){
		boardDao.update(vo);
	}
	
	public void viewCount(Long no){
		boardDao.viewCount(no);
	}
	
	public Long getGroupNo() {
		Long maxGroupNo=boardDao.getGroupNo();
		return maxGroupNo;
	}
	
	public void updateOrderNo(Long orderNo) {
		boardDao.updateOrderNo(orderNo);
	}
}
