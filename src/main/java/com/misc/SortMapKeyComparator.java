/*
 * Created on Oct 29, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.misc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class SortMapKeyComparator implements Comparator<Object>
{
    List<String> masterList = new ArrayList<String>();
    
    public SortMapKeyComparator(List<String> inMaster) {
        this.masterList = inMaster;
    }

    public int compare(Object o1, Object o2) {
        int indexObject1 = 0;
        int indexObject2 = 0;
        int returnValue = 0;
        
		String s1 = (String) o1;
		String s2 = (String) o2;
		
		indexObject1 = this.masterList.indexOf(s1);
		indexObject2 = this.masterList.indexOf(s2);
		
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
		String[] strings = new String[] {"COL_ONE", "COL_TWO", "COL_ABC", "COL_FOUR"};

		List<String> masterList = Arrays.asList(strings);

		System.out.println("MasterList: " + masterList);
		
		Map<String,String> unsortedMap = new HashMap<String, String>();
		unsortedMap.put("COL_ABC", "COL_ABC");
		unsortedMap.put("COL_FOUR", "COL_FOUR");
		unsortedMap.put("COL_TEN", "COL_TEN");
		unsortedMap.put("COL_TWO", "COL_TWO");
		unsortedMap.put("COL_ONE", "COL_ONE");
		System.out.println("Unsorted map:" + unsortedMap);
		
		Map<String, String> sorted = new TreeMap<String, String>(new SortMapKeyComparator(masterList));
		sorted.putAll(unsortedMap);
		System.out.println("Sorted map:" + sorted);
	}

}
