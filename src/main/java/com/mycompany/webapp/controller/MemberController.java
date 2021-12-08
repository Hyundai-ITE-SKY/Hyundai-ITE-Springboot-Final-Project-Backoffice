package com.mycompany.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/member")
public class MemberController {
	
	//회원 등급 관리
	@RequestMapping("/grade")
	public String memberGrade() {
		log.info("실행");
		return "member/memberGrade";
	}
	
	//회원 정보 관리 
	@RequestMapping("/list")
	public String memberInfo() {
		log.info("실행");
		return "member/memberList";
	}
	
}
