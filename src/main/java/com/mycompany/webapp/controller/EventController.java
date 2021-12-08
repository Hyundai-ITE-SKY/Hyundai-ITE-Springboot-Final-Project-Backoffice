package com.mycompany.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/event")
public class EventController {
	//상품조회
	@RequestMapping("/list")
	public String eventList() {
		log.info("실행");
		return "event/eventList";
	}
	
	@RequestMapping("/detail")
	public String eventDetail() {
		log.info("실행");
		return "event/eventDetail";
	}
	
	@RequestMapping("/update")
	public String eventUpdate() {
		log.info("실행");
		return "event/eventUpdate";
	}
	
	@RequestMapping("/create")
	public String eventCreate() {
		log.info("실행");
		return "event/eventCreate";
	}
}
