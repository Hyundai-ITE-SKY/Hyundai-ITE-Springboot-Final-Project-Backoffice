package com.mycompany.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/product")
public class ProductController {
	
	//상품등록
	@RequestMapping("/create")
	public String productCreate() {
		log.info("실행");
		return "product/ProductCreate";
	}
	
	//상품조회
	@RequestMapping("/list")
	public String productList() {
		log.info("실행");
		return "product/ProductList";
	}
	
	//상품상세
	@RequestMapping("/detail")
	public String productDetail() {
		log.info("실행");
		return "product/ProductDetail";
	}
	
	//상품수정
	@RequestMapping("/update")
	public String productUpdate() {
		log.info("실행");
		return "product/ProductUpdate";
	}
  
	@RequestMapping("/productstock")
	public String orderInquiry(Model model) {
		log.info("실행");

		return "/product/productStock";
	}
	
}