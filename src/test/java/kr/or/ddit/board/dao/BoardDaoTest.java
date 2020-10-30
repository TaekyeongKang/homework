package kr.or.ddit.board.dao;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.junit.Before;
import org.junit.Test;

import db.MybatisUtil;
import kr.or.ddit.board.model.AtchVO;
import kr.or.ddit.board.model.BoardVO;
import kr.or.ddit.board.model.PostVO;
import kr.or.ddit.board.model.ReplyVO;

public class BoardDaoTest {

	BoardDaoI boardDao;
	
	@Before
	public void setup() {
		boardDao = new BoardDao();
	}
		
	@Test
	public void getBoardListTest() {
		/***Given***/

		/***When***/
		List<BoardVO> boardList = boardDao.getBoardList();
		
		/***Then***/
		assertEquals(3, boardList.size());
		
	}
	
	@Test
	public void createBoardTest() {
		/***Given***/
		String board_name = "공지게시판";
		
		/***When***/
		int createCnt = boardDao.createBoard(board_name);
		
		/***Then***/
		assertEquals(1, createCnt);
		
	}
	
	
	@Test
	public void changeBoardTest() {
		/***Given***/
		List<BoardVO> boardList = boardDao.getBoardList();
		
		/***When***/
		BoardVO boardVO = boardList.get(1);
		boardVO.setStatus(0);
		int changeCnt = boardDao.changeBoard(boardVO);
		
		/***Then***/
		assertEquals(1, changeCnt);
		
	}
	
	@Test
	public void getPostListTest() {
		/***Given***/
		int board_seq = 1;
		
		/***When***/
		List<PostVO> postList = boardDao.getPostList(board_seq);
		
		/***Then***/
		assertEquals(12, postList.size());
		
	}

	@Test
	public void selectBoardPageListTest() {
		/***Given***/
		SqlSession sqlSession = MybatisUtil.getSqlSession();
		int page = 1;
		int pageSize = 10;
		int board_seq = 1;
		
		Map<String, Integer> postMap = new HashMap<>();
		postMap.put("page",page);
		postMap.put("pageSize",pageSize);
		postMap.put("board_seq",board_seq);
		
		/***When***/
		List<PostVO> postList =(List<PostVO>) boardDao.selectBoardPageList(sqlSession, postMap);
		sqlSession.close();

		
		/***Then***/
		assertEquals(pageSize, postList.size());
		
	}
	
	@Test
	public void selectPostTotalCntTest() {
		/***Given***/
		SqlSession sqlSession = MybatisUtil.getSqlSession();
		int board_seq = 1;
		
		/***When***/
		int postTotalCnt = boardDao.selectPostTotalCnt(sqlSession, board_seq);
		sqlSession.close();

		/***Then***/
		assertEquals(12, postTotalCnt);
		
	}
	
	@Test
	public void getBoardNameTest() {
		/***Given***/
		SqlSession sqlSession = MybatisUtil.getSqlSession();
		int board_seq = 1;
		
		/***When***/
		String board_name = boardDao.getBoardName(sqlSession, board_seq);
		sqlSession.close();
		
		/***Then***/
		assertEquals("자유게시판", board_name);
		
		
	}
	
	@Test
	public void insertPostTest() {
		/***Given***/
		SqlSession sqlSession = MybatisUtil.getSqlSession();
		
		PostVO postVO = new PostVO();
		postVO.setBoard_seq(2);
		postVO.setPost_title("test code");
		postVO.setPost_content("test code");
		postVO.setUserid("sally");
		
		/***When***/
		int insertCnt = boardDao.insertPost(sqlSession, postVO);
		if(insertCnt == 1) {
			sqlSession.commit();
		}
		sqlSession.close();
		
		/***Then***/
		assertEquals(1, insertCnt);
		
	}
	
	@Test
	public void insertAtchTest() {
		/***Given***/
		SqlSession sqlSession = MybatisUtil.getSqlSession();
		
		AtchVO atchVO = new AtchVO();
		atchVO.setAtch_realPath("d://atchFiles//cony.png");
		atchVO.setAtch_uploadName("cony.png");
		atchVO.setPost_seq(1);
		/***When***/
		int insertAtchCnt = boardDao.insertAtch(sqlSession, atchVO);
		if(insertAtchCnt == 1) {
			sqlSession.commit();
		}
		sqlSession.close();
		
		/***Then***/
		assertEquals(1, insertAtchCnt);
		
	}
	
	@Test
	public void getPostTest() {
		/***Given***/
		SqlSession sqlSession = MybatisUtil.getSqlSession();
		int post_seq = 1;
				
		/***When***/
		PostVO postVO = (PostVO)boardDao.getPost(sqlSession, post_seq);
		sqlSession.close();
		
		/***Then***/
		assertEquals("brown", postVO.getUserid());
		
	}
	
	@Test
	public void getAtchListTest() {
		/***Given***/
		SqlSession sqlSession = MybatisUtil.getSqlSession();
		int post_seq = 6;
				
		/***When***/
		List<AtchVO> atchList = (List<AtchVO>)boardDao.getAtchList(sqlSession, post_seq);
		sqlSession.close();
		
		/***Then***/
		assertEquals(2, atchList.size());
		
	}
	
	@Test
	public void getAtchTest() {
		/***Given***/
		int atch_seq = 12;
				
		/***When***/
		AtchVO atchVO = boardDao.getAtch(atch_seq);
		
		/***Then***/
		assertEquals(6, atchVO.getPost_seq());
		
	}
	
	@Test
	public void modifyPostTest() {
		/***Given***/
		SqlSession sqlSession = MybatisUtil.getSqlSession();
		PostVO postVO = (PostVO)boardDao.getPost(sqlSession, 1);
		postVO.setPost_content("modify test code");
				
		/***When***/
		int modifyCnt = boardDao.modifyPost(sqlSession, postVO);
		if(modifyCnt==1) {
			sqlSession.commit();
		}else {
			sqlSession.rollback();
		}
		sqlSession.close();
		/***Then***/
		assertEquals(1, modifyCnt);
		
	}
	
	@Test
	public void deleteAtchTest() {
		/***Given***/
		SqlSession sqlSession = MybatisUtil.getSqlSession();
		int atch_seq = 10;
				
		/***When***/
		int deleteAtchCnt = boardDao.deleteAtch(sqlSession, atch_seq);
		if(deleteAtchCnt==1) {
			sqlSession.commit();
		}else {
			sqlSession.rollback();
		}
		sqlSession.close();
		/***Then***/
		assertEquals(1, deleteAtchCnt);
		
	}
	
	@Test
	public void getAtchSeqListTest() {
		/***Given***/
		int post_seq = 6;
				
		/***When***/
		List<Integer> atchSeqList = boardDao.getAtchSeqList(post_seq);
		
		/***Then***/
		assertEquals(2, atchSeqList.size());
		
	}
	
	@Test
	public void getReplyListTest() {
		/***Given***/
		SqlSession sqlSession = MybatisUtil.getSqlSession();
		int post_seq = 6;
				
		/***When***/
		List<ReplyVO> replyList = (List<ReplyVO>)boardDao.getReplyList(sqlSession, post_seq);
		sqlSession.close();
		/***Then***/
		assertEquals(3, replyList.size());
		
	}
	
	@Test
	public void insertReplyTest() {
		/***Given***/
		ReplyVO replyVO = new ReplyVO();
		replyVO.setPost_seq(1);
		replyVO.setReply_content("insertReply test code");
		replyVO.setUserid("sally");
		
		/***When***/
		int insertReplyCnt = boardDao.insertReply(replyVO);
		
		/***Then***/
		assertEquals(1, insertReplyCnt);
		
	}
	
	@Test
	public void deleteReplyTest() {
		/***Given***/
		int reply_seq = 11;
		
		/***When***/
		int deleteReplyCnt = boardDao.deleteReply(reply_seq);
		
		/***Then***/
		assertEquals(1, deleteReplyCnt);
		
	}
	
}
