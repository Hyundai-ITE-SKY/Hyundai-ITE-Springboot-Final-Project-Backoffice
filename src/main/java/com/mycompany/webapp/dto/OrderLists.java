package com.mycompany.webapp.dto;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class OrderLists {
	private List<OrderList> orderlists;
	private int totalRows;
}
