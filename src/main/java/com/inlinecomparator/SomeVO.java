package com.inlinecomparator;

public class SomeVO {
	
	private String itemCode = "";
	private String description = "";
	
	public SomeVO(String item, String desc) {
		this.itemCode = item;
		this.description = desc;
	}
	
	public String getItemCode() {
		return this.itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public String getDescription() {
		return this.description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String toString() {
		return this.description;
	}
	
}
