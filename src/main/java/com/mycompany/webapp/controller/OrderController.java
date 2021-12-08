package com.mycompany.webapp.controller;


import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mycompany.webapp.dto.OrderItem;
import com.mycompany.webapp.dto.OrderList;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/order")
public class OrderController {
	
	@RequestMapping("/orderinquiry")
	public String orderInquiry(Model model) {
		log.info("실행");
		List<OrderList> orderList = new LinkedList<OrderList>();
		
		for(int i=0; i<5; i++) {
			OrderList order = new OrderList();
			order.setMid("user1");
			order.setOid("oid");
			order.setOdate(new Date());
			order.setOtotal(10000000);
			order.setOstatus(1);
			orderList.add(order);
		}
		//주문 개수
		
		model.addAttribute("orderList", orderList);
		return "/order/orderInquiry";
	}
	
	@RequestMapping("/orderdetail")
	public String orderDetail(Model model) {
		log.info("실행");
		List<OrderItem> orderItems = new LinkedList<OrderItem>();
		OrderList orderList = new OrderList();
		
		model.addAttribute("orderItem", orderItems);
		model.addAttribute("orderList", orderList);
		return "/order/orderDetail";
	}
}
