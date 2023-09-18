package com.demo.textract.model;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.textract.AmazonTextract;

public class PDFProcessorDto {


	private String sqsQueueName;
	private String snsTopicName;
	private String snsTopicArn;
	private String roleArn;
	private String sqsQueueUrl;
	private String sqsQueueArn;
	private String startJobId;
	private String bucket;
	private String document; 
	private AmazonSQS sqs;
	private AmazonSNS sns;
	private AmazonTextract textract;
	private String processType;
	private String jobId;
	
	public String getSqsQueueName() {
		return sqsQueueName;
	}
	public void setSqsQueueName(String sqsQueueName) {
		this.sqsQueueName = sqsQueueName;
	}
	public String getSnsTopicName() {
		return snsTopicName;
	}
	public void setSnsTopicName(String snsTopicName) {
		this.snsTopicName = snsTopicName;
	}
	public String getSnsTopicArn() {
		return snsTopicArn;
	}
	public void setSnsTopicArn(String snsTopicArn) {
		this.snsTopicArn = snsTopicArn;
	}
	public String getRoleArn() {
		return roleArn;
	}
	public void setRoleArn(String roleArn) {
		this.roleArn = roleArn;
	}
	public String getSqsQueueUrl() {
		return sqsQueueUrl;
	}
	public void setSqsQueueUrl(String sqsQueueUrl) {
		this.sqsQueueUrl = sqsQueueUrl;
	}
	public String getSqsQueueArn() {
		return sqsQueueArn;
	}
	public void setSqsQueueArn(String sqsQueueArn) {
		this.sqsQueueArn = sqsQueueArn;
	}
	public String getStartJobId() {
		return startJobId;
	}
	public void setStartJobId(String startJobId) {
		this.startJobId = startJobId;
	}
	public String getBucket() {
		return bucket;
	}
	public void setBucket(String bucket) {
		this.bucket = bucket;
	}
	public String getDocument() {
		return document;
	}
	public void setDocument(String document) {
		this.document = document;
	}
	public AmazonSQS getSqs() {
		return sqs;
	}
	public void setSqs(AmazonSQS sqs) {
		this.sqs = sqs;
	}
	public AmazonSNS getSns() {
		return sns;
	}
	public void setSns(AmazonSNS sns) {
		this.sns = sns;
	}
	public AmazonTextract getTextract() {
		return textract;
	}
	public void setTextract(AmazonTextract textract) {
		this.textract = textract;
	}
	public String getProcessType() {
		return processType;
	}
	public void setProcessType(String processType) {
		this.processType = processType;
	}
	public String getJobId() {
		return jobId;
	}
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

}
