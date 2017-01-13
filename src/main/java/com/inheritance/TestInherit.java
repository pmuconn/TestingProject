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
		
		//No need to cast if using Parent objects.
		Coverage c = cu;
		System.out.println("parent cov: " + c);
		
		//Need to cast to child from parent object.
		Coverage parent = new Coverage();
		parent.setName("parent name");
		cu = (CoverageForUpdate) parent;
		System.out.println("child cov: " + cu);
		
	}

}
