package com.mycompany.webapp.dto;

import java.util.List;

import lombok.Data;

@Data
public class StockLists {
	private List<StockList> stockLists;
	private int totalRows;
}
