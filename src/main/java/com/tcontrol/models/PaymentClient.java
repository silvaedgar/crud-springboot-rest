package com.tcontrol.models;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
@Table (name = "payment_clients")
public class PaymentClient {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull
	@Column(name="payment_date")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")	
	private Date paymentDate;
	
	@Positive(message = "La tasa de cambio tiene que ser mayor a 0")
	@Column(name="rate_exchange")
	private Float rateExchange;
	@Positive(message = "Monto de Pago tiene que ser mayor a 0")
	private Double mount;
	private String observations;
	@Column(columnDefinition = "enum('Procesado','Anulado','Historico') Default 'Procesado'")
	private String status;
	
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="user_id", referencedColumnName = "id")
	@JsonProperty(access = Access.WRITE_ONLY)
	private User user;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="client_id", referencedColumnName = "id")
	@JsonProperty(access = Access.WRITE_ONLY)
	private Client client;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="coin_id", referencedColumnName = "id")
	@JsonProperty(access = Access.WRITE_ONLY)
	private Coin coin;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="payment_form_id", referencedColumnName = "id")
	@JsonProperty(access = Access.WRITE_ONLY)
	private PaymentForm paymentForm;

	
	
	public PaymentClient() {
		super();
	}

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

	public Float getRateExchange() {
		return rateExchange;
	}

	public void setRateExchange(Float rateExchange) {
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Coin getCoin() {
		return coin;
	}

	public void setCoin(Coin coin) {
		this.coin = coin;
	}

	public PaymentForm getPaymentForm() {
		return paymentForm;
	}

	public void setPaymentForm(PaymentForm paymentForm) {
		this.paymentForm = paymentForm;
	}
	
}
