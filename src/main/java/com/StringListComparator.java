/*
 * Created on Oct 29, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class StringListComparator implements Comparator
{
    List masterList = new ArrayList();
  /**
     * @param inMaster
     */
    public StringListComparator(List inMaster) {
        this.masterList = inMaster;
    }

// TO USE THIS:
  //   Use the static "sort" method from the java.util.Collections class:
  //
  //   Collections.sort(your list, new AlphanumComparator());
  //
    public int compare(Object o1, Object o2) {
        int indexObject1 = 0;
        int indexObject2 = 0;
        int returnValue = 0;
        
		String s1 = (String) o1;
		String s2 = (String) o2;

		//String aNumber = p1.getPolicy().getProposalPolicy();
		//String bNumber = p2.getPolicy().getProposalPolicy();
		
		//loop thru the master list and get 
		indexObject1 = this.masterList.indexOf(s1);
		indexObject2 = this.masterList.indexOf(s2);
		
		//return indexObject1.compareToIgnoreCase(indexObject2);
		if (indexObject1 < indexObject2 ) {
		    returnValue = -1;
		}
		if (indexObject1 == indexObject2 ) {
		    returnValue = 0;
		}
		if (indexObject1 > indexObject2 ) {
		    returnValue = +1;
		}

		return returnValue;
    }
  
	public static void main(String[] args) {
//		String[] unsorted = new String[] {"P1","P4","P2","P3","P5"};
		String[] unsorted = new String[] {"P1","P4","P3","P5"};
		String[] sortedMaster = new String[] {"P1","P4","P2","P3","P5","P10"};

		List orig = Arrays.asList(unsorted);
		List master = Arrays.asList(sortedMaster);

		System.out.println("Original: " + orig);
		System.out.println("Master: " + master);

		List scrambled = Arrays.asList(unsorted);
		Collections.shuffle(scrambled);

		System.out.println("Scrambled: " + scrambled);

		Collections.sort(scrambled, new StringListComparator(master));

		System.out.println("Sorted: " + scrambled);
		
		
		//can it work with maps
		Map<String, String> map = new LinkedHashMap();
		map.put("P2", "TWO");
		map.put("P1", "ONE");
		map.put("P5", "FIVE");
		map.put("P3", "THREE");
		map.put("P4", "FOUR");
		List sortedKeys=new ArrayList(map.keySet());
		System.out.println("mapkey list before: "+ sortedKeys);
		Collections.sort(sortedKeys,new StringListComparator(master));
		System.out.println("mapkey list after: "+ sortedKeys);
		
		
	}

}
