<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 관리</title>
<%@ include file="/layout/commonLib.jsp" %>
<script>
	$(function(){
		$('#createBtn').on('click', function(){
			$('#createfrm').submit();
		})

	})
</script>
</head>
<body>
	<%@ include file="/layout/header.jsp"%>

	<div class="container-fluid">
		<div class="row">
			<div class="col-sm-3 col-md-2 sidebar">
				<%@ include file="/layout/left.jsp"%>
			</div>
			<br>
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<form id="createfrm" action="${cp }/createBoard" method="POST">
					<label for="board_name" class="col-sm-2 control-label">게시판 이름</label>
					<input id="newBoardName" name="board_name" type="text">
					<select name="status">
						<option value="1">사용</option>
						<option value="0">미사용</option>
					</select> 
					<button id="createBtn" type="button" class="btn btn-default">생성</button>			
				</form>
				<br><br>
				<hr>
				<br>
				<c:if test="${not empty boardList}">
					<c:forEach items="${boardList }" var="board">
						<form id="changefrm" action="${cp }/changeBoard" method="POST">
							<input type="hidden" name="board_seq" value="${board.board_seq }">
							<label for="board_name" class="col-sm-2 control-label">게시판 이름</label>
							<input name="board_name" type="text" value="${board.board_name }">
							<select name="status">
							<c:choose>
								<c:when test="${board.status == 1 }">
									<option value="1" selected="selected">사용</option>
									<option value="0">미사용</option>
								</c:when>
								<c:otherwise>
									<option value="1" >사용</option>
									<option value="0" selected="selected">미사용</option>
								</c:otherwise>
							</c:choose>
							</select> 
					<button type="submit" class="btn btn-default">수정</button>			
					</form>
					</c:forEach>
				</c:if>
				
				
			</div>
		</div>
	</div>
</body>
</html>