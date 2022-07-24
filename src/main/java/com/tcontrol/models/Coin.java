package com.tcontrol.models;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
@Table(name = "coins", uniqueConstraints = { @UniqueConstraint(columnNames = { "symbol" }) })
public class Coin {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 3, nullable = false)
	@Size(min = 1, max= 3, message = "Longitud minima del simbolo de moneda 1 caracter y maxima 3")
	private String symbol;

	@Size(min = 1, max= 50, message = "Ingrese la descripcion de la moneda con una longitud maxima de 50 caracteres")	
	@Column(length = 50, nullable = false)
	private String name;

	@Column(name = "calc_currency_purchase", columnDefinition = "enum('S','N')")
	private Character calcCurrencyPurchase;

	@Column(name = "calc_currency_sale", columnDefinition = "enum('S','N')")
	private Character calcCurrencySale;

	@Column(name = "base_currency", columnDefinition = "enum('S','N')")
	private Character baseCurrency;

	@Column(columnDefinition = "enum('Activo','Inactivo') default 'Activo'")
	private String status;

	// Esta debe ser la definicion pero no la hace no se porque private Status
	// status;

	@Column(name = "created_at")
	private LocalDateTime createdAt;
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
	@JsonProperty(access = Access.WRITE_ONLY)
	private User user;

//	@Column(name = "user_id")
//	private Long userId;

	public Coin() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Character getCalcCurrencyPurchase() {
		return calcCurrencyPurchase;
	}

	public void setCalcCurrencyPurchase(Character calcCurrencyPurchase) {
		this.calcCurrencyPurchase = calcCurrencyPurchase;
	}

	public Character getCalcCurrencySale() {
		return calcCurrencySale;
	}

	public void setCalcCurrencySale(Character calcCurrencySale) {
		this.calcCurrencySale = calcCurrencySale;
	}

	public Character getBaseCurrency() {
		return baseCurrency;
	}

	public void setBaseCurrency(Character baseCurrency) {
		this.baseCurrency = baseCurrency;
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

}
