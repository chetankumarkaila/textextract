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
@Table(name="ocr_inv_batches")
public class OCRBatches {

	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long batchId;
	
	@Column(name = "BUSINESS_UNIT")
	private String businessUnit;
	
	@Column(name = "SUPPLIER_NAME")
	private String supplierName;
	
	@Column(name = "SUPPLIER_SITE_NAME")
	private String supplierSiteName;
	
	@Column(name = "SUPPLIER_GST_NUM")
	private String suplierGstNum;
	
	@Column(name = "BATCH_DATE")
	private Date batchDate;
	
	@Column(name = "CONTROL_TOTAL")
	private String controlTotal;
	
	@Column(name = "CONTROL_AMOUNT")
	private String controlAmount;
	
	@Column(name = "CREATION_DATE")
	private Timestamp createdDate;

	@Column(name = "LAST_UPDATED_DATE")
	private Timestamp lastUpdatedDate;

	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "LAST_UPDATED_BY")
	private String lastUpdatedBy;

	public long getBatchId() {
		return batchId;
	}

	public void setBatchId(long batchId) {
		this.batchId = batchId;
	}

	public String getBusinessUnit() {
		return businessUnit;
	}

	public void setBusinessUnit(String businessUnit) {
		this.businessUnit = businessUnit;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getSupplierSiteName() {
		return supplierSiteName;
	}

	public void setSupplierSiteName(String supplierSiteName) {
		this.supplierSiteName = supplierSiteName;
	}

	public String getSuplierGstNum() {
		return suplierGstNum;
	}

	public void setSuplierGstNum(String suplierGstNum) {
		this.suplierGstNum = suplierGstNum;
	}

	public Date getBatchDate() {
		return batchDate;
	}

	public void setBatchDate(Date batchDate) {
		this.batchDate = batchDate;
	}
	
	public String getControlTotal() {
		return controlTotal;
	}

	public void setControlTotal(String controlTotal) {
		this.controlTotal = controlTotal;
	}

	public String getControlAmount() {
		return controlAmount;
	}

	public void setControlAmount(String controlAmount) {
		this.controlAmount = controlAmount;
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
		return "OCRBatches [batchId=" + batchId + ", businessUnit=" + businessUnit + ", supplierName="
				+ supplierName + ", supplierSiteName=" + supplierSiteName + ", suplierGstNum=" + suplierGstNum
				+ ", batchDate=" + batchDate + ", controlTotal=" + controlTotal + ", controlAmount=" + controlAmount
				+ ", createdDate=" + createdDate + ", lastUpdatedDate=" + lastUpdatedDate + ", createdBy=" + createdBy
				+ ", lastUpdatedBy=" + lastUpdatedBy + "]";
	}
	
}
