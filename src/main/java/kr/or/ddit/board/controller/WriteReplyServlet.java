package kr.or.ddit.board.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.omg.PortableServer.REQUEST_PROCESSING_POLICY_ID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.or.ddit.board.model.ReplyVO;
import kr.or.ddit.board.service.BoardService;
import kr.or.ddit.board.service.BoardServiceI;

@WebServlet("/writeReply")
public class WriteReplyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final Logger logger = LoggerFactory.getLogger(WriteReplyServlet.class);
	
	BoardServiceI boardService;
	
	@Override
	public void init() throws ServletException {
		boardService = new BoardService();
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		request.setCharacterEncoding("utf-8");
		
		// userid 가져오기
		String userid = request.getParameter("userid");
		logger.debug("댓글 userid : {}" , userid);
		
		// 원글번호 가져오기
		int post_seq = Integer.parseInt(request.getParameter("post_seq"));
		logger.debug("댓글 post_seq : {}" , post_seq);
		
		// 댓글내용 가져오기
		String reply_content = request.getParameter("reply_content");
		logger.debug("댓글 reply_content : {}" , reply_content);
		
		// replyVO 세팅
		ReplyVO replyVO = new ReplyVO();
		replyVO.setPost_seq(post_seq);
		replyVO.setReply_content(reply_content);
		replyVO.setUserid(userid);
		
		// insert 메서드 호출
		int insertReplyCnt = boardService.insertReply(replyVO);
		
		if(insertReplyCnt==1) {
			response.sendRedirect(request.getContextPath()+"/postRead?post_seq="+post_seq);
		}
	}

}
