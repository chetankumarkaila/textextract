package com.demo.textract.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.textract.model.OCRInvoice;
import com.demo.textract.repository.OCRInvoiceRepository;

@Service
@Transactional
public class OCRInvoiceServiceImpl implements OCRInvoiceService{

	@Autowired
	private OCRInvoiceRepository ocrInvoiceRepository;
	
	public OCRInvoice createOCRInvoice(OCRInvoice ocrInvoice)throws Exception{
		try {
			return ocrInvoiceRepository.save(ocrInvoice);
		}catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}
	}

}
