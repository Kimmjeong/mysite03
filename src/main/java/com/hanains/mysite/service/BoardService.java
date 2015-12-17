package com.hanains.mysite.service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hanains.mysite.dao.BoardDao;
import com.hanains.mysite.vo.BoardDTO;
import com.hanains.mysite.vo.BoardVo;
import com.hanains.mysite.vo.UploadFileVo;

@Service
public class BoardService {

	private final int pageSize = 5;// 한페이지의 게시물수
	private final int blockSize = 3; // 페이지 바의 페이지 수

	private static final Log LOG = LogFactory.getLog(BoardService.class);
	private static final String SAVE_PATH = "/hanains/mysiteImage/";

	@Autowired
	private BoardDao boardDao;

	public Map<String, Object> list(Long page, String kwd) {

		Long totalCount = 0L; // 총 게시물 수
		Long totalPage = 0L; // 총 페이지 수
		Long pageNum = 0L; // 페이지번호
		Long nowPage = page; // 현재 페이지
		// 페이징
		Long start = ((nowPage - 1) * pageSize) + 1; // 페이지 시작
		Long end = start + pageSize - 1; // 페이지 끝

		// 각 블럭의 시작 페이지 번호
		pageNum = ((nowPage - 1) / blockSize) * blockSize + 1;

		List<BoardDTO> list = boardDao.getList(kwd, start, end);

		// 전체 글 갯수
		totalCount = boardDao.getCount(kwd);
		// 전체 페이지 수
		totalPage = (totalCount % pageSize) == 0 ? (totalCount / pageSize) : (totalCount / pageSize) + 1;

		// 값 넘겨주기
		Map<String, Object> map = new HashMap<>();

		map.put("pageNum", pageNum);
		map.put("nowPage", nowPage);
		map.put("totalCount", totalCount);
		map.put("totalPage", totalPage);
		map.put("blockSize", blockSize);
		map.put("pageSize", pageSize);
		map.put("kwd", kwd);
		map.put("list", list);

		return map;
	}

	public void insert(BoardVo vo) {

		Long groupNo = 0L;
		Long orderNo = 0L;
		Long depth = 0L;

		// 새글
		if (vo.getGroupNo() == null) {
			groupNo = boardDao.getGroupNo() + 1;
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

	public void insertFile(MultipartFile multipartFile) {

		// 파일 처리
		if (multipartFile.isEmpty() == false) {

			String fileOriginalName = multipartFile.getOriginalFilename();
			String extName = fileOriginalName.substring(fileOriginalName.lastIndexOf(".") + 1,
					fileOriginalName.length());
			Long size = multipartFile.getSize();
			String saveFileName = getSaveFileName(extName);

			// 폴더에 파일 업로드하기
			writeFile(multipartFile, SAVE_PATH, saveFileName);

			UploadFileVo vo = new UploadFileVo();
			vo.setOriginalName(fileOriginalName);
			vo.setExtName(extName);
			vo.setFileSize(size);
			vo.setSaveName(saveFileName);
			
			System.out.println(vo);
			
			boardDao.insertFile(vo);
		}
	}

	public BoardVo writeInfo(Long no) {
		BoardVo vo = boardDao.getOneBoard(no);
		return vo;
	}

	public Map<String, Object> view(Long no) {

		BoardVo vo = boardDao.getOneBoard(no);
		// 조회수 업데이트
		boardDao.viewCount(no);
		
		UploadFileVo fileVo=boardDao.getFileData(no);
		
		String url=null;
		if(fileVo!=null)
			url = "/board-images/" + fileVo.getSaveName();
		
		Map<String, Object> map=new HashMap<>();
		map.put("boardVo", vo);
		map.put("imageUrl", url);
		
		return map;
	}

	public void delete(Long no, Long memberNo) {
		boardDao.delete(no, memberNo);
	}

	public void update(BoardVo vo) {
		boardDao.update(vo);
	}

	public void viewCount(Long no) {
		boardDao.viewCount(no);
	}

	public Long getGroupNo() {
		Long maxGroupNo = boardDao.getGroupNo();
		return maxGroupNo;
	}

	public void updateOrderNo(Long orderNo) {
		boardDao.updateOrderNo(orderNo);
	}

	private void writeFile(MultipartFile file, String path, String fileName) {
		FileOutputStream fos = null;
		try {
			byte fileData[] = file.getBytes();
			fos = new FileOutputStream(path + fileName);
			fos.write(fileData);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (Exception e) {
				}
			}
		}
	}
	
	private void deleteFile(MultipartFile file, String path, String fileName){
		
	}

	private String getSaveFileName(String extName) {

		Calendar calendar = Calendar.getInstance();
		String fileName = "";

		fileName += calendar.get(Calendar.YEAR);
		fileName += calendar.get(Calendar.MONTH);
		fileName += calendar.get(Calendar.DATE);
		fileName += calendar.get(Calendar.HOUR);
		fileName += calendar.get(Calendar.MINUTE);
		fileName += calendar.get(Calendar.SECOND);
		fileName += calendar.get(Calendar.MILLISECOND);
		fileName += ("." + extName);

		return fileName;
	}

}
