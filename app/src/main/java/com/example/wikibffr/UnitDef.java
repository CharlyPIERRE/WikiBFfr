package com.example.wikibffr;

import java.io.Serializable;

@SuppressWarnings("serial")
public class UnitDef implements Serializable {
	private String no;
	private String nom;
	private String rarete;
	private String element;
	private String nvmax;
	private String cout;
	private String SiteNumber;


	public UnitDef(){}

	public UnitDef(String no, String nom, String rarete, String element, String nvmax, String cout, String SiteNumber) {
		this.no = no;
		this.nom = nom;
		this.rarete = rarete;
		this.element = element;
		this.nvmax = nvmax;
		this.cout = cout;
		this.SiteNumber = SiteNumber;
	}

	public String getNo() {
		return this.no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getRarete() {
		return this.rarete;
	}

	public void setRarete(String rarete) {
		this.rarete = rarete;
	}

	public String getElement() {
		return this.element;
	}

	public void setElement(String element) {
		this.element = element;
	}

	public String getNvMax() {
		return this.nvmax;
	}

	public void setNvMax(String nvmax) {
		this.nvmax = nvmax;
	}

	public String getCout() {
		return this.cout;
	}

	public void setCout(String cout) {
		this.cout = cout;
	}
	
	public void setSiteNumber(String SiteNumber) {
		this.SiteNumber = SiteNumber;
	}
	
	public String getSiteNumber() {
		return this.SiteNumber;
	}
	

}