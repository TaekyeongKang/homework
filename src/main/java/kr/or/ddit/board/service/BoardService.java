package kr.or.ddit.board.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import db.MybatisUtil;
import kr.or.ddit.board.dao.BoardDao;
import kr.or.ddit.board.dao.BoardDaoI;
import kr.or.ddit.board.model.AtchVO;
import kr.or.ddit.board.model.BoardVO;
import kr.or.ddit.board.model.PostVO;
import kr.or.ddit.board.model.ReplyVO;
import kr.or.ddit.login.loginServlet;

public class BoardService implements BoardServiceI{
	
	private static final Logger logger = LoggerFactory.getLogger(BoardService.class);
	
	private BoardDaoI boardDao;
	
	public BoardService() {
		boardDao = new BoardDao();
	}
	
	@Override
	public List<BoardVO> getBoardList() {
		
		List<BoardVO> boardList = boardDao.getBoardList(); 
					
		logger.debug("Service boardList : {}" , boardList);
		
		return boardList; 
	}

	@Override
	public int createBoard(String board_name) {
		int createCnt = boardDao.createBoard(board_name);
		return createCnt;
	}

	@Override
	public int changeBoard(BoardVO boardVO) {
		return boardDao.changeBoard(boardVO);
	}

	@Override
	public List<PostVO> getPostList(int board_seq) {
		return boardDao.getPostList(board_seq);
	}

	@Override
	public Map<String, Object> selectBoardPageList(Map<String, Integer> postMap) {
		
		SqlSession sqlSession = MybatisUtil.getSqlSession();
		Map<String, Object> postPageMap = new HashMap<String, Object>();
		
		// 게시글 리스트 가져오기
		postPageMap.put("postList", boardDao.selectBoardPageList(sqlSession,postMap));
		
		int board_seq = (int)postMap.get("board_seq");
		int totalCnt = boardDao.selectPostTotalCnt(sqlSession, board_seq);
		int pageSize = (int)postMap.get("pageSize");
		int pages = (int)(Math.ceil((double)totalCnt/pageSize));
		pages = pages == 0? 1 : pages;
		// 총 페이지수 가져오기
		postPageMap.put("pages", pages);
		
		// 게시판 이름 가져오기
		postPageMap.put("board_name", boardDao.getBoardName(sqlSession,board_seq));
		
		sqlSession.close();
		
		return postPageMap;
	}

	@Override
	public String getBoardName(int board_seq) {
		SqlSession sqlSession = MybatisUtil.getSqlSession(); 
		String board_name = boardDao.getBoardName(sqlSession,board_seq);
		sqlSession.close();
		return  board_name;
	}

	@Override
	public int insertPost(PostVO postVO,  List<AtchVO> atchList) {
		SqlSession sqlSession = MybatisUtil.getSqlSession(); 
		
		// 글 등록
		// 답글일 경우 
		
		int insertPostSeq = boardDao.insertPost(sqlSession,postVO);
		logger.debug("insertPostSeq : {}" , insertPostSeq);
		
		// 첨부파일목록에 글번호값 세팅
		int insertAtchTotalCnt = 0;
		for(int i =0; i<atchList.size(); i++) {
			atchList.get(i).setPost_seq(insertPostSeq);
			// 첨부파일 등록
			int insertAtchCnt = boardDao.insertAtch(sqlSession,atchList.get(i));
			if(insertAtchCnt==1) {
				insertAtchTotalCnt++;
			}
		}
		logger.debug("insertAtchTotalCnt : {}" , insertAtchTotalCnt);

		int insertPostCnt = 0;
		if(insertPostSeq>0 && insertAtchTotalCnt==atchList.size()) {
			insertPostCnt = 1;
			sqlSession.commit();
		} else {
			sqlSession.rollback();
		}
		sqlSession.close();
		
		return insertPostSeq;
	}

	@Override
	public Map<String, Object> readPost(int post_seq) {
		Map<String, Object> map = new HashMap<>();
		SqlSession sqlSession = MybatisUtil.getSqlSession(); 
		map.put("postVO", boardDao.getPost(sqlSession,post_seq));
		map.put("atchList", boardDao.getAtchList(sqlSession, post_seq));
		map.put("replyList", boardDao.getReplyList(sqlSession, post_seq));
		sqlSession.close();
		return map;
	}

	@Override
	public AtchVO getAtch(int atch_seq) {
		return boardDao.getAtch(atch_seq);
	}

	@Override
	public PostVO getPost(int post_seq) {
		SqlSession sqlSession = MybatisUtil.getSqlSession(); 
		PostVO postVO = (PostVO)boardDao.getPost(sqlSession,post_seq); 
		sqlSession.close();
		return postVO;
		
	}

	@Override
	public int modifyPost(PostVO postVO, List<Integer> deleteAtchSeq, List<AtchVO> addAtchList) {
		SqlSession sqlSession = MybatisUtil.getSqlSession(); 
		int modifyPostCnt = boardDao.modifyPost(sqlSession, postVO);
		logger.debug("글 수정 modifyPostCnt : {}", modifyPostCnt);
		int deleteAtchCnt = 0;
		for(int i = 0; i<deleteAtchSeq.size(); i++) {
			deleteAtchCnt += boardDao.deleteAtch(sqlSession, deleteAtchSeq.get(i));
		}
		logger.debug("글 수정 deleteAtchCnt : {}", deleteAtchCnt);
		int addAtchCnt =0; 
		for(int i=0; i<addAtchList.size(); i++) {
				addAtchCnt += boardDao.insertAtch(sqlSession, addAtchList.get(i));
		}
		logger.debug("글 수정 addAtchCnt : {}", addAtchCnt);
		if(modifyPostCnt==1 && deleteAtchCnt==deleteAtchSeq.size()  && addAtchCnt==addAtchList.size()) {
			sqlSession.commit();
		}else {
			sqlSession.rollback();
		}
		logger.debug("return  totalModifyCnt : {}", modifyPostCnt+deleteAtchCnt+addAtchCnt );
		return modifyPostCnt+deleteAtchCnt+addAtchCnt;
	}

	@Override
	public List<Integer> getAtchSeqList(int post_seq) {
		return boardDao.getAtchSeqList(post_seq);
	}

	@Override
	public int insertReply(ReplyVO replyVO) {
		return boardDao.insertReply(replyVO);
	}

	@Override
	public int deleteReply(int reply_seq) {
		return boardDao.deleteReply(reply_seq);
	}

}
