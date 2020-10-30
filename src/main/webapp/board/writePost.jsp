<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

<%@ include file="/layout/commonLib.jsp" %>

<!-- include summernote css/js -->
<link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.js"></script>
<script >
	$(document).ready(function() {
	  	$('#summernote').summernote();
		i = 0;
	  	fileCount = 0;
	  	$('#atchBtn').on('click',function(){
		  	fileCount += (i-$(".note-editable img").length);
		  	if(i>=5){
				alert("파일이 5개를 초과하였습니다.")
				return;
			} else{
				i++;
				$('#postfrm').append("<input type='file' name='atch_file"+i+"'>");
				console.log("fileCount : " + i);
			}
		})
		$('#postBtn').on("click",function(){
			$('#postfrm').append("<input type='hidden' name='fileSize' value="+i+">");
			$('#postfrm').submit()
		})


	  	// 게시글내 사진 첨부 - 첨부파일의 1개로 카운트 안함,,,,,, 내용 날림
// 		$('#postBtn').on('click', function(){
// 			atch_uploadName = [];
// 			atch_realPath = [];
// 			console.log("개수 : " + $(".note-editable img").length);
// 			if($(".note-editable img").length>5){
// 				alert("이미지/첨부파일이 5개를 초과하였습니다.");
// 			} 

// 			else{
// 				for(i =0; i < $(".note-editable img").length; i++){
// 					console.log("파일이름 : "+$(".note-editable img")[i].dataset.filename);
// 					atch_realPath.push($(".note-editable img")[i].src);
// 					atch_uploadName.push($(".note-editable img")[i].dataset.filename);
					
// 				}
// 				$('form').append("<input type='hidden' name='atch_realPath' value=" +atch_realPath+">");
// 				$('form').append("<input type='hidden' name='atch_uploadName' value=" + atch_uploadName+ ">");
// 				$('#postfrm').submit();
// 			}
// 		})



	});

	
</script>
<head>
<meta charset="UTF-8">
<title>글쓰기</title>
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
							<form id="postfrm" action="${cp }/writePost" method="POST" enctype="multipart/form-data">
								<label> 제 목 </label>&nbsp;&nbsp;&nbsp;&nbsp;
								<textarea rows="1" cols="135" name="post_title"></textarea>
								<textarea id="summernote" name="editordata"></textarea>
								<input id="atchBtn" type="button" value="첨부파일 추가 ">
								<input type="hidden" name="userid" value="${S_MEMBER.userid }">
								<button id="postBtn" type="button">등록</button>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>