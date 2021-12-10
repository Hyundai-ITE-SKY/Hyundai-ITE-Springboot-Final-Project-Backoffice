package com.mycompany.webapp.dto;

import java.util.List;

import lombok.Data;

@Data
public class Products {
	private List<Product> products;
	private int totalRows;
	private Product product;
	private List<Brand> brands;
}
