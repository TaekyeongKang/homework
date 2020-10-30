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

@WebServlet("/changeBoard")
public class ChangeBoardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = LoggerFactory.getLogger(CreateBoardServlet.class);
	
	BoardServiceI boardService;
	
	@Override
	public void init() throws ServletException {
		boardService = new BoardService();
	}
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		int board_seq = Integer.parseInt(request.getParameter("board_seq"));
		String board_name = request.getParameter("board_name");
		int status = Integer.parseInt(request.getParameter("status"));
		
		logger.debug("board_seq : {}, board_name : {}, status : {}", board_seq, board_name, status);
		
		BoardVO boardVO = new BoardVO();
		boardVO.setBoard_seq(board_seq);
		boardVO.setBoard_name(board_name);
		boardVO.setStatus(status);
		
		int updateCnt = boardService.changeBoard(boardVO);
		
		if(updateCnt == 1) {
			List<BoardVO> boardList = boardService.getBoardList();
			request.getSession().setAttribute("boardList", boardList);
			response.sendRedirect(request.getContextPath()+"/board/manageBoard.jsp");
		}
		
	}

}
