package com.demo.textract.repository;

import org.springframework.data.repository.CrudRepository;

import com.demo.textract.model.OCRBatches;

public interface OCRBatchRepository extends CrudRepository<OCRBatches, Long>{
	
}
