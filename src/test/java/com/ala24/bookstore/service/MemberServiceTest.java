package com.ala24.bookstore.service;

import com.ala24.bookstore.DataBaseCleanup;
import com.ala24.bookstore.domain.*;
import com.ala24.bookstore.domain.member.Member;
import com.ala24.bookstore.domain.orders.Order;
import com.ala24.bookstore.domain.orders.OrderItem;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class MemberServiceTest {


	private static Member memberA;
	private static Member memberB;
	private static Member duplicateMember;
	private static Order order;
	private static Delivery delivery;
	private static OrderItem orderItem;
	public static final long ZERO = 0L;
	private static Cash memberACash;
	private static Cash memberBCash;

	@Autowired
	MemberService memberService;

	@Autowired
	DataBaseCleanup dataBaseCleanup;

	@BeforeEach
	void init() {

		memberACash = Cash.charge(ZERO);
		memberBCash = Cash.charge(ZERO);

		Address address = Address.builder()
				.city("서울")
				.specificAddress("은마아파트")
				.zipcode(22222)
				.build();

		memberA = Member.builder()
				.name("사나")
				.loginId("sana")
				.password("1234")
				.address(address)
				.cash(memberACash)
				.build();

		duplicateMember = Member.builder()
				.name("사나")
				.loginId("sana")
				.password("1234")
				.address(address)
				.cash(memberACash)
				.build();

		memberB = Member.builder()
				.name("다현")
				.loginId("dahyun")
				.password("1234")
				.address(address)
				.cash(memberBCash)
				.build();
	}

	@AfterEach
	void tearDown() {
		dataBaseCleanup.execute();
	}

	@Test
	void 중복된_회원_저장할_경우() {
		//given
		memberService.join(memberA);

		//when
		//then
		assertThatThrownBy(() -> memberService.join(duplicateMember))
				.isInstanceOf(IllegalStateException.class);
	}

	@Test
	void 회원을_찾는_경우() {
		//given
		memberService.join(memberA);
		memberService.join(memberB);

		//when
		List<Member> members = memberService.findMembers();

		//then
		assertThat(members.size()).isEqualTo(2);
		assertThat(members).contains(memberA, memberB);
	}

	@Test
	void 존재하지_않는_회원_찾는_경우() {
		//given
		Long savedId = memberService.join(duplicateMember);

		//when
		memberService.delete(savedId);

		//then
		assertThatThrownBy(() -> memberService.findOne(savedId))
				.isInstanceOf(NoSuchElementException.class);
	}
}