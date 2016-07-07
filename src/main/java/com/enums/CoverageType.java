/**
 ****************************************************************
 * Copyright UNITEDHEALTH GROUP CORPORATION <2011>.
 * This software and documentation contain confidential and
 * Unauthorized use and distribution are prohibited.
 * proprietary information owned by UnitedHealth Group Corporation. 
 *****************************************************************
*/
package com.enums;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;





/**
 * Defines a coverage type, may be 
 * different values depending on the 
 * source system being processed.
 * <p>
 * 		<b>CDB & CDM Medica:</b>
 * </p>
 * <ul>
 *		<li>TODO</li>
 * </ul>
 * <p>
 * 		<b>COSMOS:</b>
 * </p>
 * <ul>
 * 		<li>{@link #DEN}</li>
 * 		<li>{@link #LIF}</li>
 * 		<li>{@link #MED}</li>
 * </ul>
 * <p>
 * 		<b>PRIME & PRIME Medica:</b>
 * </p>
 * <ul>
 * 		<li>{@link #DENTAL}</li>
 * 		<li>{@link #LIFE}</li>
 * 		<li>{@link #LTD}</li>
 * 		<li>{@link #MEDICAL}</li>
 * 		<li>{@link #STD}</li>
 * 		<li>{@link #SUPP_LIFE}</li>
 * 		<li>{@link #VISION}</li>
 * </ul>
 * @author bwyka
 * @since Q2 2012 - Full Family
 */
public enum CoverageType {
	
	MEDICAL ("Medical","M","MED"),
	DENTAL ("Dental","D","DEN"),
	VISION ("Vision","V","VIS"),
	
	//DENTAL ("D", "Dental"),
	DEN ("DEN", DENTAL.value),
	LIFE ("L", "Life"),
	LIF ("LIF", LIFE.value),
	LTD ("LTD", "Long Term Disability"),
	//MEDICAL ("H", "Medical"),
	MED ("MED", MEDICAL.value),
	STD ("STD", "Short Term Disability"),
	SUPP_LIFE ("SUL", "Supplemental Life"),
//	VISION ("V", "Vision"),
	VIS ("VIS", VISION.value);
	
	/** The coverage type code */
	public final String code;
	
	/** The coverage type value */
	public final String value;

	public final List<String> codes;
	
	/**
	 * Default Constructor
	 * @param String - {@link #code}
	 * @param String - {@link #value}
	 */
	CoverageType(String inValue, String ...inCodes) {
		this.codes = Arrays.asList(inCodes);
		//default code to first in array
		this.value = inValue;
		this.code = codes.get(0);
	}

	
	/**
	 * Get the {@link CoverageType} for the specified code
	 * @param String - the code 
	 * @return CoverageType
	 * @throws EnumerationException - if the provided code does not exist in the enumeration
	 */
	public static CoverageType forCode(String inCode) {
		CoverageType returnValue = null;
		
		for (CoverageType coverageType: CoverageType.values()) {
			if (coverageType.codes.contains(inCode)) {
				returnValue = coverageType;
				//coverageType.code = inCode;
				setFinalCode(returnValue, inCode);
				break;
			}
			
//			if (coverageType.code.equalsIgnoreCase(inCode)) {
//				returnValue = coverageType;
//				break;
//			}
		}
		
		return returnValue;
	}
	
	
	/**
	 * Determine if the supplied {@link CoverageType} is equal
	 * @param CoverageType - the coverage to compare
	 * @return boolean - true if equal, false otherwise
	 */
	public boolean is(CoverageType inCoverageType) {
		boolean returnValue = false;
		
		returnValue = this.isMedical() && inCoverageType.isMedical();
		returnValue |= this.isDental() && inCoverageType.isDental();
		returnValue |= this.isVision() && inCoverageType.isVision();
		returnValue |= this.isLife() && inCoverageType.isLife();
		returnValue |= this.isSuppLife() && inCoverageType.isSuppLife();
		returnValue |= this.isLTD() && inCoverageType.isLTD();
		returnValue |= this.isSTD() && inCoverageType.isSTD();
		
		return returnValue;
	}
	
	/**
	 * Determine if this coverage type is BASE (not supplemental)
	 * @return boolean - true if BASE, false otherwise
	 */
	public boolean isBase() {
		return !isNonBaseType();
	}
	
	/**
	 * Determine if this coverage type is restricted to having its associated Products
	 * populated by a specialized "Classing Product" source system call.
	 * @see IndicatorKey#CLASSING_RESTRICTION_DECORATOR
	 * @return boolean - true if SUPP_LIFE, LTD or STD, false otherwise
	 */
	public boolean isClassingRestrictionDecorated() {
		boolean returnValue = false;
		
		returnValue = (this == SUPP_LIFE) || (this == LTD) || (this == STD);
		
		return returnValue;
	}
	
	/**
	 * Determine if this coverage type is "cross Status Period Decorated".
	 * @see IndicatorKey#CROSS_STATUS_PERIOD_DECORATOR
	 * @return boolean - true if SUPP_LIFE, false otherwise
	 */
	public boolean isCrossStatusPeriodDecorated() {
		boolean returnValue = false;
		
		returnValue = (this == SUPP_LIFE);
		
		return returnValue;
	}
	
	/**
	 * Determine if this coverage type is "Product selection message Decorated".
	 * When true, indicates thatwhen the selection is "being-added", a special 
	 * message must accompany the selection.
	 * @see IndicatorKey#PRODUCT_SELECTION_MESSAGE_DECORATOR
	 * @return boolean - true if SUPP_LIFE, false otherwise
	 */
	public boolean isProductSelectionMessageDecorated() {
		boolean returnValue = false;
		
		returnValue = (this == SUPP_LIFE);
		
		return returnValue;
	}
	
	/**
	 * Determine if this coverage type is dental
	 * @return boolean - true if dental, false otherwise
	 */
	public boolean isDental() {
		return (this == DENTAL || this == DEN);
	}
	
	/**
	 * Determine if this coverage type is a Disability Type.
	 * @return boolean - true if supplemental, false otherwise
	 */
	public boolean isDisability() {
		boolean returnValue = false;
		
		returnValue = (this == LTD) || (this == STD);
		
		return returnValue;
	}
	
	/**
	 * Determine if this coverage type is life
	 * @return boolean - true if life, false otherwise
	 */
	public boolean isLife() {
		return (this == LIFE || this == LIF);
	}
	
	/**
	 * Determine if this coverage type is Long-term Disability
	 * @return boolean - true if LTD, false otherwise
	 */
	public boolean isLTD() {
		return (this == LTD);
	}
	
	/**
	 * Determine if this coverage type is medical
	 * @return boolean - true if medical, false otherwise
	 */
	public boolean isMedical() {
		return (this == MEDICAL || this == MED);
	}
	
	/**
	 * Determine if this coverage type is other than a BASE Coverage Type.
	 * @return boolean - true if Non-Base type, false otherwise
	 */
	public boolean isNonBaseType() {
		boolean returnValue = false;
		
		returnValue = (this == SUPP_LIFE) || (this == LTD) || (this == STD);
		
		return returnValue;
	}
	
	/**
	 * Determine if this coverage type is decorated to ensure Provider object
	 * printing. 
	 * @return boolean - true if MEDICAL, false otherwise
	 */
	public boolean isPrintProviderDecorated() {
		return isMedical();
	}
	
	/**
	 * Determine if this coverage type is Short-term Disability
	 * @return boolean - true if STD, false otherwise
	 */
	public boolean isSTD() {
		return (this == STD);
	}
	
	/**
	 * Determine if this coverage type is restricted to first successfully being committed 
	 * to a Subscriber before any other assignment can occur. 
	 * @see IndicatorKey#SUBMISSION_RESTRICTION_DECORATOR
	 * @return boolean - true if SUPP_LIFE, false otherwise
	 */
	public boolean isSubmissionRestrictionDecorated() {
		boolean returnValue = false;
		
		returnValue = (this == SUPP_LIFE);
		
		return returnValue;
	}
	
	/**
	 * Determine if this coverage type is supplemental life.
	 * @return boolean - true if supplemental life, false otherwise
	 */
	public boolean isSuppLife() {
		return (this == SUPP_LIFE);
	}
	
	/**
	 * Determine if this coverage type is vision
	 * @return boolean - true if vision, false otherwise
	 */
	public boolean isVision() {
		return (this == VISION || this == VIS);
	}
	
	private static void setFinalCode(CoverageType inCoveragetype, Object inNewValue) {
		try {
			Field codeField = CoverageType.class.getDeclaredField("code");
			codeField.setAccessible(true);
		    codeField.set(inCoveragetype, inNewValue);
		} 
		catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}