package com.upload;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.aws.S3;
import com.database.DBConDao;

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
		String title = request.getParameter("title");
		String description = request.getParameter("description");
		Part filePart = request.getPart("file");
		String originalFileName = filePart.getSubmittedFileName();
		Long fileSize = filePart.getSize();
		
		String message = "";
		try {
			S3.uploadFile(originalFileName, filePart.getInputStream());
			saveObjectInformation(originalFileName, title, description);
			message = "The file has been uploaded to AWS S3 successfully";
		}catch(Exception e) {
			message = "Error in uploading file." + "<br/>" + e.getMessage();
		}
		
		
		PrintWriter out = response.getWriter();
		out.println(message);
	}

	public boolean saveObjectInformation(String objectKey, String title, String description) throws ClassNotFoundException, SQLException {
		DBConDao dao = new DBConDao();
		Connection con = dao.connection();
		
		String sql = "INSERT INTO `objects` VALUES (NULL, ?, ?, ?)";
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, objectKey);
		st.setString(2, title);
		st.setString(3, description);
		
		Integer count = st.executeUpdate();
		
		st.close();
		con.close();
		return count > 0 ? true : false;
	}
}
