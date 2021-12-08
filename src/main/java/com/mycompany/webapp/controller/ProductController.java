package com.mycompany.webapp.controller;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mycompany.webapp.dto.OrderList;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/product")
public class ProductController {

	@RequestMapping("/productstock")
	public String orderInquiry(Model model) {
		log.info("실행");

		return "/product/productStock";
	}

}
