package com.mycompany.webapp.dto;

import lombok.Data;

@Data
public class Auth {
	private String jwt;
	private String mid;
	private String result;
}
