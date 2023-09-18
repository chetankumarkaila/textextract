package com.demo.textract.constant;

public class TextractConstant {


	public static final String REGION = "ap-southeast-1";
	public static final String SNSURL = "https://sns.ap-southeast-1.amazonaws.com";
	public static final String SQSURL = "https://sqs.ap-southeast-1.amazonaws.com";
	public static final String TEXTRACTURL = "https://textract.ap-southeast-1.amazonaws.com";
	
	public enum ResponseEnum{

		/**
		 * Common
		 */
		SUCCESS(0,"Sucess","PDF processed successfully"),
		INTERNAL_ERROR(-1,"Fail","Error while processing pdf");

		private long code;
		private String message;
		private String description;

		ResponseEnum(long code,String mesage,String description){
			this.code = code;
			this.message = mesage;
			this.description = description;
		}

		public long getCode() {
			return code;
		}

		public String getMessage() {
			return message;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public void setMessage(String message) {
			this.message = message;
		}

	}

}
