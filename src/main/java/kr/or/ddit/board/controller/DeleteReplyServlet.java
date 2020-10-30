package kr.or.ddit.board.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.or.ddit.board.service.BoardService;
import kr.or.ddit.board.service.BoardServiceI;

@WebServlet("/deleteReply")
public class DeleteReplyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private static final Logger logger = LoggerFactory.getLogger(DeleteReplyServlet.class);
	
	BoardServiceI boardServie;
	
	@Override
	public void init() throws ServletException {
		boardServie = new BoardService();
	} 
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 삭제할 reply 번호 받기
		int reply_seq = Integer.parseInt(request.getParameter("reply_seq"));
		logger.debug("댓글 삭제 reply_seq :{}", reply_seq);
		
		// 서비스 메소드 호출
		// 댓글 삭제 xx , flag_delete 값 0으로 변경
		int deleteReplyCnt = boardServie.deleteReply(reply_seq);
		
		// 게시글 읽기 페이지로 가기위한 post_seq값 받시
		int post_seq = Integer.parseInt(request.getParameter("post_seq"));
		logger.debug("댓글 삭제 post_seq :{}", post_seq);
		
		// 댓글 정상 삭제 -> 본래 게시글 페이지로 이동
		if(deleteReplyCnt == 1) {
			response.sendRedirect(request.getContextPath()+"/postRead?post_seq="+post_seq);
		}
		
	}


}
