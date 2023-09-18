package com.demo.textract.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.textract.constant.TextractConstant.ResponseEnum;
import com.demo.textract.model.OCRBatches;
import com.demo.textract.model.OCRHeaders;
import com.demo.textract.model.OCRInvoice;
import com.demo.textract.model.PdfDto;
import com.demo.textract.processor.PDFDocumentProcessor;
import com.demo.textract.response.PDFResponse;
import com.demo.textract.service.OCRBatchService;
import com.demo.textract.service.OCRHeaderService;
import com.demo.textract.service.OCRInvoiceService;
import com.demo.textract.utils.ResponseUtils;

@RestController
@RequestMapping(value={"/textract"})
public class TextractController {

	private final static Logger LOGGER = LoggerFactory.getLogger(TextractController.class);
	
	@Autowired
	private OCRInvoiceService ocrInvoiceService;
	
	@Autowired
	private OCRBatchService ocrBatchService;
	
	@Autowired
	private OCRHeaderService ocrHeaderService;
	
	@GetMapping(value="/processpdf")
	@CrossOrigin
	public ResponseEntity<PDFResponse> processPDFFile(HttpServletRequest req){
		LOGGER.debug("processing PDF: {} ", req);

		try {
			
			String document = req.getParameter("fileName");
			
			if(document == null || document.isEmpty()) {
				document = "60143456.pdf";
			}

			List<OCRInvoice> listOCRInvoice = prepareOCRInvoiceList(document);
			LOGGER.debug("OCR Invoice to persist : {}",listOCRInvoice);
			
			if(listOCRInvoice != null && !listOCRInvoice.isEmpty()) {
				for(OCRInvoice ocrInvoice: listOCRInvoice) {
					ocrInvoiceService.createOCRInvoice(ocrInvoice);
				}
			}
			
			ocrBatchService.createOCRBatches(getOCRBatch());
			ocrHeaderService.createOCRHeader(getOCRHeader());
			
			return new ResponseEntity<PDFResponse>(ResponseUtils.generatePDFResponse(ResponseEnum.SUCCESS), HttpStatus.OK);
		}catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<PDFResponse>(ResponseUtils.generatePDFResponse(ResponseEnum.INTERNAL_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	

	@PostMapping(value="/processpdf")
	@CrossOrigin
	public ResponseEntity<PDFResponse> processPDFFile(@RequestBody(required = false) PdfDto pdfDTO){
		LOGGER.debug("processing PDF: {} ", pdfDTO);

		try {
			
			String document = "60143456.pdf";
			
			if(pdfDTO != null && !StringUtils.isEmpty(pdfDTO.getFileName()) && pdfDTO.getFileName().endsWith(".pdf")) {
				document = pdfDTO.getFileName();
			}

			List<OCRInvoice> listOCRInvoice = prepareOCRInvoiceList(document);
			LOGGER.debug("OCR Invoice to persist : {}",listOCRInvoice);
			
			if(listOCRInvoice != null && !listOCRInvoice.isEmpty()) {
				for(OCRInvoice ocrInvoice: listOCRInvoice) {
					ocrInvoiceService.createOCRInvoice(ocrInvoice);
				}
			}
			
			
			
			return new ResponseEntity<PDFResponse>(ResponseUtils.generatePDFResponse(ResponseEnum.SUCCESS), HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<PDFResponse>(ResponseUtils.generatePDFResponse(ResponseEnum.INTERNAL_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	private List<OCRInvoice> prepareOCRInvoiceList(String document){
		PDFDocumentProcessor documentProcessor = new PDFDocumentProcessor();
		Map<Integer, Map<Integer, String>> data = documentProcessor.processTable(document);
		List<OCRInvoice> ocrInvoiceList = new ArrayList<OCRInvoice>();
		
		if(data != null && !data.isEmpty()) {
			for(Integer d : data.keySet()) {
				Map<Integer, String> columnData = data.get(d);
				OCRInvoice ocrInvoice = new OCRInvoice();
				for(Integer c : columnData.keySet()) {
					switch (c) {
					case 1:
						ocrInvoice.setTariffCode(columnData.get(c));
						break;
					case 2:
						ocrInvoice.setDescription(columnData.get(c));
						break;
					case 3:
						ocrInvoice.setQty(columnData.get(c));
						break;
					case 4:
						ocrInvoice.setUnitRate(columnData.get(c));
						break;
					case 5:
						ocrInvoice.setLineAmount(columnData.get(c));
						break;
					default:
						break;
					}
				}
				ocrInvoice.setCreatedBy("shyam@seertree.com");
				ocrInvoice.setLastUpdatedBy("shyam@seertree.com");
				ocrInvoice.setCreatedDate(new Timestamp(new Date().getTime()));
				ocrInvoice.setLastUpdatedDate(new Timestamp(new Date().getTime()));
				ocrInvoiceList.add(ocrInvoice);
			}
		}
		return ocrInvoiceList;
	}
	
	
	private OCRHeaders getOCRHeader() {
		OCRHeaders ocrHeaders = new OCRHeaders();
		ocrHeaders.setInvoiceNumber("60143485");
		ocrHeaders.setInvoiceDate(new java.sql.Date(new Date().getTime()));;
		ocrHeaders.setServiceDescription("Other storage and warehousing services");
		ocrHeaders.setServiceAccountingCode("996729");
		ocrHeaders.setPaymentTerms("Immediate");
		ocrHeaders.setPoReference("");
		ocrHeaders.setReceipientGstNumber("32AAFCS1591Q1ZX");
		ocrHeaders.setCreatedBy("shyam@seertree.com");
		ocrHeaders.setLastUpdatedBy("shyam@seertree.com");
		ocrHeaders.setCreatedDate(new Timestamp(new Date().getTime()));
		ocrHeaders.setLastUpdatedDate(new Timestamp(new Date().getTime()));
		return ocrHeaders;
	}

	
	private OCRBatches getOCRBatch() {
		OCRBatches ocrBatches = new OCRBatches();
		ocrBatches.setBusinessUnit("AVLL KOCHI BU");
		ocrBatches.setSupplierName("INDIA GATEWAY TERMINAL PVT LTD");
		ocrBatches.setSupplierSiteName("AVLL KOCHI BU");
		ocrBatches.setSuplierGstNum("32AABCI3293F1ZV");
		ocrBatches.setBatchDate(new java.sql.Date(new Date().getTime()));
		ocrBatches.setControlTotal("1");
		ocrBatches.setControlAmount("29332");
		ocrBatches.setCreatedBy("shyam@seertree.com");
		ocrBatches.setLastUpdatedBy("shyam@seertree.com");
		ocrBatches.setCreatedDate(new Timestamp(new Date().getTime()));
		ocrBatches.setLastUpdatedDate(new Timestamp(new Date().getTime()));
		return ocrBatches;
	}
	
}
