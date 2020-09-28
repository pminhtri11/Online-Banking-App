package com.green.bank.util;

import java.io.File;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class FileLocationContextListener implements ServletContextListener{

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		
		String rootPath = System.getProperty("catalina.home");
    	ServletContext ctx = sce.getServletContext();
    	String relativePath = ctx.getInitParameter("fileDirectory");
    	File file = new File(rootPath + File.separator + relativePath);
    	if(!file.exists()) file.mkdirs();
    	System.out.println("File Directory created to be used for storing files");
    	ctx.setAttribute("FILES_DIR_FILE", file);
    	ctx.setAttribute("FILES_DIR", rootPath + File.separator + relativePath);
    	
//    	System.out.println("FILES_DIR_FILE: " + ctx.getAttribute("FILES_DIR_FILE"));
    	System.out.println("FILES_DTR: " + ctx.getAttribute("FILES_DIR"));
	}
	
}
