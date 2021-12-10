package com.mycompany.webapp.controller;

import java.text.SimpleDateFormat;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.mycompany.webapp.dto.Auth;
import com.mycompany.webapp.dto.Event;
import com.mycompany.webapp.dto.Events;
import com.mycompany.webapp.dto.Grades;
import com.mycompany.webapp.dto.IntegerVariable;
import com.mycompany.webapp.dto.Order;
import com.mycompany.webapp.dto.OrderLists;
import com.mycompany.webapp.dto.Pager;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/event")
public class EventController {
	//이벤트 조회
	@RequestMapping("/list/{pageNo}")
	public String eventList(Model model, @PathVariable int pageNo) {
		log.info("실행");
		
		//Auth auth = (Auth) session.getAttribute("auth");
		Auth auth = new Auth();
		auth.setJwt(
				"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2MzkwMzE0NzgsIm1pZCI6Im1pZDEiLCJhdXRob3JpdHkiOiJST0xFX1VTRVIifQ.ynK_wUj7ZyiTlFp180FAnnd5KvtlLnlEFgrE7Hr0OVA");
		auth.setMid("mid1");
		
		WebClient getRowsWebClient = WebClient.create();
		IntegerVariable totalRows = getRowsWebClient.get().uri("http://localhost:82/event/totalrows")
										.header("Authorization", "Bearer" + auth.getJwt()).retrieve().bodyToMono(IntegerVariable.class).block();

		WebClient webClient = WebClient.create();
		Events events = webClient.get().uri("http://localhost:82/event/list?pageNo={pageNo}", pageNo)
										.header("Authorization", "Bearer" + auth.getJwt()).retrieve().bodyToMono(Events.class).block();
		Pager pager = new Pager(5, 5, totalRows.getValue(), pageNo);
		
		model.addAttribute("events", events.getEvents());
		model.addAttribute("pager", pager);
		return "event/eventList";
	}
	//이벤트 상세 조회
	@RequestMapping("/detail/{eid}")
	public String eventDetail(Model model, @PathVariable int eid) {
		log.info("실행");
		
		//Auth auth = (Auth) session.getAttribute("auth");
		Auth auth = new Auth();
		auth.setJwt(
				"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2MzkxMDg4NDQsIm1pZCI6Im1pZDEiLCJhdXRob3JpdHkiOiJST0xFX1VTRVIifQ.K4W_19VvCxl1tXSra6Fz6VHKZEwqAuyEVGyfVYBNuqU");
		auth.setMid("mid1");
		
		WebClient webClient = WebClient.create();
		Event event = webClient.get().uri("http://localhost:82/event/detail?eid={eid}", eid)
				.header("Authorization", "Bearer" + auth.getJwt()).retrieve().bodyToMono(Event.class).block();
		
		model.addAttribute("event", event);
		
		return "event/eventDetail";
	}
	//이벤트 수정
	@RequestMapping("/update")
	public String eventUpdate() {
		log.info("실행");
		return "event/eventUpdate";
	}
	//이벤트 추가
	@GetMapping("/create")
	public String eventCreateForm(Model model) {
		log.info("실행");
		model.addAttribute("event", new Event());
		return "event/eventCreate";
	}
	
	@PostMapping("/create")
	public String eventCreate(Event event) {
		log.info("실행");
		log.info("event " + event);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

		WebClient webClient = WebClient.create();
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("eid", event.getEid()+"");
		map.add("ename", event.getEname()+"");
		map.add("edetail", event.getEdetail()+"");
		map.add("estartdate", simpleDateFormat.format(event.getEstartdate())+"");
		map.add("eenddate", simpleDateFormat.format(event.getEenddate())+"");
		map.add("eimage", event.getEimage()+"");
		map.add("eamount", event.getEamount()+"");
		map.add("elimit", event.getElimit()+"");
		//log.info("estartdate " + simpleDateFormat.format(event.getEstartdate()));
		
		//문제
		IntegerVariable integerVariable = webClient.post().uri("http://localhost:82/event/create")
											.body(BodyInserters.fromFormData(map))
											.retrieve().bodyToMono(IntegerVariable.class).block();
		
		return "event/eventList";
	}
}
