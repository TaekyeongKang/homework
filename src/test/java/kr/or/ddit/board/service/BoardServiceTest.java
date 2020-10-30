package kr.or.ddit.board.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import kr.or.ddit.board.model.AtchVO;
import kr.or.ddit.board.model.BoardVO;
import kr.or.ddit.board.model.PostVO;
import kr.or.ddit.board.model.ReplyVO;


public class BoardServiceTest {

	BoardServiceI boardService;
	
	@Before
	public void setup() {
		boardService = new BoardService();
	}
	
	
	@Test
	public void getBoardListTest() {
		/***Given***/
		
		/***When***/
		List<BoardVO> boardList = boardService.getBoardList();
		
		/***Then***/
		assertEquals(3, boardList.size());
	}
	
	@Test
	public void createBoardTest() {
		/***Given***/
		String board_name = "테스트 게시판";
		
		/***When***/
		int createBoardCnt = boardService.createBoard(board_name);
		
		/***Then***/
		assertEquals(1, createBoardCnt);
	}
	
	@Test
	public void changeBoardTest() {
		/***Given***/
		BoardVO boardVO = new BoardVO();
		boardVO.setBoard_seq(24);
		boardVO.setBoard_name("게시판 수정 테스트");
		boardVO.setStatus(0);
		
		/***When***/
		int changeBoardCnt = boardService.changeBoard(boardVO);
		
		/***Then***/
		assertEquals(1, changeBoardCnt);
	}
	
	@Test
	public void getPostListTest() {
		/***Given***/
		int board_seq = 1;
		
		/***When***/
		List<PostVO> postList = boardService.getPostList(board_seq);
		
		/***Then***/
		assertEquals(12, postList.size());
	}
	
	@Test
	public void selectBoardPageListTest() {
		/***Given***/
		int page = 1;
		int pageSize = 10;
		int board_seq = 1;
		
		Map<String, Integer> postMap = new HashMap<>();
		postMap.put("page",page);
		postMap.put("pageSize",pageSize);
		postMap.put("board_seq",board_seq);
		
		/***When***/
		Map<String, Object> resultMap = boardService.selectBoardPageList(postMap);
		List<PostVO> postList = (List<PostVO>)resultMap.get("postList");
		
		/***Then***/
		assertEquals(pageSize, postList.size());
		assertEquals(2, resultMap.get("pages"));
		assertEquals("자유게시판", resultMap.get("board_name"));
	}
	
	@Test
	public void getBoardNameTest() {
		/***Given***/
		int board_seq = 1;
		
		/***When***/
		String board_name = boardService.getBoardName(board_seq);
		
		/***Then***/
		assertEquals("자유게시판", board_name);
	}
	
	@Test
	public void insertPostTest() {
		/***Given***/
		PostVO postVO = new PostVO();
		postVO.setBoard_seq(2);
		postVO.setUserid("sally");
		postVO.setPost_title("boardService test code");
		postVO.setPost_content("boardService test code");
		
		AtchVO atchVO = new AtchVO();
		atchVO.setAtch_uploadName("cony.png");
		atchVO.setAtch_realPath("D:\\atchFiles\\cony.png");
		List<AtchVO> atchList = new ArrayList<>();
		atchList.add(atchVO);
		
		/***When***/
		int insertPostCnt = boardService.insertPost(postVO, atchList);
		
		/***Then***/
		assertEquals(1, insertPostCnt);
	}
	
	@Test
	public void readPostTest() {
		/***Given***/
		int post_seq = 6;
		
		/***When***/
		Map<String, Object> resultMap = boardService.readPost(post_seq);
		PostVO resultPost = (PostVO)resultMap.get("postVO");
		List<AtchVO> resultAtchList = (List<AtchVO>) resultMap.get("atchList");
		List<ReplyVO> resultReplyList = (List<ReplyVO>) resultMap.get("replyList"); 
		
		/***Then***/
		assertEquals(post_seq, resultPost.getPost_seq());
		assertEquals(2, resultAtchList.size());
		assertEquals(3, resultReplyList.size());
	}
	
	@Test
	public void getAtchTest() {
		/***Given***/
		int atch_seq = 1;
		
		/***When***/
		AtchVO atchVO = boardService.getAtch(atch_seq);
		
		/***Then***/
		assertEquals(1, atchVO.getPost_seq());
	}
	
	@Test
	public void getPostTest() {
		/***Given***/
		int post_seq = 6;
		
		/***When***/
		PostVO postVO = boardService.getPost(post_seq);
		
		/***Then***/
		assertEquals(1, postVO.getBoard_seq());
	}
	
	@Test
	public void modifyPostTest() {
		/***Given***/
		PostVO postVO = boardService.getPost(1);
		postVO.setPost_content("modify service test code");
		
		List<Integer> deleteAtchSeq = new ArrayList<>();
		
		List<AtchVO> addAtchList =  new ArrayList<>();
		
		/***When***/
		int modifyPostCnt = boardService.modifyPost(postVO, deleteAtchSeq, addAtchList);
		
		/***Then***/
		assertEquals(1, modifyPostCnt);
	}

	@Test
	public void getAtchSeqListTest() {
		/***Given***/
		int post_seq = 6;
		
		/***When***/
		List<Integer> atchSeqList = boardService.getAtchSeqList(post_seq);
		
		/***Then***/
		assertEquals(2, atchSeqList.size());
	}
	
	@Test
	public void insertReplyTest() {
		/***Given***/
		ReplyVO replyVO = new ReplyVO();
		replyVO.setPost_seq(1);
		replyVO.setReply_content("insertReply service test code");
		replyVO.setUserid("sallly");
		
		/***When***/
		int insertReplyCnt = boardService.insertReply(replyVO);
		
		/***Then***/
		assertEquals(1, insertReplyCnt);
	}
	
	@Test
	public void deleteReplyTest() {
		/***Given***/
		int reply_seq = 12;
		
		/***When***/
		int deleteReplyCnt = boardService.deleteReply(reply_seq);
		
		/***Then***/
		assertEquals(1, deleteReplyCnt);
	}
	
}
