package kr.or.ddit.board.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.or.ddit.board.model.AtchVO;
import kr.or.ddit.board.model.BoardVO;
import kr.or.ddit.board.model.PostVO;
import kr.or.ddit.board.service.BoardService;
import kr.or.ddit.board.service.BoardServiceI;

@WebServlet("/deletePost")
public class DeletePostServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private static final Logger logger = LoggerFactory.getLogger(DeletePostServlet.class);
	
	BoardServiceI boardService;
	
	@Override
	public void init() throws ServletException {
		boardService = new BoardService();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 게시글 번호 받기
		int post_seq = Integer.parseInt(request.getParameter("post_seq"));
		
		PostVO postVO = boardService.getPost(post_seq);
		
		// flag_delete 값 '0'으로 세팅
		postVO.setFlag_delete(0);
		// 해당 글의 첨부파일 번호 받아오기
		List<Integer> deleteAtchSeq = boardService.getAtchSeqList(post_seq) == null ? new ArrayList<>() : boardService.getAtchSeqList(post_seq);
		// 추가된 첨부파일 : 없음
		List<AtchVO> addAtchList = new ArrayList<>();
		
		
		int deletePostCnt = boardService.modifyPost(postVO, deleteAtchSeq, addAtchList);
		
		response.sendRedirect(request.getContextPath()+"/postList?board_seq="+postVO.getBoard_seq());
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
