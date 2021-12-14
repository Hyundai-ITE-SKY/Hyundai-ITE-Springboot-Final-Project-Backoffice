package com.mycompany.webapp.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.function.client.WebClient;

import com.mycompany.webapp.dto.Auth;
import com.mycompany.webapp.dto.IntegerVariable;
import com.mycompany.webapp.dto.Order;
import com.mycompany.webapp.dto.OrderLists;
import com.mycompany.webapp.dto.Pager;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/order")
public class OrderController {
	
	@RequestMapping("/orderlist/{pageNo}")
	public String orderInquiry(Model model, @PathVariable int pageNo) {
		log.info("실행");
		
		//Auth auth = (Auth) session.getAttribute("auth");
		Auth auth = new Auth();
		auth.setJwt(
				"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2MzkwMzE0NzgsIm1pZCI6Im1pZDEiLCJhdXRob3JpdHkiOiJST0xFX1VTRVIifQ.ynK_wUj7ZyiTlFp180FAnnd5KvtlLnlEFgrE7Hr0OVA");
		auth.setMid("mid1");
		
		WebClient getRowsWebClient = WebClient.create();
		IntegerVariable totalRows = getRowsWebClient.get().uri("http://localhost:82/order/totalrows")
										.header("Authorization", "Bearer" + auth.getJwt()).retrieve().bodyToMono(IntegerVariable.class).block();
		
		WebClient webClient = WebClient.create();
		OrderLists orderLists = webClient.get().uri("http://localhost:82/order/list?pageNo={pageNo}", pageNo)
										.header("Authorization", "Bearer" + auth.getJwt()).retrieve().bodyToMono(OrderLists.class).block();
		Pager pager = new Pager(5, 5, totalRows.getValue(), pageNo);

		model.addAttribute("orderLists", orderLists.getOrderlists());
		model.addAttribute("pager", pager);
		return "/order/orderList";
	}
	
	@RequestMapping("/orderdetail/{oid}")
	public String orderDetail(Model model, @PathVariable String oid) {
		log.info("실행");
		
		//Auth auth = (Auth) session.getAttribute("auth");
		Auth auth = new Auth();
		auth.setJwt(
				"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2MzkxMDg4NDQsIm1pZCI6Im1pZDEiLCJhdXRob3JpdHkiOiJST0xFX1VTRVIifQ.K4W_19VvCxl1tXSra6Fz6VHKZEwqAuyEVGyfVYBNuqU");
		auth.setMid("mid1");
		
		WebClient webClient = WebClient.create();
		Order order = webClient.get().uri("http://localhost:82/order/detail?oid={oid}", oid)
				.header("Authorization", "Bearer" + auth.getJwt()).retrieve().bodyToMono(Order.class).block();
		
		model.addAttribute("orderlist", order.getOrderlist());
		model.addAttribute("orderitems", order.getOrderitem());
		
		return "/order/orderDetail";
	}
}
