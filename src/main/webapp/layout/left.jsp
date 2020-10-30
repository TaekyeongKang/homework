<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<ul class="nav nav-sidebar">
	<li class="active"><a href="${cp }/main.jsp">Main <span class="sr-only">(current)</span></a></li>
	<c:if test="${not empty boardList}">
		<c:forEach items="${boardList }" var="board">
			<c:choose>
				<c:when test="${board.status == 1 }">
					<li class="active"><a
						href="${cp}/postList?board_seq=${board.board_seq}">${board.board_name }</a></li>
 				</c:when>
			</c:choose>
		</c:forEach>
	</c:if>
	<li class="active" ><a href="${cp }/board/manageBoard.jsp">게시판 관리</a></li>

</ul>