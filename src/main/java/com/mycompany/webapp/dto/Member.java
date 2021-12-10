package com.mycompany.webapp.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class Member {
	private String mid;
	private String mpassword;
	private String mname;
	private String memail;
	private String mtel;
	private String mzipcode;
	private String maddress1;
	private String maddress2;
	private String mgrade;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date mdate;
	private int mpoint;
	private boolean menabled;
	private String mrole;
	private int mtotalpayment;
}