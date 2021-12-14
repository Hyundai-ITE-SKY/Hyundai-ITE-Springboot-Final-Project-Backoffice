package com.mycompany.webapp.controller;

import java.io.Console;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.webapp.dto.Auth;
import com.mycompany.webapp.dto.Exhibitions;
import com.mycompany.webapp.dto.IntegerVariable;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/exhibition")
public class ExhibitionController {
	
	@GetMapping("/management")
	public String exhibitionManagement(Model model, HttpSession session) {
		log.info("실행");
		
		Auth auth = (Auth) session.getAttribute("auth");
		
		WebClient getRowsWebClient = WebClient.create();
		Exhibitions exhibitions = getRowsWebClient.get().uri("http://localhost:82/product/exhibition/list")
										.header("Authorization", "Bearer" + auth.getJwt()).retrieve().bodyToMono(Exhibitions.class).block();
		
		log.info(exhibitions.getExhibitions()+"");
		model.addAttribute("exhibitions", exhibitions.getExhibitions());
		
		return "exhibition/exhibition";
	}
	
	@PostMapping("/update")
	public String exhibitionUpdate(Exhibitions exhibitions) throws JsonProcessingException {
				
		Map<String, Object> exhibitMap = new HashMap<>();
		exhibitMap.put("exhibitions", exhibitions.getExhibitions());
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writeValueAsString(exhibitMap);
		
		log.info(jsonString+"");
		
		WebClient webClient = WebClient.create();
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		exhibitions.getExhibitions();
		
		webClient.post().uri("http://localhost:82/product/exhibition/update")
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.body(BodyInserters.fromValue(jsonString))
				.retrieve().bodyToMono(Void.class).block();
		
		return "redirect:/exhibition/management";
	}
}
