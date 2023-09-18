package com.demo.textract.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.textract.model.OCRHeaders;
import com.demo.textract.repository.OCRHeaderRepository;

@Service
@Transactional
public class OCRHeaderServiceImpl implements OCRHeaderService{

	@Autowired
	private OCRHeaderRepository ocrHeaderRepository;
	
	public OCRHeaders createOCRHeader(OCRHeaders ocrHeaders)throws Exception{
		try {
			return ocrHeaderRepository.save(ocrHeaders);
		}catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}
	}

}
