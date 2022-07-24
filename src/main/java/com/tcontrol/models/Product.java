package com.tcontrol.models;


import java.time.LocalDateTime;
import javax.persistence.*;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
@Table(name = "products", uniqueConstraints = {@UniqueConstraint (columnNames = {"code"})})
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Size(max=20, min=3, message = "Longitud de Codigo de Producto minima de 3 caracteres y maxima de 20")
	@Column(length = 20, nullable = false)
	private String code;
	@Size(max=60, min=3, message = "Longitud de Descripcion de Producto minima de 3 caracteres y maxima de 60")	
	@Column(length = 60, nullable = false)
	private String name;
	@PositiveOrZero(message = "El costo del Producto tiene que ser mayor a 0")
	@Column(name="cost_price")
	private Double costPrice;
	@Column(name="avg_cost")	
	private Double avgCost;
	@PositiveOrZero(message = "El precio de venta del Producto tiene que ser mayor a 0")	
	@Column(name="sale_price")
	private Double salePrice;
	private Double stock;
	@Column(columnDefinition = "enum('Activo','Inactivo') default 'Activo'")
	private String status;

	@Column(name = "tax_id")
	private Long taxId;
	
	@Column(name="created_at")
	private LocalDateTime createdAt;
	@Column(name="updated_at")
	private LocalDateTime updatedAt;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
	@JsonProperty(access = Access.WRITE_ONLY)
	private User user;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false)
	@JsonProperty(access = Access.WRITE_ONLY)
	private ProductCategory productCategory;

	public Product() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(Double costPrice) {
		this.costPrice = costPrice;
	}

	public Double getAvgCost() {
		return avgCost;
	}

	public void setAvgCost(Double avgCost) {
		this.avgCost = avgCost;
	}

	public Double getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(Double salePrice) {
		this.salePrice = salePrice;
	}

	public Double getStock() {
		return stock;
	}

	public void setStock(Double stock) {
		this.stock = stock;
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


	public ProductCategory getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}
	
	public Long getTaxId() {
		return taxId;
	}


	public void setTaxId(Long taxId) {
		this.taxId = taxId;
	}

}
