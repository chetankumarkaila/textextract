package com.demo.textract.model;


import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ocr_inv_headers")
public class OCRHeaders {

	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long sNo;
	
	@Column(name = "INVOICE_NUMBER")
	private String invoiceNumber;
	
	@Column(name = "INVOICE_DATE")
	private Date invoiceDate;
	
	@Column(name = "SERVICE_DESCRIPTION")
	private String serviceDescription;
	
	@Column(name = "SERVICE_ACCOUNTING_CODE")
	private String serviceAccountingCode;
	
	@Column(name = "PAYMENT_TERMS")
	private String paymentTerms;
	
	@Column(name = "PO_REFERENCE")
	private String poReference;
	
	@Column(name = "RECEIPIENT_GST_NUMBER")
	private String receipientGstNumber;
	
	@Column(name = "CREATION_DATE")
	private Timestamp createdDate;

	@Column(name = "LAST_UPDATED_DATE")
	private Timestamp lastUpdatedDate;

	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "LAST_UPDATED_BY")
	private String lastUpdatedBy;

	public long getsNo() {
		return sNo;
	}

	public void setsNo(long sNo) {
		this.sNo = sNo;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public String getServiceDescription() {
		return serviceDescription;
	}

	public void setServiceDescription(String serviceDescription) {
		this.serviceDescription = serviceDescription;
	}

	public String getServiceAccountingCode() {
		return serviceAccountingCode;
	}

	public void setServiceAccountingCode(String serviceAccountingCode) {
		this.serviceAccountingCode = serviceAccountingCode;
	}

	public String getPaymentTerms() {
		return paymentTerms;
	}

	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
	}

	public String getPoReference() {
		return poReference;
	}

	public void setPoReference(String poReference) {
		this.poReference = poReference;
	}

	public String getReceipientGstNumber() {
		return receipientGstNumber;
	}

	public void setReceipientGstNumber(String receipientGstNumber) {
		this.receipientGstNumber = receipientGstNumber;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public Timestamp getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Timestamp lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Override
	public String toString() {
		return "OCRHeaders [sNo=" + sNo + ", invoiceNumber=" + invoiceNumber + ", invoiceDate=" + invoiceDate
				+ ", serviceDescription=" + serviceDescription + ", serviceAccountingCode=" + serviceAccountingCode
				+ ", paymentTerms=" + paymentTerms + ", poReference=" + poReference + ", receipientGstNumber="
				+ receipientGstNumber + ", createdDate=" + createdDate + ", lastUpdatedDate=" + lastUpdatedDate
				+ ", createdBy=" + createdBy + ", lastUpdatedBy=" + lastUpdatedBy + "]";
	}
	
}
