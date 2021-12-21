package com.mycompany.webapp.dto;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class OrderPerDays {
	private List<OrderPerDay> orderPerDays;
}