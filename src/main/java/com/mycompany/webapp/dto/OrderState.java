package com.mycompany.webapp.dto;

import lombok.Data;

@Data
public class OrderState {
	private int finPayCount;
	private int shippingCount;
	private int finShippingCount;
	private int confirmOrderCount;
}
