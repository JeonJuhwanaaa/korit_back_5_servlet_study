package com.study.servlet_study.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.study.servlet_study.Service.AccountService;
import com.study.servlet_study.Service.ProductService;
import com.study.servlet_study.entity.Product;

import lombok.Builder;


@WebServlet("/product")
public class ProductServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private ProductService productService;
       
    public ProductServlet() {
        super();
        productService = ProductService.getInstance();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String productName = request.getParameter("productName");
		
		Product product = productService.getProduct(productName);
		response.setStatus(200);
		response.getWriter().println(product);
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int price = 0;
		
		try {
			price = Integer.parseInt(request.getParameter("price"));
		}catch (NumberFormatException e) {
			response.setStatus(400);
			response.getWriter().println("숫자만 입력해야 합니다");
			return;
		}
		
//		String productName = request.getParameter("productName");
//		int price = Integer.parseInt(request.getParameter("price"));
//		String size = request.getParameter("size");
//		String color = request.getParameter("color");
		
		Product product = Product.builder()
				.productName(request.getParameter("productName"))
				.price(price)
				.size(request.getParameter("size"))
				.color(request.getParameter("color"))
				.build();
		
		// 중복확인 후 중복이라면 코드400 오류 뜨고 getWriter 응답
		if(productService.getProduct(product.getProductName()) != null) {
			response.setStatus(400);
			response.getWriter().println("이미 등록된 상품명입니다");
			return;
		}
		
		productService.addProduct(product);
		response.setStatus(201);
		response.getWriter().println("상품 등록이 완료되었습니다");
	
		
		
//		if(productService.getDuplication(product) == true) {
//			response.setStatus(400);
//			response.getWriter().println(0);
//		}else {
//			response.setStatus(201);
//			response.getWriter().println(1);
//		}
		
	}

}
