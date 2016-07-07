package com.enums;

public class TestEnum {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String med1 = "M";
		String med2 = "MED";
		CoverageType ctMed1 = CoverageType.MEDICAL;

		System.out.println("ctMed1 initialized name: "+ ctMed1.name() +" code: " + ctMed1.code + " value: " + ctMed1.value);

		
		ctMed1 = CoverageType.forCode(med1);
		System.out.println("ctMed1 name: "+ ctMed1.name() +" code: " + ctMed1.code + " value: " + ctMed1.value);
		
		CoverageType ctMed2 = CoverageType.forCode(med2);
		System.out.println("ctMed2 name: "+ ctMed1.name() +" code: " + ctMed2.code + " value: " + ctMed2.value);
		System.out.println("ctMed1 name: "+ ctMed1.name() +" code: " + ctMed1.code + " value: " + ctMed1.value);
		
		if(ctMed1.equals(ctMed2)) {
			System.out.println("test1: Both enums are equal");
		} else {
			System.out.println("test1: Both enums are NOT equal");
		}


		if(CoverageType.MEDICAL.equals(ctMed2)) {
			System.out.println("test2: Both enums are equal");
		} else {
			System.out.println("test2: Both enums are NOT equal");
		}
	
	}

}
