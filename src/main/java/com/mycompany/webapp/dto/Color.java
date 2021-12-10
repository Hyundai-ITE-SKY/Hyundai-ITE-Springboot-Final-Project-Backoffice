package com.mycompany.webapp.dto;

import java.util.List;

import lombok.Data;

@Data
public class Color {
	private String pid;
	private String ccolorcode;
	private String cimage1;
	private String cimage2;
	private String cimage3;
	private String ccolorimage;
	private String cmatchpid;
	private List<Stock> stocks;
}