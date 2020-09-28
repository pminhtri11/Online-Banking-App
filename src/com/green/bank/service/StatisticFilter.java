package com.green.bank.service;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

@WebFilter(filterName= "TimeFilter", urlPatterns = "*")
public class StatisticFilter implements Filter {


	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		HttpServletRequest req = (HttpServletRequest) request;
		long start = System.currentTimeMillis();
		// pass the request along the filter chain
		chain.doFilter(request, response);		
		long different = System.currentTimeMillis() - start;		
		String name = "servlet";
		if (request instanceof HttpServletRequest) {
		      name = req.getRequestURI();
		}
		System.out.println(name + " took " + different + " ms");
	}
}
