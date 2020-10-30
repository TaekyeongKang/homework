package kr.or.ddit.member.service;

import static org.junit.Assert.*;

import org.junit.Test;

import kr.or.ddit.member.model.MemberVO;

public class MemberServiceTest {

	
	@Test
	public void getMemberTest() {
		/***Given***/
		MemberServiceI membaerService = new MemberService();
		String userid = "sally";

		/***When***/
		MemberVO memberVO = membaerService.getMember(userid);

		/***Then***/
		assertEquals("sallyPass", memberVO.getPass());
		
		
	}

}
