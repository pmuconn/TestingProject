/**
 ****************************************************************
 * Copyright UNITEDHEALTH GROUP CORPORATION <2011>.
 * This software and documentation contain confidential and
 * Unauthorized use and distribution are prohibited.
 * proprietary information owned by UnitedHealth Group Corporation. 
 *****************************************************************
*/
package com.teaas.utils;

/**
 * Defines a {@link TransactionMessage} type.</br>
 * Types are defined as:</br>
 * <ul>
 * 		Error</br>
 * 		Info</br>
 * 		Warning</br>
 * 		Other
 * </ul>
 */
public enum TransactionMessageType {

	CRITICAL ("Critical"),
	ERROR ("Error"),
	INFO ("Info"),
	WARNING ("Warning"),
	OTHER ("Other");
	
	/** The message type value */
	public final String value;
	
	
	/**
	 * Default Constructor
	 * @param String - the value
	 */
	TransactionMessageType(String inValue) {
		this.value = inValue;
	}
	
	
	/**
	 * Get the {@link TransactionMessageType} for the specified value
	 * @param String - the value
	 * @return MessageType
	 */
	public static TransactionMessageType forValue(String inValue) {
		TransactionMessageType returnValue = TransactionMessageType.ERROR;
		
		for (TransactionMessageType messageType: TransactionMessageType.values()) {
			if (messageType.value.equalsIgnoreCase(inValue)) {
				returnValue = messageType;
				break;
			}
		}
		
		return returnValue;
	}
	
}
