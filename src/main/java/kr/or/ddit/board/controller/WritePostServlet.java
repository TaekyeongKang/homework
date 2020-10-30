package kr.or.ddit.board.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fileUpload.FileUploadUtil;
import kr.or.ddit.board.model.AtchVO;
import kr.or.ddit.board.model.PostVO;
import kr.or.ddit.board.service.BoardService;
import kr.or.ddit.board.service.BoardServiceI;

@WebServlet("/writePost")
@MultipartConfig
public class WritePostServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final Logger logger = LoggerFactory.getLogger(WritePostServlet.class);
	
	BoardServiceI boardService;
	int board_seq;
	int p_post_seq;
	@Override
	public void init() throws ServletException {
		boardService = new BoardService();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 파라미터 가져오기 
		String board_seq_str = request.getParameter("board_seq");
		logger.debug("doGet board_seq_str : {}" , board_seq_str);
		board_seq = Integer.parseInt(board_seq_str);
		logger.debug("글 쓰기 board_seq : {}" , board_seq);

		// 답글일 경우 p_post_seq 값 받아오기
		p_post_seq = request.getParameter("p_post_seq") == null? 0 : Integer.parseInt(request.getParameter("p_post_seq"));
		
		// 게시판 이름 구하기
		String board_name = boardService.getBoardName(board_seq);
		
		request.setAttribute("board_name", board_name);
		
		request.getRequestDispatcher("/board/writePost.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		
		String post_title = request.getParameter("post_title");
		String post_content = request.getParameter("editordata");
		String userid = request.getParameter("userid");
		int fileSize = Integer.parseInt(request.getParameter("fileSize"));
		
		
		logger.debug("userid : {}" , userid);
		logger.debug("fileSize : {}" , fileSize);
		logger.debug("board_seq : {}, post_title : {}, post_content : {}",board_seq, post_title, post_content);
		logger.debug("p_post_seq : {}" , p_post_seq);
		
		// 파일 객체 넘길 리스트
		List<AtchVO> atchList = new ArrayList<>();
		
		// 넘어온 파일 정보 확인 (part로 넘어옴)
		for(int i = 1; i<= fileSize; i++) {
			String no = i+"";
			Part atch_file = request.getPart("atch_file"+no);
			logger.debug(atch_file.toString());
			logger.debug("atch_file : {}",atch_file.getHeader("Content-Disposition"));
			
			String atch_uploadName = FileUploadUtil.getFilename(atch_file.getHeader("Content-Disposition"));
			if(atch_uploadName != null && !atch_uploadName.equals("")) {
				String extension = FileUploadUtil.getExtension(atch_uploadName);
				logger.debug("extension : {}",extension);
				String atch_realPath = "";
				if(atch_file.getSize() > 0) {
					atch_realPath = "D:\\atchFiles\\" + UUID.randomUUID().toString() +"."+ extension;
					atch_file.write(atch_realPath);
				}
				
				// 첨부파일 등록
				AtchVO atchVO = new AtchVO();
				atchVO.setAtch_uploadName(atch_uploadName);
				atchVO.setAtch_realPath(atch_realPath);
				atchList.add(atchVO);
			}
		}

		
		
		// 게시글 등록
		PostVO postVO = new PostVO();
		postVO.setBoard_seq(board_seq);
		postVO.setPost_title(post_title);
		postVO.setPost_content(post_content);
		postVO.setUserid(userid);
		
		// 답글일 경우 = 부모 글번호가 있을 경우
		if(p_post_seq !=0) {
			postVO.setP_post_seq(p_post_seq);
		}
		logger.debug("글쓰기 postVO : {}" , postVO);
		int insertPostSeq = boardService.insertPost(postVO,atchList);

		// 1건이 입력되었을 때 : 정상 -> postList 페이지로 이동
		// 1건이 아닐 때 : 비정상 -> 사용자가 데이터를 다시 입력할 수 있도록 등록페이지로 이동
		if(insertPostSeq > 0 ) {
			//request.getRequestDispatcher("/postList").forward(request, response);
			// forward 방식으로 postList 페이지 호출 : postListServlet 의 doPost()메서드 실행하겠다.
			// postListServlet 의 doPost()메서드 에서 doGet() 메서드 호출
			// but 새로고침하면 오류 
			// redirect  방식
			
			// 상세보기 페이지로
			response.sendRedirect(request.getContextPath()+"/postRead?post_seq="+insertPostSeq);
		}
		else {
			// 사용자가 입력했던 정보를 담은 파라미터를 다시 가져가게끔 설정

			//doGet(request,response);
		}


//		String[] atch_uploadName = request.getParameterValues("atch_uploadName");
//		String[] atch_realPath = request.getParameterValues("atch_realPath");
//		logger.debug("atch_file : {}" , atch_file1);
		// 이미지 삽입 realFilename값 가져오기
		//$(".note-editable img")[1].dataset.filename
		
	}

}
