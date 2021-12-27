package com.mycompany.webapp.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.webapp.dto.Brand;
import com.mycompany.webapp.dto.Category;
import com.mycompany.webapp.dto.Color;
import com.mycompany.webapp.dto.IntegerVariable;
import com.mycompany.webapp.dto.Pager;
import com.mycompany.webapp.dto.Product;
import com.mycompany.webapp.dto.Products;
import com.mycompany.webapp.dto.Result;
import com.mycompany.webapp.dto.Review;
import com.mycompany.webapp.dto.ReviewList;
import com.mycompany.webapp.dto.Stock;
import com.mycompany.webapp.dto.StockLists;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/admin/product")
public class ProductController {

	// 상품등록 페이지
	@GetMapping("/create")
	public String productCreate(Model model, HttpSession session) {
		log.info("실행");

		WebClient webClient = WebClient.create("http://kosa1.iptime.org:50507/product");
		Products products = webClient.get().uri("/allbrand").retrieve().bodyToMono(Products.class).block();
		model.addAttribute("brands", products.getBrands());
		Category category = webClient.get().uri("/category").retrieve().bodyToMono(Category.class).block();
		model.addAttribute("category", category.getCategory());

		return "product/productCreate";
	}

	// 상품등록 기능
	@PostMapping("/create")
	public String productCreateExec(Product product) throws Exception {
		List<Color> colors = product.getColors();
		String pid = product.getPid();

		for (Color color : colors) {
			color.setPid(pid);
			List<Stock> stocks = color.getStocks();
			for (Stock stock : stocks) {
				stock.setPid(pid);
				stock.setCcolorcode(color.getCcolorcode());
			}
		}

		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writeValueAsString(product);
		log.info(jsonInString);

		WebClient webClient = WebClient.create("http://kosa1.iptime.org:50507/product");
		webClient.post().uri("/create").header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.body(BodyInserters.fromValue(jsonInString)).retrieve().bodyToMono(Void.class).block();

		return "redirect:/admin/product/list";
	}

	// 상품조회
	@RequestMapping("/list")
	public String productList(Model model, HttpSession session, @RequestParam(defaultValue = "1") int pageNo) {
		log.info("실행");

		WebClient webClient = WebClient.create();

		Products productList = webClient.get().uri("http://kosa1.iptime.org:50507/product/list/{pageNo}", pageNo).retrieve()
				.bodyToMono(Products.class).block();
		Category categoryList = webClient.get().uri("http://kosa1.iptime.org:50507/product/category").retrieve()
				.bodyToMono(Category.class).block();

		Pager pager = new Pager(12, 5, productList.getTotalRows(), pageNo);

		model.addAttribute("products", productList.getProducts());
		model.addAttribute("pager", pager);
		model.addAttribute("categoryList", categoryList.getCategory());
		model.addAttribute("cl", "");
		model.addAttribute("cm", "");
		model.addAttribute("cs", "");

		return "product/productList";
	}

	// 상품상세
	@RequestMapping("/detail")
	public String productDetail(Model model, HttpSession session, @RequestParam("pid") String pid) {
		log.info("실행");

		WebClient webClient = WebClient.create();

		Products products = webClient.get().uri("http://kosa1.iptime.org:50507/product/{pid}", pid).retrieve()
				.bodyToMono(Products.class).block();

		Product product = products.getProduct();

		model.addAttribute("product", product);
		model.addAttribute("colors", product.getColors());

		return "product/productDetail";
	}

	// 상품수정 페이지
	@GetMapping("/update")
	public String productUpdate(Model model, HttpSession session, @RequestParam("pid") String pid) {
		log.info("실행");

		WebClient webClient = WebClient.create();

		Products products = webClient.get().uri("http://kosa1.iptime.org:50507/product/{pid}", pid).retrieve()
				.bodyToMono(Products.class).block();
		Category categoryList = webClient.get().uri("http://kosa1.iptime.org:50507/product/category").retrieve()
				.bodyToMono(Category.class).block();

		Product product = products.getProduct();

		model.addAttribute("product", product);
		model.addAttribute("colors", product.getColors());
		model.addAttribute("categoryList", categoryList.getCategory());

		List<Brand> brands = products.getBrands();
		model.addAttribute("brands", brands);

		return "product/productUpdate";
	}

	// 상품 수정 기능
	@PostMapping("/update")
	public String productUpdateExec(Product product, Model model, HttpSession session, RedirectAttributes redirattr)
			throws Exception {
		log.info("실행");

		String pid = product.getPid();

		for (Color color : product.getColors()) {
			color.setPid(pid);
			List<Stock> stocks = color.getStocks();

			for (Stock stock : stocks) {
				stock.setPid(pid);
				stock.setCcolorcode(color.getCcolorcode());
			}
		}

		String pseason = product.getPseason();
		product.setPseason(pseason.substring(0, pseason.length() - 1)); // 콤마가 찍히는 부분 제거

		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writeValueAsString(product);

		WebClient webClient = WebClient.create("http://kosa1.iptime.org:50507/product");
		Products updateProducts = webClient.post().uri("/update")
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.body(BodyInserters.fromValue(jsonInString)).retrieve().bodyToMono(Products.class).block();

		Product updateProduct = updateProducts.getProduct();
		redirattr.addAttribute("pid", updateProduct.getPid());

		return "redirect:/admin/product/detail";
	}

	// 상품삭제
	@RequestMapping("/delete")
	public String deleteProduct(@RequestParam String pid) {
		log.info("실행");
		WebClient webClient = WebClient.create("http://kosa1.iptime.org:50507/product");
		IntegerVariable integerVariable = webClient.delete().uri("/{pid}", pid).retrieve()
				.bodyToMono(IntegerVariable.class).block();
		return "redirect:/admin/product/list";
	}

	@RequestMapping("/stock/list/{pageNo}")
	public String getProductStock(Model model, @PathVariable int pageNo, HttpSession session) {
		log.info("실행");

		WebClient getRowsWebClient = WebClient.create();
		IntegerVariable totalRows = getRowsWebClient.get().uri("http://kosa1.iptime.org:50507/product/stock/totalrows").retrieve()
				.bodyToMono(IntegerVariable.class).block();

		WebClient webClient = WebClient.create();
		StockLists stockLists = webClient.get().uri("http://kosa1.iptime.org:50507/product/stock/list/{pageNo}", pageNo)
				.retrieve().bodyToMono(StockLists.class).block();

		Pager pager = new Pager(12, 5, totalRows.getValue(), pageNo);

		model.addAttribute("stocks", stockLists.getStockLists());
		model.addAttribute("pager", pager);

		return "/product/productStock";
	}

	@GetMapping("/search")
	public String getSearchList(@RequestParam String type, @RequestParam String keyword,
			@RequestParam(defaultValue = "1") int pageNo, @RequestParam String clarge, @RequestParam String cmedium,
			@RequestParam String csmall, HttpSession session, Model model) {
		log.info("실행");
		WebClient webClient = WebClient.create("http://kosa1.iptime.org:50507/product");

		Products products = webClient.get()
				.uri(builder -> builder.path("/getSearchList").queryParam("type", type).queryParam("w", keyword)
						.queryParam("pageNo", pageNo).queryParam("clarge", clarge).queryParam("cmedium", cmedium)
						.queryParam("csmall", csmall).build())
				.retrieve().bodyToMono(Products.class).block();
		Category categoryList = webClient.get().uri("http://kosa1.iptime.org:50507/product/category").retrieve()
				.bodyToMono(Category.class).block();

		Pager pager = new Pager(12, 5, products.getTotalRows(), pageNo);

		model.addAttribute("products", products.getProducts());
		model.addAttribute("pager", pager);
		model.addAttribute("keyword", keyword);
		model.addAttribute("type", type);
		model.addAttribute("cl", clarge);
		model.addAttribute("cm", cmedium);
		model.addAttribute("cs", csmall);
		model.addAttribute("categoryList", categoryList.getCategory());

		return "/product/productList";
	}

	@GetMapping("/searchInStock")
	public String getSearchStockList(@RequestParam String type, @RequestParam String keyword,
			@RequestParam(defaultValue = "1") int pageNo, HttpSession session, Model model) {
		log.info("실행");
		WebClient webClient = WebClient.create("http://kosa1.iptime.org:50507/product");

		StockLists stocklists = webClient
				.get().uri(builder -> builder.path("/getSearchStockList").queryParam("type", type)
						.queryParam("w", keyword).queryParam("pageNo", pageNo).build())
				.retrieve().bodyToMono(StockLists.class).block();

		Pager pager = new Pager(12, 5, stocklists.getTotalRows(), pageNo);

		model.addAttribute("pager", pager);
		model.addAttribute("stocks", stocklists.getStockLists());
		model.addAttribute("keyword", keyword);
		model.addAttribute("type", type);

		return "/product/productStock";

	}

	@PostMapping("/stock/update")
	public String stockUpdate(Stock stock, Model model, HttpSession session) throws Exception {
		log.info("실행");

		WebClient webClient = WebClient.create();
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();

		map.add("pid", stock.getPid() + "");
		map.add("ccolorcode", stock.getCcolorcode() + "");
		map.add("ssize", stock.getSsize() + "");
		map.add("samount", stock.getSamount() + "");

		Stock resultStock = webClient.post().uri("http://kosa1.iptime.org:50507/product/stock/update")
				.body(BodyInserters.fromFormData(map)).retrieve().bodyToMono(Stock.class).block();

		return "redirect:/admin/product/stock/list/1";
	}

	@GetMapping("/review")
	public String getReviewList(Model model) {
		log.info("실행");

		WebClient webClient = WebClient.create();
		ReviewList reviewList = webClient.get().uri("http://kosa1.iptime.org:50507/product/reviewlist").retrieve()
				.bodyToMono(ReviewList.class).block();

		model.addAttribute("reviewList", reviewList.getReviewList());

		return "/product/productReview";
	}

	@GetMapping("/review/detail")
	public String getReview(int rno, Model model) {
		log.info("실행");

		WebClient webClient = WebClient.create();
		Review review = webClient.get().uri("http://kosa1.iptime.org:50507/product/review?rno={rno}", rno).retrieve()
				.bodyToMono(Review.class).block();

		model.addAttribute("review", review);

		return "/product/productReviewDetail";
	}

	@PostMapping("/review/answer/update")
	public String updateReviewAnswer(int rno, String content) {
		log.info("실행");

		WebClient webClient = WebClient.create();

		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();

		map.add("rno", String.valueOf(rno));
		map.add("content", content);

		Result result = webClient.post().uri("http://kosa1.iptime.org:50507/product/review/answer/update")
				.body(BodyInserters.fromFormData(map)).retrieve().bodyToMono(Result.class).block();

		log.info(result.toString());

		return "redirect:/admin/product/review/detail?rno=" + String.valueOf(rno);
	}
}