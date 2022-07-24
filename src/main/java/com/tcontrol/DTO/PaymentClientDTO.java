package com.tcontrol.DTO;

import java.util.Date;

public class PaymentClientDTO {

	private Long id;
	private Date paymentDate;
	private Double rateExchange;
	private Double mount;
	private String observations;
	private Long userId;
	private Long clientId;
	private Long coinId;
	private Long paymentFormId;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}
	public Double getRateExchange() {
		return rateExchange;
	}
	public void setRateExchange(Double rateExchange) {
		this.rateExchange = rateExchange;
	}
	public Double getMount() {
		return mount;
	}
	public void setMount(Double mount) {
		this.mount = mount;
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
	public Long getClientId() {
		return clientId;
	}
	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}
	public Long getCoinId() {
		return coinId;
	}
	public void setCoinId(Long coinId) {
		this.coinId = coinId;
	}
	public Long getPaymentFormId() {
		return paymentFormId;
	}
	public void setPaymentFormId(Long paymentFormId) {
		this.paymentFormId = paymentFormId;
	}
}
