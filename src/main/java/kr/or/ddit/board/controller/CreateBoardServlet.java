package kr.or.ddit.board.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.or.ddit.board.model.BoardVO;
import kr.or.ddit.board.service.BoardService;
import kr.or.ddit.board.service.BoardServiceI;

@WebServlet("/createBoard")
public class CreateBoardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private static final Logger logger = LoggerFactory.getLogger(CreateBoardServlet.class);
	
	BoardServiceI boardService;
	
	@Override
	public void init() throws ServletException {
		boardService = new BoardService();
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		
		String board_name = request.getParameter("board_name");
		logger.debug("게시판 생성 board_name : {}", board_name);
		int createCnt = boardService.createBoard(board_name);
		logger.debug("createCnt : {}", createCnt);
		
		// 생성 성공시 : 게시판관리 (manageBoard.jsp) 로 redirect
		if(createCnt == 1) {
			List<BoardVO> boardList = boardService.getBoardList();
			request.getSession().setAttribute("boardList", boardList);
			response.sendRedirect(request.getContextPath()+"/board/manageBoard.jsp");
		}
		
	}

}
