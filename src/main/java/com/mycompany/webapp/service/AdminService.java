package com.mycompany.webapp.service;

import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.mycompany.webapp.dto.Member;

@Service
public class AdminService {

	public Member selectAdminInfo(String mid) {
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("mid", mid);
		
		WebClient webClient = WebClient.create();
		Member member = webClient.post().uri("http://kosa1.iptime.org:50507/login")
								 .body(BodyInserters.fromFormData(map))
								 .retrieve()
								 .bodyToMono(Member.class).block();
		return member;
	}

}
