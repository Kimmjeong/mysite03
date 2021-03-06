package com.hanains.mysite.vo;

public class BoardDTO {

	private Long no;
	private String title;
	private Long memberNo;
	private String memberName;
	private Long viewCnt;
	private String regDate;
	private Long groupNo;
	private Long orderNo;
	private Long depth;

	public Long getGroupNo() {
		return groupNo;
	}
	public void setGroupNo(Long groupNo) {
		this.groupNo = groupNo;
	}
	public Long getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(Long orderNo) {
		this.orderNo = orderNo;
	}
	public Long getDepth() {
		return depth;
	}
	public void setDepth(Long depth) {
		this.depth = depth;
	}
	public Long getNo() {
		return no;
	}
	public void setNo(Long no) {
		this.no = no;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Long getMemberNo() {
		return memberNo;
	}
	public void setMemberNo(Long memberNo) {
		this.memberNo = memberNo;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public Long getViewCnt() {
		return viewCnt;
	}
	public void setViewCnt(Long viewCnt) {
		this.viewCnt = viewCnt;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	@Override
	public String toString() {
		return "BoardDTO [no=" + no + ", title=" + title + ", memberNo=" + memberNo + ", memberName=" + memberName
				+ ", viewCnt=" + viewCnt + ", regDate=" + regDate + ", groupNo=" + groupNo + ", orderNo=" + orderNo
				+ ", depth=" + depth +"]";
	}

	
	
	
}
