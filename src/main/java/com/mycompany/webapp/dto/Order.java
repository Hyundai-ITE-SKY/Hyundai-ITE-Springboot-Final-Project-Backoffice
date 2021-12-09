package com.mycompany.webapp.dto;

import java.util.List;

import lombok.Data;

@Data
public class Order {
	private OrderList orderlist;
	private List<OrderItem> orderitem;
}