/**
 * 
 */
package com.comparatorsort;

import java.io.Serializable;

/**
 * @author pmorano
 * This class represents a plans Metal Tier per the 2014 Essential Health Benefit section
 * of Healthcare reform.
 */

public enum MetallicTier implements Serializable {

	BRONZE ("B", "Bronze"),
	SILVER ("S", "Silver"),
	GOLD ("G", "Gold"),
	PLATINUM ("P", "Platinum"),
	DEFAULT ("D", "N/A"); 
	
	/** The metal code */
	public final String code;
	
	/** The metal value */
	public final String value;
	
	/**
	 * Default Constructor
	 * @param String - the code
	 * @param String - the value
	 */
	MetallicTier (String inCode, String inValue) {
		this.code = inCode;
		this.value = inValue;
	}
	
	/**
	 * Get the metal for the specified code
	 * @param String - the code 
	 * @return MetallicTier
	 */
	public static MetallicTier forCode(String inCode) {
		MetallicTier returnValue = MetallicTier.DEFAULT;
		
		for (MetallicTier tier: MetallicTier.values()) {
			if (tier.code.equalsIgnoreCase(inCode)) {
				returnValue = tier;
				break;
			}
		}
		return returnValue;
	}
	
	public static MetallicTier forValue(String inValue) {
		MetallicTier returnValue = MetallicTier.DEFAULT;
		
		for (MetallicTier tier: MetallicTier.values()) {
			if (tier.value.equalsIgnoreCase(inValue)) {
				returnValue = tier;
				break;
			}
		}
		return returnValue;
	}
}
