package com.upload;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.aws.S3;

import software.amazon.awssdk.services.s3.S3Client;


@WebServlet("/upload-file")
@MultipartConfig(
		fileSizeThreshold = 1024*1024*2, // 2 MB
		maxFileSize = 1024*1024*5, // 5 MB
		maxRequestSize = 1024*1024*20 // 20 MB
)
public class UploadToS3 extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/s3/upload.html").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String requestedFileName = request.getParameter("newFileName");
		Part filePart = request.getPart("file");
		String originalFileName = filePart.getSubmittedFileName();
		Long fileSize = filePart.getSize();
		
		String message = "";
		try {
			S3.uploadFile(originalFileName, filePart.getInputStream());
			message = "The file has been uploaded to AWS S3 successfully";
		}catch(Exception e) {
			message = "Error in uploading file." + "<br/>" + e.getMessage();
		}
		
		
		PrintWriter out = response.getWriter();
		out.println(message);
	}
}
