package com.study.servlet_study.filter;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

@WebFilter("/*")
public class RequestCaractorEncodingFilter extends HttpFilter implements Filter {

    public RequestCaractorEncodingFilter() {
        super();

    }

	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
//		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		String[] methods = new String[] { "POST", "PUT" };
		
		//배열을 List로 변환(asList 사용)
		//왼쪽에 오른쪽이 포함하고있니? contains
		if(Arrays.asList(methods).contains(httpRequest.getMethod().toUpperCase())) {
			httpRequest.setCharacterEncoding("utf-8");
		}

		chain.doFilter(request, response);
		
		
	}


	public void init(FilterConfig fConfig) throws ServletException {

	}

}
