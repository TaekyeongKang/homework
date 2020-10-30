<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>

<%@ include file="/layout/commonLib.jsp" %>

<!-- include summernote css/js -->
<link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.js"></script>
<head>
<meta charset="UTF-8">
<title>글 수정</title>
<script >
	$(document).ready(function() {
  		$('#summernote').summernote();
	
		$('.removeAtch').on('click',function(){
			atch_seq = $(this).data("atch_seq");
			$('#atch'+atch_seq).remove();
			$(this).remove();
			$('#modifyfrm').append("<input type='hidden' name='atch_seq' value='"+atch_seq+"'>");
		})


		var i = 0;
	  	$('#atchBtn').on('click',function(){
		  	fileCount = ($('#modifyfrm>a').length+ i);
		  	if(fileCount>=5){
				alert("파일이 5개를 초과하였습니다.")
				return;
			} else{
				i++;
				$('#modifyfrm').append("<input type='file' name='atch_file"+i+"'>");
				console.log("i : " + i);
				console.log("fileCount : " + fileCount);
			}
		})
		
		$('#modifyBtn').on('click', function(){
			console.log("전송 i : " + i);
			console.log(${postVO.post_seq})
			$('#modifyfrm').append("<input type='hidden' name='addfileSize' value='"+i+"'>");
			$('#modifyfrm').submit();
		})
	})
</script>
</head>
<body>
	
<%@ include file="/layout/header.jsp" %>

	<div class="container-fluid">
		<div class="row">

			<div class="col-sm-3 col-md-2 sidebar">
				<%@ include file="/layout/left.jsp"%>
			</div>
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<h2>${board_name }</h2>
				<hr>
				<div class="row">
					<div class="col-sm-8 blog-main">
						<div class="blog-post">
							<form id="modifyfrm" action="${cp }/modifyPost" method="POST" enctype="multipart/form-data">
								<label> 제 목 </label>&nbsp;&nbsp;&nbsp;&nbsp;
								<textarea rows="1" cols="135" name="post_title">${postVO.post_title }</textarea>
								<textarea id="summernote" name="editordata">${postVO.post_content }</textarea>
								<c:choose>
									<c:when test="${atchList.size() > 0 }">
										<c:forEach items="${atchList }" var="atch">
											<a id="atch${atch.atch_seq  }"href="${cp }/atchDownload?atch_seq=${atch.atch_seq}"> ${atch.atch_uploadName }</a>
											<input type="button" data-atch_seq ="${atch.atch_seq }" value="X" class="removeAtch"><br>
										</c:forEach>
									</c:when>
								</c:choose>
								<br>
								<input type="hidden" name="post_seq" value=${postVO.post_seq}>
								<input type="button" id="atchBtn" value="첨부파일 추가"> 
								<input type="button" id="modifyBtn" value="등록">
								<input type="button" id="backBtn" value="목록으로">
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>