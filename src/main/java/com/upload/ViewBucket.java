package com.upload;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.aws.AWSCredentials;
import com.aws.S3;
import com.database.DBConDao;
import com.mysql.cj.jdbc.result.ResultSetMetaData;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.S3Object;
import software.amazon.awssdk.services.s3.paginators.ListObjectsV2Iterable;

@WebServlet("/view-bucket")
public class ViewBucket extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		Fetch Object Key from AWS S3
//		AWSCredentials cred = new AWSCredentials();
//		AwsBasicCredentials AwsCred = cred.credentials();
//
//    	Properties props = System.getProperties();
//    	props.setProperty("aws.region", "ap-south-1");
//		S3Client client = S3Client.builder()
//				.region(null)
//				.credentialsProvider(StaticCredentialsProvider.create((AwsCred)))
//				.build();
//		
//		ListObjectsV2Request listRequest = ListObjectsV2Request
//				.builder()
//				.bucket(S3.BUCKET)
//				.prefix("")
//				.build();
//		ListObjectsV2Iterable iterateResponse = client.listObjectsV2Paginator(listRequest);
//		
//		ArrayList<String> files = new ArrayList();
//		for (ListObjectsV2Response page : iterateResponse) {
//			page.contents().forEach((S3Object object) -> {
//				files.add(object.key());
//			});
//		}
		ArrayList<Object> objects = new ArrayList();
		try {
			objects = fetchObjectInformation();
		}catch(Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("objects", objects);
		request.setAttribute("bucket", S3.BUCKET);
		request.getRequestDispatcher("/s3/view.jsp").forward(request, response);
		
		
	}
	
	public ArrayList<Object> fetchObjectInformation() throws ClassNotFoundException, SQLException {
		DBConDao dao = new DBConDao();
		Connection con = dao.connection();
		
		String sql = "SELECT * FROM `objects`";
		PreparedStatement st = con.prepareStatement(sql);

		ResultSet rs = st.executeQuery();
		ArrayList<Object> objects = new ArrayList<Object>();
		while(rs.next()) {
			Object obj = new Object();
			obj.setObjectId(rs.getInt(1));
			obj.setObjectKey(rs.getString(2));
			obj.setTitle(rs.getString(3));
			obj.setDescription(rs.getString(4));
			objects.add(obj);
		}
		
		st.close();
		con.close();
		
		return objects;
	}

}


