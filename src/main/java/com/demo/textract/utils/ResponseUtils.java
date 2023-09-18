package com.demo.textract.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.demo.textract.response.PDFResponse;

public class ResponseUtils {

	private final static Logger LOGGER = LoggerFactory.getLogger(ResponseUtils.class);
	
	public static PDFResponse generatePDFResponse(com.demo.textract.constant.TextractConstant.ResponseEnum responseEnum) {
		PDFResponse pdfResponse = new PDFResponse();
		pdfResponse.setCode(responseEnum.getCode());
		pdfResponse.setMessage(responseEnum.getMessage());
		pdfResponse.setDescription(responseEnum.getDescription());
		return pdfResponse;
	}
	
}
