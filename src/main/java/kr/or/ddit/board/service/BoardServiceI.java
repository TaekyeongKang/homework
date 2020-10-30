package kr.or.ddit.board.service;

import java.util.List;
import java.util.Map;

import kr.or.ddit.board.model.AtchVO;
import kr.or.ddit.board.model.BoardVO;
import kr.or.ddit.board.model.PostVO;
import kr.or.ddit.board.model.ReplyVO;

public interface BoardServiceI {
	List<BoardVO> getBoardList();

	int createBoard(String board_name);

	int changeBoard(BoardVO boardVO);

	List<PostVO> getPostList(int board_seq);

	Map<String, Object> selectBoardPageList(Map<String, Integer> postMap);

	String getBoardName(int board_seq);

	int insertPost(PostVO postVO, List<AtchVO> atchList);

	Map<String, Object> readPost(int post_seq);

	AtchVO getAtch(int atch_seq);

	PostVO getPost(int post_seq);

	// 글 수정 메서드 ( 제목&내용 수정, 첨부파일 삭제, 첨부파일 추가)
	int modifyPost(PostVO postVO, List<Integer> deleteAtchSeq, List<AtchVO> addAtchList);

	// 첨부파일번호 리스트 뽑기
	List<Integer> getAtchSeqList(int post_seq);

	// 댓글쓰기
	int insertReply(ReplyVO replyVO);

	// 댓글 삭제
	int deleteReply(int reply_seq);


}
