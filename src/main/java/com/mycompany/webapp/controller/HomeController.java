package com.mycompany.webapp.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.webapp.dto.Auth;
import com.mycompany.webapp.dto.IntegerVariable;
import com.mycompany.webapp.dto.OrderPerDays;
import com.mycompany.webapp.dto.Products;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class HomeController {

	@RequestMapping("/")
	public String home(HttpSession session, Model model) {
		log.info("실행");

		Auth auth = (Auth) session.getAttribute("auth");

		if (auth == null || auth.getResult().equals("fail")) {
			return "redirect:/loginform";
		}
		
		WebClient webClient = WebClient.create();
		//총 매출액
		IntegerVariable totalPrice = webClient.get().uri("http://localhost:82/dash/totalprice")
				.header("Authorization", "Bearer "+ auth.getJwt()).retrieve().bodyToMono(IntegerVariable.class).block();
		model.addAttribute("totalPrice", totalPrice.getValue());
		
		//이번달 매출액
		IntegerVariable monthPrice = webClient.get().uri("http://localhost:82/dash/monthprice")
				.header("Authorization", "Bearer "+ auth.getJwt()).retrieve().bodyToMono(IntegerVariable.class).block();
		model.addAttribute("monthPrice", monthPrice.getValue());
		
		//금일 매출액
		IntegerVariable todayPrice = webClient.get().uri("http://localhost:82/dash/todayprice")
				.header("Authorization", "Bearer "+ auth.getJwt()).retrieve().bodyToMono(IntegerVariable.class).block();
		model.addAttribute("todayPrice", todayPrice.getValue());
		
		//날짜별 주문수
		OrderPerDays orderPerDays = webClient.get().uri("http://localhost:82/dash/order/day")
				.header("Authorization", "Bearer "+ auth.getJwt()).retrieve().bodyToMono(OrderPerDays.class).block();
		model.addAttribute("orderPerDays", orderPerDays.getOrderPerDays());
		log.info("orderPerDays(일자별 결제건수) : "+orderPerDays.getOrderPerDays());
		
		//날짜별 주문자수
		OrderPerDays orderPerDayUser = webClient.get().uri("http://localhost:82/dash/order/dayuser")
				.header("Authorization", "Bearer "+ auth.getJwt()).retrieve().bodyToMono(OrderPerDays.class).block();
		model.addAttribute("orderPerDayUser", orderPerDayUser.getOrderPerDays());
		log.info("orderPerDayUser(일자별 결제자수) : "+orderPerDayUser.getOrderPerDays());

		//월별, 카테고리별 매출
		String MonthTotalPrice = webClient.get().uri("http://localhost:82/dash/monthtotalprice")
									.header("Authorization", "Bearer "+ auth.getJwt()).retrieve().bodyToMono(String.class).block();
		model.addAttribute("monthTotalPrice", MonthTotalPrice);
		return "home";
	}

	@RequestMapping("/loginform")
	public String loginForm() {
		log.info("실행");
		return "login";
	}

	@RequestMapping("/login")
	public String login(String mid, String mpassword, Model model, HttpSession session) {
		log.info("실행");

		WebClient webClient = WebClient.create();
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();

		map.add("mid", mid);
		map.add("mpassword", mpassword);

		Auth auth = webClient.post().uri("http://localhost:82/login").body(BodyInserters.fromFormData(map)).retrieve()
				.bodyToMono(Auth.class).block();

		session.setAttribute("auth", auth);
		model.addAttribute("auth", auth);

		return "redirect:/";
	}
}
