package com.tcontrol.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
@Table(name = "sales")
public class Sale {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	@Column(name = "rate_exchange", columnDefinition = "float(12,4) ")
	Double rateExchange;
	
	@Column(name = "sale_date")	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	Date date;
	@Size(max=10, message="Longitud maxima de la factura es 10")
	String invoice;
	@Column(columnDefinition = "float(12,2) ")
	Double mount;	
	@Column(name = "tax_mount", columnDefinition = "float(12,2) ")
	Double taxMount;
	@Column(name = "paid_mount", columnDefinition = "float(12,2) ")
	Double paidMount;
	@Column(columnDefinition = "enum('Credito','Contado') ")	
	String conditions;
	@Size(max=150, message="Longitud maxima de las observaciones es 150")
	String observations;
	@Column(columnDefinition = "enum('Pendiente','Parcial','Cancelada','Historico','Anulada') ")
	String status;

	@Column(name = "created_at")
	LocalDateTime createdAt;
	@Column(name = "updated_at")
	LocalDateTime updatedAt;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
	@JsonProperty(access = Access.WRITE_ONLY)
	User user;


	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "coin_id", referencedColumnName = "id", nullable = false)
	@JsonProperty(access = Access.WRITE_ONLY)	
	Coin coin;

//	@JoinColumn(name = "client_id")     opcion 2
//	@JoinColumn(name = "client_id", referencedColumnName = "id", nullable = false) opcion 2
	
	@JoinColumn(name = "client_id", nullable = true)    
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	Client client;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "sale_id", referencedColumnName = "id", nullable = false)
	List<SaleDetail> itemsSaleDetail;

	public Double calculateMount() {
		Double mount = 0.0;

		for (int i = 0; i < itemsSaleDetail.size(); i++) {
			mount += itemsSaleDetail.get(i).calculateMountItem();
		}
		return mount;
	}

	public Sale() {
		this.itemsSaleDetail = new ArrayList<SaleDetail>();
		this.client = new Client();
		this.coin = new Coin();
	}

	public void addItemSale(SaleDetail itemSaleDetail) {
		this.itemsSaleDetail.add(itemSaleDetail);
	}

	public List<SaleDetail> getItemsSaleDetail() {
		return itemsSaleDetail;
	}

	public void setItemsSaleDetail(List<SaleDetail> itemsSaleDetail) {
		this.itemsSaleDetail = itemsSaleDetail;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getInvoice() {
		return invoice;
	}

	public void setInvoice(String invoice) {
		this.invoice = invoice;
	}

	public Double getMount() {
		return mount;
	}

	public void setMount(Double mount) {
		this.mount = mount;
	}

	public Double getRateExchange() {
		return rateExchange;
	}

	public void setRateExchange(Double rateExchange) {
		this.rateExchange = rateExchange;
	}

	public Double getTaxMount() {
		return taxMount;
	}

	public void setTaxMount(Double taxMount) {
		this.taxMount = taxMount;
	}

	public Double getPaidMount() {
		return paidMount;
	}

	public void setPaidMount(Double paidMount) {
		this.paidMount = paidMount;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
