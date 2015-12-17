package com.hanains.mysite.vo;

public class UploadFileVo {
	
	private Long no;
	private Long boardNo;
	private String originalName;
	private String saveName;
	private String extName;
	private Long fileSize;
	public Long getNo() {
		return no;
	}
	public void setNo(Long no) {
		this.no = no;
	}
	public Long getBoardNo() {
		return boardNo;
	}
	public void setBoardNo(Long boardNo) {
		this.boardNo = boardNo;
	}
	public String getOriginalName() {
		return originalName;
	}
	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}
	public String getSaveName() {
		return saveName;
	}
	public void setSaveName(String saveName) {
		this.saveName = saveName;
	}
	
	public String getExtName() {
		return extName;
	}
	public void setExtName(String extName) {
		this.extName = extName;
	}
	public Long getFileSize() {
		return fileSize;
	}
	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}
	@Override
	public String toString() {
		return "UploadFileVo [no=" + no + ", boardNo=" + boardNo + ", originalName=" + originalName + ", saveName="
				+ saveName + ", extName=" + extName + ", fileSize=" + fileSize + "]";
	}
	
	

}
