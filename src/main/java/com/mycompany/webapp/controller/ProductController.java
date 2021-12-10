package com.mycompany.webapp.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;

import com.mycompany.webapp.dto.Auth;
import com.mycompany.webapp.dto.Brand;
import com.mycompany.webapp.dto.Category;
import com.mycompany.webapp.dto.Pager;
import com.mycompany.webapp.dto.Product;
import com.mycompany.webapp.dto.Products;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/product")
public class ProductController {
	
	//상품등록
	@RequestMapping("/create")
	public String productCreate() {
		log.info("실행");
		return "product/productCreate";
	}
	
	//상품조회
	@RequestMapping("/list")
	public String productList(Model model, HttpSession session, @RequestParam(defaultValue="1") int pageNo) {
		log.info("실행");
		
		Auth auth = new Auth();
		auth.setJwt(
				"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2MzkxMjYwMDgsIm1pZCI6Im1pZDEiLCJhdXRob3JpdHkiOiJST0xFX1VTRVIifQ.lW5znR6F9Zdl8G20TRWeVi33n-EiX6eJ6-RHIOSn7Gk");
		auth.setMid("mid1");
		
		WebClient webClient = WebClient.create();

		Products productList = webClient.get().uri("http://localhost:82/product/list/{pageNo}", pageNo)
				.header("Authorization", "Bearer "+ auth.getJwt()).retrieve().bodyToMono(Products.class).block();
		
		Pager pager = new Pager(12, 5, productList.getTotalRows(), pageNo);
		model.addAttribute("products", productList.getProducts());
		model.addAttribute("pager",pager);

		return "product/productList";
	}
	
	//상품상세
	@RequestMapping("/detail")
	public String productDetail(Model model, HttpSession session, @RequestParam("pid")String pid) {
		log.info("실행");
		
		Auth auth = new Auth();
		auth.setJwt(
				"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2MzkxMjYwMDgsIm1pZCI6Im1pZDEiLCJhdXRob3JpdHkiOiJST0xFX1VTRVIifQ.lW5znR6F9Zdl8G20TRWeVi33n-EiX6eJ6-RHIOSn7Gk");
		auth.setMid("mid1");
		
		WebClient webClient = WebClient.create();
		
		Products products = webClient.get().uri("http://localhost:82/product/{pid}", pid)
				.header("Authorization", "Bearer "+ auth.getJwt()).retrieve().bodyToMono(Products.class).block();
		
		Product product = products.getProduct();
		model.addAttribute("product", product);
		model.addAttribute("colors", product.getColors());
		return "product/productDetail";
	}
	
	//상품수정 페이지 
	@RequestMapping("/update")
	public String productUpdate(Model model, HttpSession session, @RequestParam("pid")String pid) {
		log.info("실행");
		

		Auth auth = new Auth();
		auth.setJwt(
				"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2MzkxMjYwMDgsIm1pZCI6Im1pZDEiLCJhdXRob3JpdHkiOiJST0xFX1VTRVIifQ.lW5znR6F9Zdl8G20TRWeVi33n-EiX6eJ6-RHIOSn7Gk");
		auth.setMid("mid1");
		
		WebClient webClient = WebClient.create();
		
		Products products = webClient.get().uri("http://localhost:82/product/{pid}", pid)
				.header("Authorization", "Bearer "+ auth.getJwt()).retrieve().bodyToMono(Products.class).block();
		Category categoryList = webClient.get().uri("http://localhost:82/product/category")
				.header("Authorization", "Bearer "+ auth.getJwt()).retrieve().bodyToMono(Category.class).block();
		
		Product product = products.getProduct();

		model.addAttribute("product", product);
		model.addAttribute("colors", product.getColors());
		model.addAttribute("categoryList", categoryList.getCategory());
		
		List<Brand> brands = products.getBrands();
		model.addAttribute("brands", brands);
		return "product/productUpdate";
	}
  
	@RequestMapping("/productstock")
	public String orderInquiry(Model model) {
		log.info("실행");

		return "/product/productStock";
	}
	
}