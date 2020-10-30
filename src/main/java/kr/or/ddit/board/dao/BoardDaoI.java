package kr.or.ddit.board.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import kr.or.ddit.board.model.AtchVO;
import kr.or.ddit.board.model.BoardVO;
import kr.or.ddit.board.model.PostVO;
import kr.or.ddit.board.model.ReplyVO;

public interface BoardDaoI {

	List<BoardVO> getBoardList();

	int createBoard(String board_name);

	int changeBoard(BoardVO boardVO);

	List<PostVO> getPostList(int board_seq);

	Object selectBoardPageList(SqlSession sqlSession, Map<String, Integer> postMap);

	int selectPostTotalCnt(SqlSession sqlSession, int board_seq);

	String getBoardName(SqlSession sqlSession, int board_seq);

	int insertPost(SqlSession sqlSession, PostVO postVO);

	int insertAtch(SqlSession sqlSession, AtchVO atchVO);

	Object getPost(SqlSession sqlSession, int post_seq);

	Object getAtchList(SqlSession sqlSession, int post_seq);

	Object getReplyList(SqlSession sqlSession, int post_seq);

	AtchVO getAtch(int atch_seq);

	int modifyPost(SqlSession sqlSession, PostVO postVO);

	int deleteAtch(SqlSession sqlSession, int i);

	List<Integer> getAtchSeqList(int post_seq);

	int insertReply(ReplyVO replyVO);

	int deleteReply(int reply_seq);



}
