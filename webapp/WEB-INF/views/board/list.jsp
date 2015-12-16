<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath }/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/include/header.jsp"/>
		<div id="content">
			<div id="board">
				<form id="search_form" action="${pageContext.request.contextPath }/board" method="get">
					<input type="text" id="kwd" name="kwd" value="${listData.kwd }">
					<input type="submit" value="찾기">
				</form>
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>				
				
				<c:set var="totalCount" value="${listData.totalCount}"/>  <%-- 게시글 갯수 --%>
				
				<c:forEach items="${listData.list }" var="list" varStatus="i">
						<tr>
							<td>${listData.totalCount-(listData.pageSize*(listData.nowPage-1))-i.index}</td> <!-- 글번호 -->
							<td class="title" style="padding-left:${( list.depth )*10 }px">
								<c:if test="${list.depth > 0 }">
									<img src="${pageContext.request.contextPath }/assets/images/ico-reply.gif">
								</c:if> <a href="${pageContext.request.contextPath }/board/view/${list.no }">${list.title }</a>
							</td>
							<td>${list.memberName }</td>
							<td>${list.viewCnt }</td>
							<td>${list.regDate }</td>
							<td><c:choose>
									<c:when test='${authUser.no == list.memberNo }'>
										<a
											href="${pageContext.request.contextPath }/board/delete/${list.no }"
											class="del">삭제</a>
									</c:when>
									<c:otherwise>
									  &nbsp;
								    </c:otherwise>
								</c:choose>
							</td>
						</tr>
						
				</c:forEach>
				
				</table>
				<div class="pager">
					<ul>
						
						<c:set var="pageNum" value="${listData.pageNum }"/> <%-- 페이지바 시작 번호 --%>
						
						<c:if test="${pageNum!=1}">
								<li class="pg-prev"><a href="${pageContext.request.contextPath }/board?p=${pageNum-1}&kwd=${listData.kwd }">◀ 이전</a></li>
						</c:if>
						
						<c:forEach begin="1" end="${listData.totalPage}" var="i">
							<c:if test="${!(i > listData.blockSize || pageNum > listData.totalPage) }">
								<c:choose> 
									<c:when test="${pageNum!=listData.nowPage }" > <%-- 현재 페이지가 아닐 경우 링크걸기 --%>
										<li><a href="${pageContext.request.contextPath }/board?p=${pageNum}&kwd=${listData.kwd }">${pageNum}</a></li>
									</c:when>
									<c:otherwise> <%-- 현재 페이지이면 색 주기 --%>
										<li><font color="red">${pageNum}</font></li>
									</c:otherwise>
								</c:choose>
								<c:set var="pageNum" value="${pageNum+1}"/>
							</c:if>
						</c:forEach>
						
						<c:if test="${pageNum <= listData.totalPage}">
								<li class="pg-next"><a href="${pageContext.request.contextPath }/board?p=${pageNum}&kwd=${listData.kwd }">다음 ▶</a></li>
						</c:if>
						
					</ul>	
				</div>
				<div class="bottom">
					<a href="${pageContext.request.contextPath }/board/writeform" id="new-book">글쓰기</a>
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