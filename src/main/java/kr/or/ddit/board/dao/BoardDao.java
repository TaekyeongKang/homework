package kr.or.ddit.board.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import db.MybatisUtil;
import kr.or.ddit.board.model.AtchVO;
import kr.or.ddit.board.model.BoardVO;
import kr.or.ddit.board.model.PostVO;
import kr.or.ddit.board.model.ReplyVO;

public class BoardDao implements BoardDaoI{

	private static final Logger logger = LoggerFactory.getLogger(BoardDao.class);
	
	@Override
	public List<BoardVO> getBoardList() {
		
		SqlSession sqlSession = MybatisUtil.getSqlSession();
		
		List<BoardVO> boardList =  sqlSession.selectList("board.getBoardList");
		logger.debug("Dao boardList : {}", boardList);
		
		sqlSession.close();
		
		return boardList;
	}

	@Override
	public int createBoard(String board_name) {
		SqlSession sqlSession = MybatisUtil.getSqlSession();
		
		int createCnt = 0;
		try {
			createCnt = sqlSession.insert("board.createBoard", board_name);
		} catch (Exception e) {
		}
		
		if(createCnt == 1) { // 한건이 삽입되었을 때 commit
			sqlSession.commit();
			
		}
		else { // 한건이 아니라면 정상적이지 않음 -> rollback
			sqlSession.rollback();
		}
		sqlSession.close();
		
		return createCnt;
	}

	@Override
	public int changeBoard(BoardVO boardVO) {
		
		SqlSession sqlSession = MybatisUtil.getSqlSession();
		
		int changeCnt = 0;
		try {
			changeCnt = sqlSession.insert("board.changeBoard", boardVO);
		} catch (Exception e) {
		}
		
		if(changeCnt == 1) { // 한건이 삽입되었을 때 commit
			sqlSession.commit();
			
		}
		else { // 한건이 아니라면 정상적이지 않음 -> rollback
			sqlSession.rollback();
		}
		sqlSession.close();
		
		return changeCnt;
	}

	@Override
	public List<PostVO> getPostList(int board_seq) {
		SqlSession sqlSession = MybatisUtil.getSqlSession();
		
		List<PostVO> postList =  sqlSession.selectList("board.getPostList", board_seq);
		logger.debug("Dao postList : {}", postList);
		sqlSession.close();
		
		return postList;
	}

	@Override
	public Object selectBoardPageList(SqlSession sqlSession, Map<String, Integer> postMap) {
		return sqlSession.selectList("board.selectBoardPageList", postMap);
	}

	@Override
	public int selectPostTotalCnt(SqlSession sqlSession,int board_seq) {
		return sqlSession.selectOne("board.selectPostTotalCnt",board_seq);
	}

	@Override
	public String getBoardName(SqlSession sqlSession,int board_seq) {
		return sqlSession.selectOne("board.getBoardName",board_seq);
	}

	@Override
	public int insertPost(SqlSession sqlSession, PostVO postVO) {
		logger.debug("postVO : {} ", postVO);
		sqlSession.insert("board.insertPost",postVO);
		int insertPostSeq = postVO.getPost_seq();
		return insertPostSeq;
	}

	@Override
	public int insertAtch(SqlSession sqlSession, AtchVO atchVO) {
		return sqlSession.insert("board.insertAtch",atchVO);
	}

	@Override
	public PostVO getPost(SqlSession sqlSession, int post_seq) {
		PostVO postVO = sqlSession.selectOne("board.getPost", post_seq);
		return postVO;
	}

	@Override
	public List<AtchVO> getAtchList(SqlSession sqlSession, int post_seq) {
		List<AtchVO> atchList = sqlSession.selectList("board.getAtchList", post_seq);
		return atchList;
	}

	@Override
	public AtchVO getAtch(int atch_seq) {
		SqlSession sqlSession = MybatisUtil.getSqlSession();
		AtchVO atchVO = sqlSession.selectOne("board.getAtch", atch_seq);
		sqlSession.close();
		return atchVO;
	}

	@Override
	public int modifyPost(SqlSession sqlSession, PostVO postVO) {
		return sqlSession.update("board.modifyPost", postVO);
	}

	@Override
	public int deleteAtch(SqlSession sqlSession, int i) {
		return sqlSession.delete("board.deleteAtch", i);
	}

	@Override
	public List<Integer> getAtchSeqList(int post_seq) {
		SqlSession sqlSession = MybatisUtil.getSqlSession();
		List<Integer> atchSeqList = sqlSession.selectList("board.getAtchSeqList", post_seq);
		sqlSession.close();
		return atchSeqList;
	}

	@Override
	public Object getReplyList(SqlSession sqlSession, int post_seq) {
		List<ReplyVO> replyList = sqlSession.selectList("board.getReplyList", post_seq);
		return replyList;
	}

	@Override
	public int insertReply(ReplyVO replyVO) {
		SqlSession sqlSession = MybatisUtil.getSqlSession();
		int insertReplyCnt = sqlSession.insert("board.insertReply", replyVO);
		if(insertReplyCnt == 1) {
			sqlSession.commit();
		} else {
			sqlSession.rollback();
		}
		sqlSession.close();
		return insertReplyCnt;
	}

	@Override
	public int deleteReply(int reply_seq) {
		SqlSession sqlSession = MybatisUtil.getSqlSession();
		int deleteReplyCnt = sqlSession.insert("board.deleteReply", reply_seq);
		if(deleteReplyCnt == 1) {
			sqlSession.commit();
		} else {
			sqlSession.rollback();
		}
		sqlSession.close();
		return deleteReplyCnt;
	}
		

}
