package com.mycompany.webapp.dto;

import lombok.Data;

@Data
public class StockList {
	private String bname;
	private String pid;
	private String pname;
	private String ccolorcode;
	private String ssize;
	private int samount;
	private int ptotalamount;
	private int stotalamount;
}
