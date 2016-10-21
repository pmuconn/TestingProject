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
 * Holds any message information for document load transations
 */
public class TransactionMessage {
	
	/**
	 * The type of this {@link TransactionMessage}
	 * @see TransactionMessageType
	 */
	private TransactionMessageType type = TransactionMessageType.INFO;
	
	/**
	 * The description of this {@link TransactionMessage}
	 */
	private String desc = "";

	
	/**
	 * Default Constructor
	 */
	TransactionMessage() { }
	
	TransactionMessage(TransactionMessageType type, String description) { 
		this.type = type;
		this.desc = description;
	}
	
	/**
	 * @return String - {@link #desc}
	 */
	public String getDesc() {
		return this.desc;
	}

	/**
	 * @return MessageType - {@link #type}
	 */
	public TransactionMessageType getType() {
		return this.type;
	}

	/**
	 * Determines if this Message type is the supplied {@link TransactionMessageType}.
	 * @param String - Message type (i.e. Error)
	 */
	public boolean isMessageType(TransactionMessageType inMessageType) {
		return (this.type.equals(inMessageType));
	}

	/**
	 * @param String - {@link #desc}
	 */
	public void setDesc(String inDesc) {
		this.desc = inDesc;
	}

	/**
	 * @param TransactionMessageType - {@link #type}
	 */
	public void setType(TransactionMessageType inMessageType) {
		this.type = inMessageType;
	}

	/**
	 * @param String - {@link #type}
	 */
	public void setType(String inType) {
		this.type = TransactionMessageType.forValue(inType);
	}

	/**
	 * Override the default toString method and format this
	 * class's string format for console and logging purposes
	 * @see THUtils#objectToString(Object)
	 * @return String - this class's string representation
	 */
	public String toString() {
		return THUtils.objectToString(this);
	}

}