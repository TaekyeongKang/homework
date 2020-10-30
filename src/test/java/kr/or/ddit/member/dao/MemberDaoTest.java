package kr.or.ddit.member.dao;

import static org.junit.Assert.*;

import org.junit.Test;

import kr.or.ddit.member.model.MemberVO;

public class MemberDaoTest {

	@Test
	public void getMemberTest() {
		/***Given***/
		MemberDaoI memberDao = new MemberDao();
		String userid = "sally";
		
		/***When***/
		MemberVO memberVO = memberDao.getMember(userid);
		
		/***Then***/
		assertEquals("sallyPass", memberVO.getPass());
	}

}
