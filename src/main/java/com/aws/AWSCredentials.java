package com.aws;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;

public class AWSCredentials {
	String accessKeyId = "AKIAX6BWEJNDPKJUQ7GM";
	String secretAccessKey = "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";
	
	public AwsBasicCredentials credentials() {
		
		return AwsBasicCredentials.create(accessKeyId, secretAccessKey);
		
	}
}
