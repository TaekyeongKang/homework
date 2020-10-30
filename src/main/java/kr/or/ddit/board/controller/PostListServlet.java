package kr.or.ddit.board.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.or.ddit.board.service.BoardService;
import kr.or.ddit.board.service.BoardServiceI;



@WebServlet("/postList")
public class PostListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final Logger logger = LoggerFactory.getLogger(PostListServlet.class);
	
	BoardServiceI boardService;
	
	@Override
	public void init() throws ServletException {
		boardService = new BoardService();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		
		// 파라미터값 가져오기
		// 페이지 번호
		String page_str = request.getParameter("page");
		int page = page_str == null ? 1 : Integer.parseInt(page_str);
		request.setAttribute("page", page);
		
		// 페이지 사이즈
		String pageSize_str = request.getParameter("pageSize");
		int pageSize = pageSize_str == null ? 10 : Integer.parseInt(pageSize_str);
		request.setAttribute("pageSize", pageSize);

		// 게시판 번호
		int board_seq = Integer.parseInt(request.getParameter("board_seq"));
		logger.debug("postList board_seq : {}" , board_seq);
		
		// 파라미터 맵객체에 넣어서 보내기
		Map<String, Integer> postMap = new HashMap<>();
		
		postMap.put("page", page);
		postMap.put("pageSize", pageSize);
		postMap.put("board_seq", board_seq);
		
	
		
		// postMap 을 인자로, 결과(postList, 총 페이지수 )를 map으로 
		Map<String, Object> map = boardService.selectBoardPageList(postMap); 
	
		// 게시판 이름 가져오기
		request.setAttribute("board_name", map.get("board_name"));
		request.setAttribute("board_seq", board_seq);
		request.setAttribute("postList", map.get("postList"));
		request.setAttribute("pages", map.get("pages"));
		
		
		// 목록화면 
		request.getRequestDispatcher("/board/postList.jsp").forward(request, response);
		
		logger.debug("board_name :{} , board_seq : {}, pages : {}", map.get("board_name"), board_seq, map.get("pages"));
		logger.debug("postList : {}",map.get("postList") );
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
