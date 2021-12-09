package com.mycompany.webapp.dto;

import java.util.List;

import lombok.Data;

@Data
public class Products {
	public List<Product> products;
	public int totalRows;
}
