package com.mycompany.webapp.dto;

import java.util.Date;

import lombok.Data;

@Data
public class Review {
	private int rno;
	private String mid;
	private String mname;
	private String pid;
	private String pname;
	private String pcolor;
	private String psize;
	private Date rdate;
	private int rrate;
	private int rprice;
	private int rcomfortable;
	private String rtitle;
	private String rcontent;
}