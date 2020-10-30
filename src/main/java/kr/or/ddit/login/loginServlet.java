package kr.or.ddit.login;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.or.ddit.board.model.BoardVO;
import kr.or.ddit.board.service.BoardService;
import kr.or.ddit.board.service.BoardServiceI;
import kr.or.ddit.member.model.MemberVO;
import kr.or.ddit.member.service.MemberService;
import kr.or.ddit.member.service.MemberServiceI;

/**
 * Servlet implementation class loginServlet
 */
@WebServlet("/login")
public class loginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(loginServlet.class);
	private MemberServiceI memberService;
	
	@Override
	public void init() throws ServletException {
		// 서비스 객체 생성
		memberService = new MemberService();
	}
	
	
	// login 화면을 클라이언트에게 응답으로 생성
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.debug("LoginServlet doGet");
		logger.debug("UNT_CD parameter : {}", request.getParameter("UNT_CD"));
		request.getRequestDispatcher("/login.jsp").forward(request, response);
	}

	// login 화면에서 사용자가 보낸 아이디,비밀번호를 사용하여 로그인 처리 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userId = request.getParameter("userId");
		String password = request.getParameter("password");
		logger.debug("userId : {}, password : {} ", userId, password);
		
		// 파라미터로 온 userId가 db상에 존재하는지 확인하고, 비밀번호가 데이터베이스에 저장된 비밀번호와 일치하는지 확인
		// select * 
		// from 회원
		// where 회원 아이디 = 파라미터로 넘어온 userId;
		// => 메소드  public MemberVO getMember(String userId)
		
		
		MemberVO memberVO = memberService.getMember(userId);
		
		
		
		
		// db에 등록되지 않은 경우 or 비밀번호가 불일치할 경우-> login 페이지 반환
		if(memberVO == null || !memberVO.getPass().equals(password)) {
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}
		// 비밀번호가 일치할 경우 ->  main 페이지로 이동
		else if(memberVO.getPass().equals(password)){
			request.getSession().setAttribute("S_MEMBER", memberVO);
			
			BoardServiceI boardService = new BoardService();
			// 게시판 목록 request에 저장
			List<BoardVO> boardList = boardService.getBoardList();
			logger.debug("boardList : {}", boardList);
			request.getSession().setAttribute("boardList", boardList);
			
			request.getRequestDispatcher("/main.jsp").forward(request, response);
		} 
			
		
	}

}
