package com.mycompany.webapp.dto;

import java.util.List;

import lombok.Data;

@Data
public class Category {
	private List<CategoryLarge> category;
}
