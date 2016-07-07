package com.inheritance;

public class CoverageForUpdate extends Coverage {

	private String previousName = "";

	/**
	 * @return the previousName
	 */
	public String getPreviousName() {
		return previousName;
	}

	/**
	 * @param previousName the previousName to set
	 */
	public void setPreviousName(String previousName) {
		this.previousName = previousName;
	}

	public String toString() {
		return(super.toString() + " previousName: " + this.previousName);
	}
}
