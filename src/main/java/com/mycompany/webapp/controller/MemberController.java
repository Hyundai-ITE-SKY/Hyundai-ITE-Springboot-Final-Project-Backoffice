package com.mycompany.webapp.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.function.client.WebClient;

import com.mycompany.webapp.dto.Auth;
import com.mycompany.webapp.dto.Grades;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/member")
public class MemberController {

	// 회원 등급 관리
	@RequestMapping("/grade")
	public String memberGrade(Model model, HttpSession session) {
		log.info("실행");

		// Auth auth = (Auth) session.getAttribute("auth");
		Auth auth = new Auth();
		auth.setJwt(
				"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2MzkwMzE0NzgsIm1pZCI6Im1pZDEiLCJhdXRob3JpdHkiOiJST0xFX1VTRVIifQ.ynK_wUj7ZyiTlFp180FAnnd5KvtlLnlEFgrE7Hr0OVA");
		auth.setMid("mid1");
		
		WebClient webClient = WebClient.create();

		Grades grades = webClient.get().uri("http://localhost:82/member/grade/list")
				.header("Authorization", "Bearer " + auth.getJwt()).retrieve().bodyToMono(Grades.class).block();
		
		model.addAttribute("grades", grades.getGrades());

		return "member/memberGrade";
	}

	// 회원 정보 관리
	@RequestMapping("/list")
	public String memberInfo() {
		log.info("실행");
		return "member/memberList";
	}

}
