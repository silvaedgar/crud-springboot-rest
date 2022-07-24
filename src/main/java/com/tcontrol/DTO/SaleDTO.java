package com.tcontrol.DTO;

import java.util.Date;
import java.util.List;


public class SaleDTO {
	
	private String invoice;
	private Double rateExchange;
	private Date date;
	private String conditions;
	private String observations;
	private Long userId;
	private Long coinId;
	private Long clientId;
	private List<SaleDetailDTO> itemsSaleDetail;
	

	public SaleDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	public List<SaleDetailDTO> getItemsSaleDetail() {
		return itemsSaleDetail;
	}

	public void setItemsSaleDetail(List<SaleDetailDTO> itemsSaleDetail) {
		this.itemsSaleDetail = itemsSaleDetail;
	}

	public Double getRateExchange() {
		return rateExchange;
	}

	public void setRateExchange(Double rateExchange) {
		this.rateExchange = rateExchange;
	}

	public String getInvoice() {
		return invoice;
	}

	public void setInvoice(String invoice) {
		this.invoice = invoice;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getConditions() {
		return conditions;
	}

	public void setConditions(String conditions) {
		this.conditions = conditions;
	}

	public String getObservations() {
		return observations;
	}

	public void setObservations(String observations) {
		this.observations = observations;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getCoinId() {
		return coinId;
	}

	public void setCoinId(Long coinId) {
		this.coinId = coinId;
	}

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	
}
