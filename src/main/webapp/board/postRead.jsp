<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>

<%@ include file="/layout/commonLib.jsp"%>

<!-- include summernote css/js -->
<link
	href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.css"
	rel="stylesheet">
<script
	src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.js"></script>
<script>
	$(document).ready(function() {
		// 수정버튼 
		$('#modifyBtn').on('click',function() {
			document.location = "/modifyPost?post_seq=${postVO.post_seq}";
		})

		// 삭제버튼
		$('#deleteBtn').on('click',function() {
			document.location = "/deletePost?post_seq=${postVO.post_seq}";
		})

		// 목록으로 버튼
		$('#backBtn').on('click',function() {
			document.location = "/postList?board_seq=${postVO.board_seq}";
		})

		// 답글달기 버튼
		$('#answerPostBtn').on('click',function() {
			document.location = "/writePost?board_seq=${postVO.board_seq}&p_post_seq=${postVO.post_seq}";
		})

		// 댓글등록 버튼
		$('#replyBtn').on('click', function() {
			var replySize = $('#reply_content').val().length;
			console.log("replySize : " + replySize);
			if (replySize > 500) {
				alert("댓글은 500자 이상 쓸 수 없습니다.")
				return;
			}
			$('#replyfrm').submit();
		})

		// 댓글 삭제 버튼
		$('.replyDeleteBtn').on('click',function() {
			reply_seq = $(this).data("reply_seq");
			document.location = "/deleteReply?reply_seq="+ reply_seq+ "&post_seq=${postVO.post_seq}";
		})
	})
</script>
<head>
<meta charset="UTF-8">
<title>글 상세조회</title>
<style>
#post_content {
	border: 1.5px solid black;
	border-radius: 0.2em;
	height: 300px;
	display: block;
}

#post_title {
	border: 1.5px solid black;
	border-radius: 0.2em;
}

.form-group {
	display: block;
}
textarea{
	display : inline;
}
#reply_content{
	width: 70%;
	height: 100px;
}
.form-group{
	display:bolock;
}
#replyBtn, #replyDeleteBtn{
	margin-left: 10px;
}
</style>
</head>
<body>
	
	<%@include file="/layout/header.jsp" %>
	
	<div class="container-fluid">
		<div class="row">

			<div class="col-sm-3 col-md-2 sidebar">
				<%@ include file="/layout/left.jsp" %>
			</div>
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
			
				<div>
					<label for="title" >제 목</label>
					<div>
						<div  id="post_title" name="post_title"  READONLY>${postVO.post_title }</div>
					</div>
				</div>
				<br>
				<div>
					<label for="content" >내 용</label>
					<div >
						<div id="post_content" name="post_content" readonly >${postVO.post_content }</div>
					</div>
				</div>
				<br>
				<div>
					<c:choose>
						<c:when test="${atchList.size() > 0 }">
						<label for="atch">첨부파일</label>
							<div >
								<c:forEach items="${atchList }" var="atch">
									<a href="${cp }/atchDownload?atch_seq=${atch.atch_seq}">${atch.atch_uploadName }</a>
									<br>
								</c:forEach>
							</div>
						</c:when>
					</c:choose>
				</div>
				<br><br>
				<div>
					<label for="atch" >댓글</label>
					<div >
						<div>
						<c:if test="${not empty replyList }">
							<table >
							<c:forEach items="${replyList }" var="reply">
								<c:choose>
									<c:when test="${reply.flag_delete == 1}">
										<tr>
											<td>${reply.reply_content}&nbsp;&nbsp;&nbsp;&nbsp;[${reply.userid } / <fmt:formatDate
												value="${reply.reply_date }" pattern="yyyy-MM-dd" />]
											</td>
											<c:if test="${reply.userid == S_MEMBER.userid }">
												<td><input data-reply_seq="${reply.reply_seq }" class="replyDeleteBtn"
														   type="button" value="삭제"></td>
											</c:if>
										</tr>
									</c:when>
									<c:when test="${reply.flag_delete == 0}">
										<tr>
											<td>[ 삭제된 댓글 입니다. ]</td>
										</tr>
									</c:when>
								</c:choose>
							</c:forEach>
							</table>
						</c:if>
						</div>
					</div>
				</div>
				<hr>
				<div>
					<label for="atch" ></label>
					<form id="replyfrm" action="${cp }/writeReply" method="POST">
						&nbsp;&nbsp;
						<input type="text" id="reply_content" cols="120" rows="3" name="reply_content" >
						<input id="replyBtn" type="button" value="댓글 등록">
						<input type="hidden" name="userid" value="${S_MEMBER.userid }">
						<input type="hidden" name="post_seq" value="${postVO.post_seq}">
					</form>
				</div>
				<br><br>
				<div>
					<c:choose>
						<c:when test="${S_MEMBER.userid == postVO.userid}">
							<input type="button" id="modifyBtn" value="수정">
							<input type="button" id="deleteBtn" value="삭제">
						</c:when>
					</c:choose>
					<input type="button" id="answerPostBtn" value="답글쓰기"> 
					<input type="button" id="backBtn" value="목록으로">
				</div>
			</div>
		</div>
	</div>
</body>
</html>