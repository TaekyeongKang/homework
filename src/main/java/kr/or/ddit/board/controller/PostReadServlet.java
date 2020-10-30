package kr.or.ddit.board.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.or.ddit.board.model.PostVO;
import kr.or.ddit.board.service.BoardService;
import kr.or.ddit.board.service.BoardServiceI;

/**
 * Servlet implementation class PostReadServlet
 */
@WebServlet("/postRead")
public class PostReadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = LoggerFactory.getLogger(PostReadServlet.class);
	
	BoardServiceI boardService;
	
	@Override
	public void init() throws ServletException {
		boardService = new BoardService();
	}
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		int post_seq = Integer.parseInt(request.getParameter("post_seq"));
		logger.debug("글읽기 post_seq : {}", post_seq);
		
		Map<String, Object> map= boardService.readPost(post_seq);
		
		
		request.setAttribute("postVO", map.get("postVO"));
		logger.debug("글읽기 postVO : {}", map.get("postVO") );
		request.setAttribute("atchList", map.get("atchList"));
		logger.debug("글읽기 atchList : {}", map.get("atchList") );
		request.setAttribute("replyList", map.get("replyList"));
		logger.debug("글읽기 replyList : {}", map.get("replyList") );
		
		request.getRequestDispatcher("/board/postRead.jsp").forward(request, response);
		
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
