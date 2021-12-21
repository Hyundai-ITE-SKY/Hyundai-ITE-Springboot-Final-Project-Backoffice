package com.mycompany.webapp.dto;

import java.util.Date;

import lombok.Data;

@Data
public class OrderPerDay {
	private String odate;
	private int num;
}