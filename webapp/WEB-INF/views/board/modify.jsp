<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath}/assets/css/board.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/jquery/jquery-1.9.0.js"></script>
<script type="text/javascript">
	function checkWrite(){
		
		var title=$("#title").val();
		var content=$("textarea").val();
		
		if(title==""){
			alert("제목을 써주세요.");
			$("#title").focus();
			return false;
		}
		
		if(content==""){
			alert("내용을 써주세요.");
			$("#content").focus();
			return false;
		}
		
		return true;
	}
</script>
</head>
<body>
<c:set var="vo" value="${writeView.boardVo}"/>
	<div id="container">
		<c:import url="/WEB-INF/views/include/header.jsp"/>
		<div id="content">
			<div id="board">
				<form class="board-form" method="post" action="${pageContext.request.contextPath}/board/modify" enctype="multipart/form-data">
					<input type="hidden" name="no" value="${vo.no}">
					<table class="tbl-ex">
						<tr>
							<th colspan="2">글수정</th>
						</tr>
						<tr>
							<td class="label">제목</td>
							<td><input type="text" name="title" value="${vo.title }" id="title"></td>
						</tr>
						<tr>
							<td class="label">내용</td>
							<td>
								<textarea id="content" name="content">${vo.content }</textarea>
							</td>
						</tr>
						<tr>
							<td rowspan="2">파일</td>
							<td>
								<c:if test="${not empty writeView.imageName }">
									<div>${writeView.imageName }</div>
								</c:if>
							</td>
						</tr>
						<tr>
							<td><input type="file" name="uploadFile"></td>
						</tr>
						
					</table>
					<div class="bottom">
						<a href="${pageContext.request.contextPath}/board">취소</a>
						<input type="submit" value="수정">
					</div>
				</form>				
			</div>
		</div>
		<c:import url="/WEB-INF/views/include/navigation.jsp">
			<c:param name="menu" value="board"></c:param>
		</c:import>
		<c:import url="/WEB-INF/views/include/footer.jsp"/>
	</div>
</body>
</html>