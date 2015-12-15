package com.hanains.mysite.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hanains.mysite.vo.BoardListVo;
import com.hanains.mysite.vo.BoardVo;

@Repository
public class BoardDao {
	
	@Autowired
	private SqlSession sqlSession;
		
	// 전체 글목록
	public List<BoardListVo> getList(boolean search, String kwd, Long start, Long end){
		
		String where = "";

		if (search) {
			where = "and (a.title like '%%"+kwd+"%%' or b.name like '%%"+kwd+"%%')" ;
		}
		
		System.out.println("검색?: "+where);
		System.out.println(start);
		System.out.println(end);
		
		Map<String, Object> map=new HashMap<>();
		map.put("start", start);
		map.put("end", end);
		map.put("where", where);
		
		List<BoardListVo> list=sqlSession.selectList("board.getbyBoardList", map);
		return list;
	}
		
	// 글쓰기
	public void insert(BoardVo vo){
		sqlSession.selectOne("board.insert", vo);
	}

	// 글보기
	public BoardVo view(Long no){
		BoardVo vo=sqlSession.selectOne("board.view",no);
		return vo;
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
		Long maxGroupNo=sqlSession.selectOne("board.getbyGroupNo");
		return maxGroupNo;
	}

	// 그룹 내 순서 업데이트
	public void updateOrderNo(Long orderNo) {
		sqlSession.selectOne("board.updateOrderNo");
	}
		
}
