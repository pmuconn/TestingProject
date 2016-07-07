package com.teaas.utils;

import java.util.ArrayList;
import java.util.List;

public class XpathData {
	private String xpath = "";
	private String value = "";
	private String xpathShort = "";
	private String name = "";
	private List<XpathData> attributes = new ArrayList<XpathData>();
	/**
	 * @return the xpath
	 */
	public String getXpath() {
		return xpath;
	}
	/**
	 * @param xpath the xpath to set
	 */
	public void setXpath(String xpath) {
		this.xpath = xpath;
	}
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	/**
	 * @return the xpathShort
	 */
	public String getXpathShort() {
		return xpathShort;
	}
	/**
	 * @param xpathShort the xpathShort to set
	 */
	public void setXpathShort(String xpathShort) {
		this.xpathShort = xpathShort;
	}
	/**
	 * @return the attributes
	 */
	public List<XpathData> getAttributes() {
		return attributes;
	}
	/**
	 * @param attributes the attributes to set
	 */
	public void setAttributes(List<XpathData> attributes) {
		this.attributes = attributes;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	public XpathData getAttribute(String name) {
		XpathData adata = null;
		for (XpathData data : this.attributes) {
			if ( data.getName().equalsIgnoreCase(name)) {
				adata = data;
			}
		}
		return adata;
	}
	
	public boolean isAttribute() {
		if (this.xpath.contains("@")) {
			//@ is now xpath denotes an attribute.
			return true;
		} else {
			return false;
		}
	}
	
	public boolean hasAttributes() {
		if (this.attributes.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}
	
}
