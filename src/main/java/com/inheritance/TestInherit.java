package com.inheritance;

public class TestInherit {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		CoverageForUpdate cu = new CoverageForUpdate();
		cu.setName("UpdateName");
		cu.setType("M");
		cu.setPreviousName("childPrevName");
		System.out.println("child cov: " + cu);
		
		Coverage c = (Coverage)cu;
		System.out.println("parent cov: " + c);
		
	}

}
