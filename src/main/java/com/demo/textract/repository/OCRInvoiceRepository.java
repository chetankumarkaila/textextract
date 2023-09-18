package com.demo.textract.repository;

import org.springframework.data.repository.CrudRepository;

import com.demo.textract.model.OCRInvoice;

public interface OCRInvoiceRepository extends CrudRepository<OCRInvoice, Long>{
	
}
