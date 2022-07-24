package com.tcontrol.DTO;

public class ClientDTO {

	private Long id;
	private Character documentType;
	private String document;
	private String names;
	private String address;
	private Character countInBs;
	private Long userId;

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

//	public Long getUserId() {
//		return userId;
//	}
//
//	public void setUserId(Long userId) {
//		this.userId = userId;
//	}
	
	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long user) {
		this.userId = user;
	}

	public Character getCountInBs() {
		return countInBs;
	}

	public void setCountInBs(Character countInBs) {
		this.countInBs = countInBs;
	}

	
}
