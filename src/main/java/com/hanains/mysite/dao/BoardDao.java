package com.hanains.mysite.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hanains.mysite.vo.BoardDTO;
import com.hanains.mysite.vo.BoardVo;

@Repository
public class BoardDao {
	
	@Autowired
	private SqlSession sqlSession;
		
	// 전체 글목록
	public List<BoardDTO> getList(String kwd, Long start, Long end){
		
		Map<String, Object> map=new HashMap<>();
		map.put("start", start);
		map.put("end", end);
		map.put("kwd", kwd);
		
		List<BoardDTO> list=sqlSession.selectList("board.getBoardList", map);
		return list;
	}
	
	// 게시글 수
	public Long getCount(String kwd){
		Map<String, Object> map=new HashMap<>();
		map.put("kwd", kwd);
		
		Long cnt=sqlSession.selectOne("board.getCount",map);
		return cnt;
	}
	
	// 하나의 게시글 정보
	public BoardVo getOneBoard(Long no){
		BoardVo vo=sqlSession.selectOne("board.getOneBoardData",no);
		return vo;
	}
	
	/*
	// 글보기
	public BoardVo view(Long no){
		BoardVo vo=sqlSession.selectOne("board.view",no);
		return vo;
	}
	*/	
	
	// 글쓰기
	public void insert(BoardVo vo){
		sqlSession.selectOne("board.insert", vo);
	}

	// 글삭제
	public void delete(Long no, Long memberNo){
		
		Map<String, Object> map=new HashMap<>();
		map.put("no", no);
		map.put("memberNo", memberNo);
		
		sqlSession.selectOne("board.delete",map);
	}

	// 글수정
	public void update(BoardVo vo){
		sqlSession.selectOne("board.update",vo);
	}

	// 조회수 증가
	public void viewCount(Long no){
		sqlSession.selectOne("board.viewCount",no);
	}


	// 최대 그룹 번호
	public Long getGroupNo() {
		Long maxGroupNo=sqlSession.selectOne("board.getGroupNo");
		return maxGroupNo;
	}

	// 그룹 내 순서 업데이트
	public void updateOrderNo(Long orderNo) {
		sqlSession.selectOne("board.updateOrderNo", orderNo);
	}
		
}
