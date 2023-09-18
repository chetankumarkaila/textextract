package com.demo.textract.model;

public class PdfDto {

	private String fileName;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public String toString() {
		return "PdfDto [fileName=" + fileName + "]";
	}
	
}
