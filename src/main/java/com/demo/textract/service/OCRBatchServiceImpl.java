package com.demo.textract.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.textract.model.OCRBatches;
import com.demo.textract.repository.OCRBatchRepository;

@Service
@Transactional
public class OCRBatchServiceImpl implements OCRBatchService{

	@Autowired
	private OCRBatchRepository ocrBatchRepository;
	
	public OCRBatches createOCRBatches(OCRBatches ocrBatches)throws Exception{
		try {
			return ocrBatchRepository.save(ocrBatches);
		}catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}
	}
}
