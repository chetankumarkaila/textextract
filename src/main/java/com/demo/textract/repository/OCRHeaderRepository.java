package com.demo.textract.repository;

import org.springframework.data.repository.CrudRepository;

import com.demo.textract.model.OCRHeaders;

public interface OCRHeaderRepository extends CrudRepository<OCRHeaders, Long>{
	
}
