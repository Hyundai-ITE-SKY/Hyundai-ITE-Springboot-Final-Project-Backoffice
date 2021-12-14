package com.mycompany.webapp.dto;

import java.util.List;

import lombok.Data;

@Data
public class CategoryLarge {
	private String clarge;
	private List<CategoryMedium> cmedium;
}