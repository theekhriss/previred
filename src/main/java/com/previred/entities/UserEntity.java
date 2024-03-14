package com.previred.entities;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "usuarios")
public class UserEntity implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4987776322646160236L;
	private String id;
	private String clave;
	@Id
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Column(name = "clave", nullable = false)
	public String getClave() {
		return clave;
	}
	public void setClave(String clave) {
		this.clave = clave;
	}
	
	public UserEntity(String id, String clave) {
		this.id = id;
		this.clave = clave;
	}
	public UserEntity() {
		
	}
	
	

}
