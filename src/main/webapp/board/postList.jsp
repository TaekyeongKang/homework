<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${board_name }</title>

<%@ include file="/layout/commonLib.jsp" %>

<script type="text/javascript">
	$(document).ready(function(){
		$('#postList tr').on('click', function() {
			// data-post_seq
			var post_seq = $(this).data("post_seq");
			if($(this).data("flag_delete") == 1){
				document.location = "/postRead?post_seq=" + post_seq;
			}
			if($(this).data("flag_delete") == 0){
			}
		})
	})
</script>

</head>
<body>
	<%@ include file="/layout/header.jsp" %>
	
	<div class="container-fluid">
		<div class="row">
			
<div class="col-sm-3 col-md-2 sidebar">
	<%@ include file="/layout/left.jsp" %>
</div><div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">

<div class="row">
	<div class="col-sm-8 blog-main">
		<h2 class="sub-header">${board_name }</h2>
		<div class="table-responsive">
			<table class="table table-striped">
				<tr>
					<th>글 번호</th>
					<th>제   목</th>
					<th>작 성 자</th>
					<th>작성일시</th>
				</tr>
				
			 <tbody id="postList">	<!-- <tbody> : 테이블의 내용만 crud 하고 싶을 때 분리할 수 있는 간단한 방법 -->
					<c:forEach items="${postList }" var="post">
						<c:choose>
						<c:when test="${post.flag_delete == 0 }">
							<tr data-post_seq="${post.post_seq }" data-flag_delete="0">
								<td>${post.post_seq }</td>
								<td>&nbsp;&nbsp;&nbsp;[삭제된 게시글 입니다]</td>
								<td></td>
								<td></td>
							<!-- format : yyyy-MM-dd -->
							</tr>
						</c:when>
						<c:when test="${post.flag_delete == 1 }">
							<tr data-post_seq="${post.post_seq }"  data-flag_delete="1">
								<td>${post.post_seq }</td>
								<td><c:forEach var="i" begin="1" end="${post.level}" >&nbsp;&nbsp;</c:forEach>
									<c:if test="${post.level ne 1 }">└─</c:if>
								    ${post.post_title }
								</td>
								<td>${post.userid }</td>
								<td><fmt:formatDate value="${post.post_date }" pattern="yyyy-MM-dd"/></td>
							<!-- format : yyyy-MM-dd -->
							</tr>
						</c:when>
						</c:choose>
					</c:forEach>
				</tbody>
			</table>
		</div>
			<a href="${cp }/writePost?board_seq=${board_seq }" class="btn btn-default pull-right">글 쓰 기</a>
		<div class="text-center">
			<ul class="pagination">
				<li>
				<c:choose>
					<c:when test="${page == 1 }">
						<span>&lt;&lt;</span>	
					</c:when>
					<c:when test="${page > 1 }">
						<a href="${cp }/postList?page=1&board_seq=${board_seq}">&lt;&lt;</a>
					</c:when>
				</c:choose>
				</li>
				<li>
				<c:choose>
					<c:when test="${page == 1 }">
						<span>&lt;</span>	
					</c:when>
					<c:when test="${page > 1}">
						<a href="${cp }/postList?page=${page - 1 }&board_seq=${board_seq}">&lt;</a>
					</c:when>
				</c:choose>
				</li>
				<c:forEach var="i" begin="1" end="${pages }">
					<c:choose>
						<c:when test="${page == i }">
							<li class="active"><span>${i }</span></li>
						</c:when>
						<c:otherwise>
							<li><a href="${cp }/postList?page=${i }&board_seq=${board_seq}">${i }</a></li>
						</c:otherwise>
					</c:choose>
				</c:forEach>
				<li>
				<c:choose>
					<c:when test="${page == pages }">
						<span>&gt;</span>	
					</c:when>
					<c:when test="${page < pages }">
						<a href="${cp }/postList?page=${page + 1 }&board_seq=${board_seq}">&gt;</a>
					</c:when>
				</c:choose>
				</li>
				<li>
				<c:choose>
					<c:when test="${page == pages }">
						<span>&gt;&gt;</span>	
					</c:when>
					<c:when test="${page < pages }">
						<a href="${cp }/postList?page=${pages}&board_seq=${board_seq}">&gt;&gt;</a>
					</c:when>
				</c:choose>
				</li>
			</ul>
		</div>
	</div>
</div>
	</div>
		</div>
	</div>
		
</body>
</html>