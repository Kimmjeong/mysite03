<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<%@page import="com.hanains.mysite.vo.BoardVo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% pageContext.setAttribute("enter", "\n"); %>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath}/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
<c:set var="vo" value="${writeView.boardVo}"/>
	<div id="container">
		<c:import url="/WEB-INF/views/include/header.jsp"/>
		<div id="content">
			<div id="board" class="board-form">
				<table class="tbl-ex">
					<tr>
						<th colspan="2">글보기</th>
					</tr>
					<tr>
						<td class="label">제목</td>
						<td>${vo.title }</td>
					</tr>
					<tr>
						<td class="label">내용</td>
						<td>
							<div class="view-content">${fn:replace(vo.content,enter,"<br>") }</div>
						</td>
					</tr>
					<tr>
						<td class="label">파일</td>
						<td>
							<c:if test="${not empty writeView.imageUrl }">
								<img src="${pageContext.request.contextPath }${writeView.imageUrl }" style="width:150px"><br>
							</c:if>
						</td>
					</tr>
				</table>
				<div class="bottom">
					<a href="${pageContext.request.contextPath}/board/">글목록</a>
					<c:if test='${authUser.no == vo.memberNo }'>
						<a href="${pageContext.request.contextPath }/board/modifyform/${vo.no }">글수정</a>
					</c:if>
					<c:if test='${not empty authUser }'>
						<a href="${pageContext.request.contextPath }/board/reply/${vo.no }">답글</a>
					</c:if>
				</div>
			</div>
		</div>
		<c:import url="/WEB-INF/views/include/navigation.jsp">
			<c:param name="menu" value="board"></c:param>
		</c:import>
		<c:import url="/WEB-INF/views/include/footer.jsp"/>
	</div>
</body>
</html>