package com.aws;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3ClientBuilder;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

public class S3 {
	public static String BUCKET = "piyush1321";
	public static void uploadFile(String fileName, InputStream inputStream) throws S3Exception, AwsServiceException, SdkClientException, IOException {
		AWSCredentials cred = new AWSCredentials();
		AwsBasicCredentials AwsCred = cred.credentials();

    	Properties props = System.getProperties();
    	props.setProperty("aws.region", "ap-south-1");
        S3Client client = S3Client.builder().region(null)
                .credentialsProvider(StaticCredentialsProvider.create(AwsCred))
                .build();
        
        PutObjectRequest request = PutObjectRequest.builder()
                                .bucket(BUCKET)
                                .key(fileName)
                                .acl("public-read")
                                .build();
        
        client.putObject(request,
                RequestBody.fromInputStream(inputStream, inputStream.available()));
		
		
	}
	
}
