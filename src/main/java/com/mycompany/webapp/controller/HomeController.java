package com.mycompany.webapp.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.json.*;
import org.json.JSONObject;
import org.springframework.*;
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
import com.mycompany.webapp.dto.OrderState;

import ch.qos.logback.core.joran.conditional.IfAction;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class HomeController {

	@RequestMapping("/")
	public String goToHome() {
		return "redirect:/admin";
	}
	
	@RequestMapping("/admin")
	public String home(HttpSession session, Model model) {
		log.info("실행");

//		Auth auth = (Auth) session.getAttribute("auth");
//
//		if (auth == null || auth.getResult().equals("fail")) {
//			return "redirect:/loginform";
//		}
		
		WebClient webClient = WebClient.create("http://kosa1.iptime.org:50507/dash");
		
		//주문/배송상태
		OrderState orderState = webClient.get().uri("/orderState").retrieve().bodyToMono(OrderState.class).block();
		model.addAttribute("orderState", orderState);
		
		//총 매출액
		IntegerVariable totalPrice = webClient.get().uri("/totalprice").retrieve().bodyToMono(IntegerVariable.class).block();
		model.addAttribute("totalPrice", totalPrice.getValue());
		
		//이번달 매출액
		IntegerVariable monthPrice = webClient.get().uri("/monthprice").retrieve().bodyToMono(IntegerVariable.class).block();
		model.addAttribute("monthPrice", monthPrice.getValue());
		
		//금일 매출액
		IntegerVariable todayPrice = webClient.get().uri("/todayprice").retrieve().bodyToMono(IntegerVariable.class).block();
		model.addAttribute("todayPrice", todayPrice.getValue());
		
		//날짜별 주문수
		OrderPerDays orderPerDays = webClient.get().uri("/order/day").retrieve().bodyToMono(OrderPerDays.class).block();
		model.addAttribute("orderPerDays", orderPerDays.getOrderPerDays());
		log.info("orderPerDays(일자별 결제건수) : "+orderPerDays.getOrderPerDays());
		
		//날짜별 주문자수
		OrderPerDays orderPerDayUser = webClient.get().uri("/order/dayuser").retrieve().bodyToMono(OrderPerDays.class).block();
		model.addAttribute("orderPerDayUser", orderPerDayUser.getOrderPerDays());
		log.info("orderPerDayUser(일자별 결제자수) : "+orderPerDayUser.getOrderPerDays());

		//월별, 카테고리별 매출
		String MonthTotalPrice = webClient.get().uri("/monthtotalprice").retrieve().bodyToMono(String.class).block();
		Map<String, Map<String, Integer>> paramMap;

		JSONObject jsonObject = new JSONObject(MonthTotalPrice);
		log.info(jsonObject+"");
		
		Map<String, Map<String, Integer>> monthTotalPriceMap = new HashMap<>();
		Iterator<String> keysItr = jsonObject.keys();
		while(keysItr.hasNext()) {
			String key1 = keysItr.next();
			Object value1 = jsonObject.get(key1);
			Map<String, Integer> innerMap = new HashMap<>();
			monthTotalPriceMap.put(key1, innerMap);
			
			JSONObject jsonObject2 = new JSONObject(value1.toString());
			Iterator<String> keysItr2 = jsonObject2.keys();
			while(keysItr2.hasNext()) {
				String key2 = keysItr2.next();
				String value2 = jsonObject2.get(key2).toString();
				monthTotalPriceMap.get(key1).put(key2, Integer.parseInt(value2));
			}
		}
		model.addAttribute("monthTotalPrices", monthTotalPriceMap);
		return "home";
	}

	@RequestMapping("/loginform")
	public String loginForm() {
		log.info("실행");
		return "loginForm";
	}
	
	@RequestMapping("/loginError")
	public String loginError(Model model) {
		model.addAttribute("loginError",true);
		return "loginForm";
	}
	
	@RequestMapping("/accessDenied")
	public String accessDenied() {
		log.info("실행");
		return "error/403";
	}

//	@RequestMapping("/login")
//	public String login(String mid, String mpassword, Model model, HttpSession session) {
//		log.info("실행");
//
//		WebClient webClient = WebClient.create();
//		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
//
//		map.add("mid", mid);
//		map.add("mpassword", mpassword);
//
//		Auth auth = webClient.post().uri("http://kosa1.iptime.org:50507/login").body(BodyInserters.fromFormData(map)).retrieve()
//				.bodyToMono(Auth.class).block();
//
//		session.setAttribute("auth", auth);
//		model.addAttribute("auth", auth);
//
//		return "redirect:/";
//	}
}
