package com.upload;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aws.AWSCredentials;
import com.aws.S3;

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
		AWSCredentials cred = new AWSCredentials();
		AwsBasicCredentials AwsCred = cred.credentials();

    	Properties props = System.getProperties();
    	props.setProperty("aws.region", "ap-south-1");
		S3Client client = S3Client.builder()
				.region(null)
				.credentialsProvider(StaticCredentialsProvider.create((AwsCred)))
				.build();
		
		ListObjectsV2Request listRequest = ListObjectsV2Request
				.builder()
				.bucket(S3.BUCKET)
				.prefix("")
				.build();
		ListObjectsV2Iterable iterateResponse = client.listObjectsV2Paginator(listRequest);
		
		ArrayList<String> files = new ArrayList();
		for (ListObjectsV2Response page : iterateResponse) {
			page.contents().forEach((S3Object object) -> {
				files.add(object.key());
			});
		}
		
		request.setAttribute("objects", files);
		request.setAttribute("bucket", S3.BUCKET);
		request.getRequestDispatcher("/s3/view.jsp").forward(request, response);
		
		
	}

}


