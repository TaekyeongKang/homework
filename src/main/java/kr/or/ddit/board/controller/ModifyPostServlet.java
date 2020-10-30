package kr.or.ddit.board.controller;

import java.awt.PrintGraphics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import oracle.net.aso.a;

/**
 * Servlet implementation class ModifyPostServlet
 */
@WebServlet("/modifyPost")
@MultipartConfig
public class ModifyPostServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = LoggerFactory.getLogger(ModifyPostServlet.class);
	
	BoardServiceI boardService;
	
	@Override
	public void init() throws ServletException {
		boardService = new BoardService();
	}
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int post_seq = Integer.parseInt(request.getParameter("post_seq"));
		
		Map<String, Object> map= boardService.readPost(post_seq);
		
		request.setAttribute("postVO", map.get("postVO"));
		logger.debug("postVO : {}", map.get("postVO") );
		request.setAttribute("atchList", map.get("atchList"));
		logger.debug("atchList : {}", map.get("atchList") );
		
		
		request.getRequestDispatcher("/board/modifyPost.jsp").forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		
		// 수정하는 글 번호
		int post_seq = Integer.parseInt(request.getParameter("post_seq"));
		logger.debug("글 수정 post_seq : {}", post_seq);
		
		// 수정한 글 제목
		String post_title = request.getParameter("post_title");
		// 수정한 글 내용
		String post_content = request.getParameter("editordata");
		
		// 글 수정사항 반영
		PostVO postVO = boardService.getPost(post_seq);
		postVO.setPost_title(post_title);
		postVO.setPost_content(post_content);
		postVO.setFlag_delete(postVO.getFlag_delete());
		
		
		// 삭제한 첨부파일 번호배열
		String[] deleteAtchSeq_str =  request.getParameterValues("atch_seq") == null ? new String[0] : request.getParameterValues("atch_seq");
		
		List<Integer> deleteAtchSeq = new ArrayList<>();
		for(int i=0; i< deleteAtchSeq_str.length; i++) {
			deleteAtchSeq.add(Integer.parseInt(deleteAtchSeq_str[i]));
			logger.debug("deleteAtchSeq : {}" ,deleteAtchSeq.get(i));
		}
		
		// 추가된 첨부파일 사이즈
		int addfileSize = Integer.parseInt(request.getParameter("addfileSize"));
		logger.debug("addfileSize : {}" , addfileSize);
		
		// 추가한 첨부파일 배열
		List<AtchVO> addAtchList = new ArrayList<AtchVO>();
		
			
			for(int i = 0; i<addfileSize; i++) {
				String no = (i+1)+"";
				Part atch_file = request.getPart("atch_file"+no);
				logger.debug("atch_file1 : {}",atch_file.getHeader("Content-Disposition"));
				String atch_uploadName = FileUploadUtil.getFilename(atch_file.getHeader("Content-Disposition"));
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
				atchVO.setPost_seq(post_seq);
				addAtchList.add(atchVO);
			}
		
		
		int modifyTotalCnt = boardService.modifyPost(postVO,deleteAtchSeq,addAtchList);
		logger.debug("수정 servlet modifyTotalCnt : {}" , modifyTotalCnt);
		logger.debug("수정 servlet deleteAtchSeq.size() : {}" , deleteAtchSeq.size());
		logger.debug("수정 servlet addfileSize : {}" , addfileSize);
		if(modifyTotalCnt == deleteAtchSeq.size()+addfileSize +1) {
			response.sendRedirect(request.getContextPath()+"/postRead?post_seq="+post_seq);
		}
		
	}

}
