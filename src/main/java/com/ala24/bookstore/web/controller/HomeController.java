package com.ala24.bookstore.web.controller;

import com.ala24.bookstore.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Slf4j
@Controller
public class HomeController {

	public static final String LOGIN_MEMBER = "loginMember";

	@RequestMapping("/")
	public String home() {
		return "home";
	}

	@GetMapping("/")
	public String homeLogin(@SessionAttribute(name = LOGIN_MEMBER, required = false) Member loginMember, Model model) {

		log.info("로그인 확인 : {}", loginMember);

		if (loginMember == null) {
			return "home";
		}

		model.addAttribute("member", loginMember);
		return "loginHome";
	}
}
