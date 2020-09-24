/*
 * package com.green.bank.service;
 * 
 * import java.io.IOException;
 * 
 * import javax.servlet.Filter; import javax.servlet.FilterChain; import
 * javax.servlet.ServletException; import javax.servlet.ServletRequest; import
 * javax.servlet.ServletResponse; import javax.servlet.annotation.WebFilter;
 * import javax.servlet.http.HttpServletRequest;
 * 
 * @WebFilter(urlPatterns = "*") public class LoginFilter implements Filter{
 * 
 * @Override public void doFilter(ServletRequest request, ServletResponse
 * response, FilterChain chain) throws IOException, ServletException {
 * HttpServletRequest req= (HttpServletRequest) request;
 * if(req.getSession().getAttribute("username")!=null ) {
 * chain.doFilter(request, response);//go ahead
 * System.out.println("Response being sent..");//called when the response if
 * coming back }else { req.getRequestDispatcher("login").forward(request,
 * response); }
 * 
 * }
 * 
 * }
 */