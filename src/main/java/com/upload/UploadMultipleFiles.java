package com.upload;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.aws.S3;
import com.database.DBConDao;

@WebServlet("/upload-multiple-files")
@MultipartConfig(
		fileSizeThreshold = 1024*1024*2, // 2 MB
		maxFileSize = 1024*1024*5, // 5 MB
		maxRequestSize = 1024*1024*20 // 20 MB
)
public class UploadMultipleFiles extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/s3/upload-multiple-files.html").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		
		List<Part> fileParts = (List<Part>) request.getParts();
		for(Part filePart : fileParts) {
			String fileName = filePart.getSubmittedFileName();
			String message = "";
			try {
				S3.uploadFile(fileName, filePart.getInputStream());
				message = fileName + " has been uploaded to AWS S3 successfully";
			}catch(Exception e) {
				message = fileName + "Error in uploading file." + "<br/>" + e.getMessage();
			}
			out.println(message);
		}
	}
	
}
