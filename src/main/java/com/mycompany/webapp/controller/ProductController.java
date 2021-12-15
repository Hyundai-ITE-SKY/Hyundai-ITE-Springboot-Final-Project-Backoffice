package com.mycompany.webapp.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.webapp.dto.Auth;
import com.mycompany.webapp.dto.Brand;
import com.mycompany.webapp.dto.Category;
import com.mycompany.webapp.dto.Color;
import com.mycompany.webapp.dto.IntegerVariable;
import com.mycompany.webapp.dto.Pager;
import com.mycompany.webapp.dto.Product;
import com.mycompany.webapp.dto.Products;
import com.mycompany.webapp.dto.Stock;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/product")
public class ProductController {
	
	//상품등록 페이지
	@GetMapping("/create")
	public String productCreate(Model model, HttpSession session) {
		log.info("실행");
		Auth auth = new Auth();
		auth.setJwt("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2MzkxMjYwMDgsIm1pZCI6Im1pZDEiLCJhdXRob3JpdHkiOiJST0xFX1VTRVIifQ.lW5znR6F9Zdl8G20TRWeVi33n-EiX6eJ6-RHIOSn7Gk");
		auth.setMid("mid1");
		WebClient webClient = WebClient.create("http://localhost:82/product");
		Products products = webClient.get().uri("/allbrand").header("Authorization", "Bearer "+ auth.getJwt()).retrieve().bodyToMono(Products.class).block();
		model.addAttribute("brands", products.getBrands());
		Category category = webClient.get().uri("/category").header("Authorization", "Bearer "+ auth.getJwt()).retrieve().bodyToMono(Category.class).block();
		model.addAttribute("category", category.getCategory());
		
		return "product/productCreate";
	}
	
	//상품등록 기능
	@PostMapping("/create")
	public String productCreateExec(Product product) throws Exception {
		List<Color> colors = product.getColors();
		String pid = product.getPid();
		
		for(Color color : colors) {
			color.setPid(pid);
			List<Stock> stocks = color.getStocks();
			for(Stock stock: stocks) {
				stock.setPid(pid);
				stock.setCcolorcode(color.getCcolorcode());
			}
		}
		log.info(product.toString());
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writeValueAsString(product);
		log.info(jsonInString);
		
		WebClient webClient = WebClient.create("http://localhost:82/product");
		webClient.post().uri("/create").header(
				HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.body(BodyInserters.fromValue(jsonInString))
				.retrieve().bodyToMono(Void.class).block();
		
		return "redirect:/product/list";
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
	@GetMapping("/update")
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
	
	//상품 수정 기능
	@PostMapping("/update")
	public String productUpdateExec(Product product, Model model, HttpSession session, RedirectAttributes redirattr) throws Exception {
		log.info("실행");
		
		String pid = product.getPid();
		
		for(Color color : product.getColors()) {
			color.setPid(pid);
			List<Stock> stocks = color.getStocks();
			
			for(Stock stock: stocks) {
				stock.setPid(pid);
				stock.setCcolorcode(color.getCcolorcode());
			}
		}
		
		String pseason = product.getPseason();
		product.setPseason(pseason.substring(0, pseason.length()-1)); //콤마가 찍히는 부분 제거
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writeValueAsString(product);
		log.info(jsonInString);
		
		WebClient webClient = WebClient.create("http://localhost:82/product");
		Products updateProducts = webClient.post().uri("/update").header(
				HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.body(BodyInserters.fromValue(jsonInString))
				.retrieve().bodyToMono(Products.class).block();
		
		Product updateProduct = updateProducts.getProduct();
		redirattr.addAttribute("pid", updateProduct.getPid());
		
		return "redirect:/product/detail";
	}
	
	//상품삭제
	@RequestMapping("/delete")
	public String deleteProduct(@RequestParam String pid) {
		log.info("실행");
		WebClient webClient = WebClient.create("http://localhost:82/product");
		IntegerVariable integerVariable = webClient.delete().uri("/{pid}", pid).retrieve().bodyToMono(IntegerVariable.class).block();
		return "redirect:/product/list";
	}
	
	@RequestMapping("/productstock")
	public String updateProductStock(Model model) {
		log.info("실행");
		
		return "/product/productStock";
	}
	
}