package com.tcontrol.models;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
@Table(name = "clients")
public class Client {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@Column(name = "document_type", columnDefinition = "enum ('V','E','J','G','P')")
	Character documentType;

	@Size(max = 10, message="Ingrese el numero de documento(Cedula,rif u otro documento)")
	@Column(length = 10, nullable = false)
	String document;

	@Size(max = 60, min = 5, message = "El nombre debe contener un minimo de 5 caracteres y maximo de 60")
	@NotNull(message="El campo nombre no puede ser nulo")
	@Column(length = 60, nullable = false)
	String names;

	@Column(length = 250, nullable = false)
	@NotNull(message="El campo direccion no puede estar nulo")
	String address;

	@Column(columnDefinition = "float(13,2) default 0")
	Float balance;

	@Column(columnDefinition = "enum('Activo','Inactivo') default 'Activo'")
	String status;

	@Column(name = "count_in_bs", columnDefinition = "enum('S','N') default 'S'")
	Character countInBs;

	@Column(name = "created_at")
	LocalDateTime createdAt;

	@Column(name = "updated_at")
	LocalDateTime updatedAt;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id")
	@JsonProperty(access = Access.WRITE_ONLY)
	User user;

//	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL) 
//	@JoinColumn(name = "client_id", referencedColumnName = "id", nullable = false)	
//	@OneToMany(mappedBy = "client", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	
//	@OneToMany(targetEntity = Client.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//	@JoinColumn(name = "client_id", referencedColumnName = "id")	
	@OneToMany(mappedBy = "client", fetch = FetchType.LAZY, cascade = CascadeType.ALL)	
	List<Sale> sales;
	
	public Client() {
//		sales = new ArrayList<Sale>();
	}

	public void addSale(Sale sale) {
		sales.add(sale);
	}

//	public List<Sale> getSales() { Esta linea ecita que el resultado devuelva las facturas del ciente en los get
//		return sales;
//	}

	public void setSales(List<Sale> sales) {
		this.sales = sales;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Character getDocumentType() {
		return documentType;
	}

	public void setDocumentType(Character documentType) {
		this.documentType = documentType;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public String getNames() {
		return names;
	}

	public void setNames(String names) {
		this.names = names;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Float getBalance() {
		return balance;
	}

	public void setBalance(Float balance) {
		this.balance = balance;
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
	
	public Character getCountInBs() {
		return countInBs;
	}

	public void setCountInBs(Character countInBs) {
		this.countInBs = countInBs;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
