package com.mycompany.webapp.dto;

import lombok.Data;

@Data
public class OrderItem {
	private String oid;
	private String pid;
	private String ccolorcode;
	private String psize;
	private int oamount;
}
