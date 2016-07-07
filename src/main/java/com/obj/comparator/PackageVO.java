package com.obj.comparator;

public class PackageVO {
	private String packageName = "";
	private String packageNumber = "";
	
	public PackageVO(String name, String number) {
		this.setPackageName(name);
		this.setPackageNumber(number);
	}
	
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public String getPackageNumber() {
		return packageNumber;
	}
	public void setPackageNumber(String packageNumber) {
		this.packageNumber = packageNumber;
	}
	
	public String toString() {
		return (this.getPackageName() + " " + this.getPackageNumber());
	}
}
