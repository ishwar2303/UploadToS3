package com.upload;

import java.io.IOException;
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
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;

@WebServlet("/delete-object")
public class DeleteObject extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		AWSCredentials cred = new AWSCredentials();
		AwsBasicCredentials AwsCred = cred.credentials();

    	Properties props = System.getProperties();
    	props.setProperty("aws.region", "ap-south-1");
		S3Client client = S3Client.builder()
				.region(null)
				.credentialsProvider(StaticCredentialsProvider.create((AwsCred)))
				.build();

		String objectKey = request.getParameter("objectKey");
		
		DeleteObjectRequest delRequest = DeleteObjectRequest
				.builder()
				.bucket(S3.BUCKET)
				.key(objectKey)
				.build();
		client.deleteObject(delRequest);
		
		response.sendRedirect("delete-object");
	}

}
