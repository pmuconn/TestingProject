package com.map.hashcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestCollection {

	public static void main(String[] args) {

		Map<FiscalYear, String> years = new HashMap<FiscalYear, String>();

		FiscalYear f1 = new FiscalYear(2020, "10");
		FiscalYear f2 = new FiscalYear(2021, "10");
		FiscalYear f3 = new FiscalYear(2020, "11");
		FiscalYear f4 = new FiscalYear(2021, "10");
		FiscalYear f5 = new FiscalYear(2021, "12");

		List<FiscalYear> yearTests = new ArrayList<FiscalYear>()
		{{
            add(f1);
            add(f2);
            add(f3);
            add(f4);
            add(f5);
		}};
		

		for (FiscalYear year : yearTests) {
			
			if (years.containsKey(year)) {
				
				System.out.println("Map already contains the year, " + year);
				
				
			} else {
				System.out.println("Map does not contain the year, " + year + " adding..");
				years.put(year, year.toString());
			}
		}
		
	}

}
