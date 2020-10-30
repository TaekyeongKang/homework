package kr.or.ddit.board.controller;

import java.io.FileInputStream;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.or.ddit.board.model.AtchVO;
import kr.or.ddit.board.service.BoardService;
import kr.or.ddit.board.service.BoardServiceI;

/**
 * Servlet implementation class AtchDownloadServlet
 */
@WebServlet("/atchDownload")
public class AtchDownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private static final Logger logger = LoggerFactory.getLogger(AtchDownloadServlet.class);
	
	BoardServiceI boardService;
	
	@Override
	public void init() throws ServletException {
		boardService = new BoardService();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int atch_seq = Integer.parseInt(request.getParameter("atch_seq"));
		logger.debug("atch_seq : {}",atch_seq);
		
		AtchVO atchVO = boardService.getAtch(atch_seq);
		logger.debug("atchVO : {}", atchVO);
		
		response.setHeader("Content-Disposition", "attachment; filename=\"" + atchVO.getAtch_uploadName()+"\"");
		response.setContentType("application/octet-stream");
		 
		
		// 경로 확인 후 파일 입출력을 통해 응답 생성
		// 파일 읽기
		// 응답 생성

		FileInputStream fis = new FileInputStream(atchVO.getAtch_realPath());
		// 파일 이름(경로)로부터 파일을 읽어들일 객체
		ServletOutputStream sos = response.getOutputStream();

		// 바이트 배열로 읽어들인 파일을 읽어서 넣어주는 작업
		byte[] buffer = new byte[512];

		// 바이트배열에 담긴 파일 정보를 쓰기작업
		while(fis.read(buffer) != -1) {
			sos.write(buffer);
		}

		fis.close();
		sos.flush();
		sos.close();

		
	}


}
