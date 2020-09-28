package com.green.bank;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.time.LocalDateTime;

import javax.mail.Session;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.green.bank.model.AccountModel;

@WebServlet(urlPatterns = "/manageFile")
@MultipartConfig(
		fileSizeThreshold = 1024*1024*10, 	// 10 MB 
		maxFileSize = 1024*1024*50,      	// 50 MB
		maxRequestSize = 1024*1024*100) 
public class FileServlet extends HttpServlet {

	private static final long serialVersionUID = -8068039220352106263L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();		
		String fileName = (String) session.getAttribute("fileName");
		String path = (String) getServletContext().getAttribute("FILES_DIR");
		
		InputStream in = new FileInputStream(path + File.separator + fileName);
		resp.setContentType("application/octet-stream");
		resp.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		
		PrintWriter writer = resp.getWriter();
		int i;   
		while ((i=in.read()) != -1) {  
			writer.write(i);   
		}   
		in.close(); 
		System.out.println("File downloaded at client successfully"); 
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		AccountModel accountDetails = (AccountModel) request.getSession().getAttribute("userDetails");
		String username = accountDetails.getFName() + accountDetails.getLName();
		String fileName = username + "-" + request.getParameter("fileType");
		File directory = (File) getServletContext().getAttribute("FILES_DIR_FILE");		
		
        //Get all the parts from request and write it to the file on server
        for (Part part : request.getParts()) {
        	String fullName = directory + File.separator + fileName;
            part.write(fullName);
            System.out.println("File uploaded: " + fullName);
        } 

        if (fileName.equals(username + "-" + "Passport")) {
        	 session.setAttribute("Passport", fileName);
        }
        if (fileName.equals(username + "-" + "Address")) {
        	session.setAttribute("Address", fileName);
        }
        if (fileName.equals(username + "-" + "SSN")) {
        	session.setAttribute("SSN", fileName);
        }
        if (fileName.equals(username + "-" + "Check")) {
        	session.setAttribute("Check", fileName);
        }
        
       
        getServletContext().getRequestDispatcher("/profile.jsp").forward(request, response);
	}
}
