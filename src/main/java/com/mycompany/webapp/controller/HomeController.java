package com.mycompany.webapp.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.mycompany.webapp.dto.Auth;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class HomeController {

	@RequestMapping("/")
	public String home(HttpSession session) {
		log.info("실행");

		if (session.getAttribute("auth") == null) {
			return "redirect:/loginform";
		}

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

		log.info(auth.toString());

		session.setAttribute("auth", auth);
		model.addAttribute("auth", auth);

		return "redirect:/";
	}
}
