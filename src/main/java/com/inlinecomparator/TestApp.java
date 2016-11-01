package com.inlinecomparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TestApp {

	public static void main(String[] args) {
		
		List<SomeVO> someList = new ArrayList<SomeVO>();
		
		someList.add(new SomeVO("IO","IO DESC") );
		someList.add(new SomeVO("UF","UF DESC") );
		someList.add(new SomeVO("BC","BC DESC") );
		someList.add(new SomeVO("AA","AA DESC") );
		someList.add(new SomeVO("WW","WW DESC") );

		System.out.println("List before: " + someList);
        Collections.sort(someList, new Comparator<SomeVO>() {
            public int compare(SomeVO k1, SomeVO k2) {
                return k1.getDescription().compareTo(k2.getDescription());
            }
        });
		System.out.println("List after: " + someList);

	}

}
