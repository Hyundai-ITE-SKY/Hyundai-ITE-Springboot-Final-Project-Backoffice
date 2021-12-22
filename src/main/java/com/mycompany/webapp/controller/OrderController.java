package com.mycompany.webapp.controller;


import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;

import com.mycompany.webapp.dto.Auth;
import com.mycompany.webapp.dto.IntegerVariable;
import com.mycompany.webapp.dto.Order;
import com.mycompany.webapp.dto.OrderList;
import com.mycompany.webapp.dto.OrderLists;
import com.mycompany.webapp.dto.Pager;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/admin/order")
public class OrderController {
	
	@RequestMapping("/orderlist/{pageNo}")
	public String orderInquiry(Model model, @PathVariable int pageNo, HttpSession session) {
		log.info("실행");
		
		Auth auth = (Auth) session.getAttribute("auth");
		
		WebClient getRowsWebClient = WebClient.create();
		IntegerVariable totalRows = getRowsWebClient.get().uri("http://localhost:82/order/totalrows")
										.retrieve().bodyToMono(IntegerVariable.class).block();
		
		WebClient webClient = WebClient.create();
		OrderLists orderLists = webClient.get().uri("http://localhost:82/order/list?pageNo={pageNo}", pageNo)
										.retrieve().bodyToMono(OrderLists.class).block();
		Pager pager = new Pager(12, 5, totalRows.getValue(), pageNo);

		model.addAttribute("orderLists", orderLists.getOrderlists());
		model.addAttribute("pager", pager);
		return "/order/orderList";
	}
	
	@RequestMapping("/orderdetail/{oid}")
	public String orderDetail(Model model, @PathVariable String oid, HttpSession session) {
		log.info("실행");
		
		Auth auth = (Auth) session.getAttribute("auth");
		
		WebClient webClient = WebClient.create();
		Order order = webClient.get().uri("http://localhost:82/order/detail?oid={oid}", oid)
				.retrieve().bodyToMono(Order.class).block();
		
		model.addAttribute("orderlist", order.getOrderlist());
		model.addAttribute("orderitems", order.getOrderitem());
		
		return "/order/orderDetail";
	}
	
	@RequestMapping("/search")
	public String getSearchList(@RequestParam(required=false) String type,
							  @RequestParam(required=false) String keyword,
							  @RequestParam(defaultValue="1") int pageNo,
							  @RequestParam(required=false) String startdate,
							  @RequestParam(required=false) String enddate,
							  HttpSession session, Model model) {
		log.info("type : "+ type+ "keyword : " + keyword);
		Auth auth = (Auth) session.getAttribute("auth");
		
		WebClient webClient = WebClient.create("http://localhost:82/order");
		OrderLists orderlists = webClient.get().uri(builder -> builder.path("/getSearchList")
				.queryParam("type", type)
				.queryParam("keyword", keyword)
				.queryParam("pageNo", pageNo)
				.queryParam("startdate", startdate)
				.queryParam("enddate", enddate)
				.build())
				.retrieve().bodyToMono(OrderLists.class).block();
		
		Pager pager = new Pager(12, 5, orderlists.getTotalRows(), pageNo);
		
		for(OrderList order:orderlists.getOrderlists()) {
			log.info(order.getOstatus()+"");
		}
		model.addAttribute("orderLists", orderlists.getOrderlists());
		model.addAttribute("pager", pager);
		model.addAttribute("type", type);
		model.addAttribute("keyword", keyword);
		model.addAttribute("startdate", startdate);
		model.addAttribute("enddate", enddate);
		
		return "/order/orderList";
	}
}
