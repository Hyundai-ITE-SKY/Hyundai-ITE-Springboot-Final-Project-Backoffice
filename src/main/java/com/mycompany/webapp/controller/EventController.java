package com.mycompany.webapp.controller;

import java.text.SimpleDateFormat;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestBodySpec;

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
@RequestMapping("/admin/event")
public class EventController {
	//이벤트 조회
	@RequestMapping("/list/{pageNo}")
	public String eventList(Model model, @PathVariable int pageNo, HttpSession session) {
		log.info("실행");
		
		Auth auth = (Auth) session.getAttribute("auth");
		
		WebClient getRowsWebClient = WebClient.create();
		IntegerVariable totalRows = getRowsWebClient.get().uri("http://kosa1.iptime.org:50507/event/totalrows")
										.retrieve().bodyToMono(IntegerVariable.class).block();
		
		WebClient webClient = WebClient.create();
		Events events = webClient.get().uri("http://kosa1.iptime.org:50507/event/list?pageNo={pageNo}", pageNo)
										.retrieve().bodyToMono(Events.class).block();
		Pager pager = new Pager(5, 5, totalRows.getValue(), pageNo);
		
		model.addAttribute("events", events.getEvents());
		model.addAttribute("pager", pager);
		return "event/eventList";
	}
	//이벤트 상세 조회
	@RequestMapping("/detail/{eid}")
	public String eventDetail(Model model, @PathVariable int eid, HttpSession session) {
		log.info("실행");
		
		Auth auth = (Auth) session.getAttribute("auth");
		
		WebClient webClient = WebClient.create();
		Event event = webClient.get().uri("http://kosa1.iptime.org:50507/event/detail?eid={eid}", eid)
				.retrieve().bodyToMono(Event.class).block();
		
		model.addAttribute("event", event);
		
		return "event/eventDetail";
	}
	
	//이벤트 수정
	@GetMapping("/update/{eid}")
	public String eventUpdateForm(Model model, @PathVariable int eid, HttpSession session) {
		log.info("실행");
		
		Auth auth = (Auth) session.getAttribute("auth");
		
		WebClient webClient = WebClient.create();
		Event event = webClient.get().uri("http://kosa1.iptime.org:50507/event/detail?eid={eid}", eid)
				.retrieve().bodyToMono(Event.class).block();
		
		model.addAttribute("event", event);
		
		return "event/eventUpdate";
	}
	
	@PostMapping("/update/{eid}")
	public String eventUpdate(Model model, @PathVariable int eid, Event event, HttpSession session) {
		log.info("실행");
		/*수정*/
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
		
		IntegerVariable integerVariable = webClient.post().uri("http://kosa1.iptime.org:50507/event/update")
				.body(BodyInserters.fromFormData(map))
				.retrieve().bodyToMono(IntegerVariable.class).block();
		
		
		/* detail로 보낼 event 정보 get */
		Auth auth = (Auth) session.getAttribute("auth");
		
		WebClient webClient2 = WebClient.create();
		Event newEvent = webClient2.get().uri("http://kosa1.iptime.org:50507/event/detail?eid={eid}", eid)
				.retrieve().bodyToMono(Event.class).block();
		
		model.addAttribute("event", newEvent);
		
		return "event/eventDetail";
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
		
		IntegerVariable integerVariable = webClient.post().uri("http://kosa1.iptime.org:50507/event/create")
											.body(BodyInserters.fromFormData(map))
											.retrieve().bodyToMono(IntegerVariable.class).block();
		
		return "redirect:/admin/event/list/1";
	}
	
	@GetMapping("/delete/{eid}")
	public String eventDelete(@PathVariable int eid) {
		log.info("실행");
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("eid", eid+"");
		
		WebClient webClient = WebClient.create();
		IntegerVariable integerVariable = ((RequestBodySpec) webClient.delete().uri("http://kosa1.iptime.org:50507/event/delete"))
				.body(BodyInserters.fromFormData(map))
				.retrieve().bodyToMono(IntegerVariable.class).block();
		
		return "redirect:/admin/event/list/1";
	}
}
