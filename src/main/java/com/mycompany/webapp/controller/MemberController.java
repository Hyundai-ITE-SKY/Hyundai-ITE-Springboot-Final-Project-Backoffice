package com.mycompany.webapp.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.mycompany.webapp.dto.Grade;
import com.mycompany.webapp.dto.Grades;
import com.mycompany.webapp.dto.Member;
import com.mycompany.webapp.dto.Members;
import com.mycompany.webapp.dto.Result;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/admin/member")
public class MemberController {
	// 회원 정보 관리
	@RequestMapping("/list")
	public String memberInfo(Model model, HttpSession session) {
		log.info("실행");
		WebClient webClient = WebClient.create();

		Members members = webClient.get().uri("http://kosa1.iptime.org:50507/member/list").retrieve()
				.bodyToMono(Members.class).block();

		model.addAttribute("members", members.getMembers());

		return "member/memberList";
	}

	@RequestMapping("/create")
	public String createMember(Member member, Model model, HttpSession session) {
		log.info("실행");

		WebClient webClient = WebClient.create();
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();

		SimpleDateFormat sDate = new SimpleDateFormat("yyyy-MM-dd");

		map.add("mid", member.getMid());
		map.add("mpassword", member.getMpassword());
		map.add("mname", member.getMname());
		map.add("memail", member.getMemail());
		map.add("mtel", member.getMtel());
		map.add("mzipcode", member.getMzipcode());
		map.add("maddress1", member.getMaddress1());
		map.add("maddress2", member.getMaddress2());
		map.add("mgrade", "Level1");
		map.add("mdate", sDate.format(member.getMdate()));
		map.add("mpoint", "0");
		map.add("mrole", member.getMrole());
		map.add("mtotalpayment", String.valueOf(member.getMtotalpayment()));

		log.info(member.toString());

		Result result = webClient.post().uri("http://kosa1.iptime.org:50507/member/create")
				.body(BodyInserters.fromFormData(map)).retrieve().bodyToMono(Result.class).block();

		log.info(result.toString());

		return "redirect:/admin/member/list";
	}

	@RequestMapping("/detail")
	public String memberDetail(String mid, Model model, HttpSession session) {
		log.info("실행");

		WebClient webClient = WebClient.create();

		log.info(mid);

		Member member = webClient.get().uri("http://kosa1.iptime.org:50507/member/detail?mid={mid}", mid).retrieve()
				.bodyToMono(Member.class).block();

		model.addAttribute("member", member);

		return "member/memberDetail";
	}

	@RequestMapping("/delete")
	public String deleteGrade(String mid, Model model, HttpSession session) {
		log.info("실행");

		WebClient webClient = WebClient.create();

		Result result = webClient.delete().uri("http://kosa1.iptime.org:50507/member/delete?mid={mid}", mid).retrieve()
				.bodyToMono(Result.class).block();

		log.info(result.toString());

		return "redirect:/admin/member/list";
	}

	// 회원 등급 관리
	@RequestMapping("/grade")
	public String memberGrade(Model model, HttpSession session) {
		log.info("실행");

		WebClient webClient = WebClient.create();
		Grades grades = webClient.get().uri("http://kosa1.iptime.org:50507/member/grade/list").retrieve()
				.bodyToMono(Grades.class).block();
		model.addAttribute("grades", grades.getGrades());

		return "member/memberGrade";
	}

	@RequestMapping("/grade/create")
	public String createGrade(Grade grade, Model model, HttpSession session) {
		log.info("실행");

		WebClient webClient = WebClient.create();
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("gname", grade.getGname());
		map.add("gmax", String.valueOf(grade.getGmax()));
		map.add("gsale", String.valueOf(grade.getGsale()));

		Result result = webClient.post().uri("http://kosa1.iptime.org:50507/member/grade/create")
				.body(BodyInserters.fromFormData(map)).retrieve().bodyToMono(Result.class).block();

		log.info(result.toString());

		return "redirect:/admin/member/grade";
	}

	// 스케줄러
	@Scheduled(cron = "0 0 1 * * *")
	public void updateGradePerDay() {
		log.info("새벽 1시마다 실행" + new Date());
		applyGrade();
	}

	@RequestMapping("/grade/apply")
	public String applyGrade() {
		log.info("실행");

		WebClient webClient = WebClient.create();

		Result result = webClient.get().uri("http://kosa1.iptime.org:50507/member/grade/apply").retrieve()
				.bodyToMono(Result.class).block();

		log.info(result.toString());

		return "redirect:/admin/member/list";
	}

	@RequestMapping("/grade/update")
	public String updateGrade(int beforegmax, Grade grade, Model model, HttpSession session) {
		log.info("실행");

		WebClient webClient = WebClient.create();
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("gname", grade.getGname());
		map.add("gmax", String.valueOf(grade.getGmax()));
		map.add("gsale", String.valueOf(grade.getGsale()));

		Result result = webClient.post().uri("http://kosa1.iptime.org:50507/member/grade/update/" + beforegmax)
				.body(BodyInserters.fromFormData(map)).retrieve().bodyToMono(Result.class).block();

		log.info(result.toString());

		return "redirect:/admin/member/grade";
	}

	@RequestMapping("/grade/delete")
	public String deleteGrade(int gmax, Model model, HttpSession session) {
		log.info("실행");

		WebClient webClient = WebClient.create();

		Result result = webClient.delete().uri("http://kosa1.iptime.org:50507/member/grade/delete?gmax={gmax}", gmax)
				.retrieve().bodyToMono(Result.class).block();

		log.info(result.toString());

		return "redirect:/admin/member/grade";
	}
}
