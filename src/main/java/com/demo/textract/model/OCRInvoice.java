package com.demo.textract.model;


import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ocr_inv_lines")
public class OCRInvoice {

	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long sNo;
	
	@Column(name = "TARIFF_CODE")
	private String tariffCode;
	
	@Column(name = "DESCRIPTION")
	private String description;
	
	@Column(name = "QTY")
	private String qty;
	
	@Column(name = "UNIT_RATE")
	private String unitRate;
	
	@Column(name = "LINE_AMOUNT")
	private String lineAmount;
	
	@Column(name = "HSN_SAC")
	private String hsnSac;
	
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
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getQty() {
		return qty;
	}

	public void setQty(String qty) {
		this.qty = qty;
	}

	public String getUnitRate() {
		return unitRate;
	}

	public void setUnitRate(String unitRate) {
		this.unitRate = unitRate;
	}

	public String getLineAmount() {
		return lineAmount;
	}

	public void setLineAmount(String lineAmount) {
		this.lineAmount = lineAmount;
	}

	public String getHsnSac() {
		return hsnSac;
	}

	public void setHsnSac(String hsnSac) {
		this.hsnSac = hsnSac;
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

	public String getTariffCode() {
		return tariffCode;
	}

	public void setTariffCode(String tariffCode) {
		this.tariffCode = tariffCode;
	}

	@Override
	public String toString() {
		return "OCRInvoice [sNo=" + sNo + ", tariffCode=" + tariffCode + ", description=" + description + ", qty=" + qty
				+ ", unitRate=" + unitRate + ", lineAmount=" + lineAmount + ", hsnSac=" + hsnSac + ", createdDate="
				+ createdDate + ", lastUpdatedDate=" + lastUpdatedDate + ", createdBy="
				+ createdBy + ", lastUpdatedBy=" + lastUpdatedBy + "]";
	}
}
