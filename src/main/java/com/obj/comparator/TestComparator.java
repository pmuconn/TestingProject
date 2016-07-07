/*
 * Created on Oct 29, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.obj.comparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class TestComparator implements Comparator
{
    public int compare(Object o1, Object o2) {
		PackageVO p1 = (PackageVO) o1;
		PackageVO p2 = (PackageVO) o2;
		String i1,i2;

        String s1 = p1.getPackageName();
        String s2 = p2.getPackageName();
        
        if ((s1.compareTo(s2)) == 0) {
        	i1 = p1.getPackageNumber();
        	i2 = p2.getPackageNumber();
            return i1.compareTo(i2);
        } else {
            return s1.compareTo(s2);
        }
    }
  
	public static void main(String[] args) {

		List packageList = new ArrayList();
		
		/**
		 * Test the Comparator
		 */
		packageList.add(new PackageVO("Package Name A", "1"));
		packageList.add(new PackageVO("Package Name C", "2"));
		packageList.add(new PackageVO("Package Name B", "1"));
		packageList.add(new PackageVO("Package Name C", "9"));
		packageList.add(new PackageVO("Package Name D", "1"));
		packageList.add(new PackageVO("Package Name C", "0"));
		packageList.add(new PackageVO("Package Assumed", "1"));
		packageList.add(new PackageVO("Package Name C", "4"));
		packageList.add(new PackageVO("Package Name F", "1"));
		packageList.add(new PackageVO("Package Name C", "3"));
		packageList.add(new PackageVO("Package Standalone", "1"));
		
		Collections.sort(packageList, new TestComparator());
		System.out.println("Sorted: " + packageList.toString());
		
		/**
		 * Test the sorting map method.
		 */
		//Create map for actual sort.
		Map packageMap = new HashMap();
		packageMap.put("1", new PackageVO("Package Assumed", "6"));
		packageMap.put("6", new PackageVO("Package Name C", "6"));
		packageMap.put("3", new PackageVO("Package Name B", "3"));
		packageMap.put("4", new PackageVO("Package Name C", "4"));
		packageMap.put("5", new PackageVO("Package Name D", "5"));
		packageMap.put("2", new PackageVO("Package Name C", "2"));
		packageMap.put("7", new PackageVO("Package Standalone", "7"));
		packageMap.put("10", new PackageVO("Package Name C", "10"));
		packageMap.put("9", new PackageVO("Package Name F", "9"));
		packageMap.put("8", new PackageVO("Package Name C", "8"));
		packageMap.put("11", new PackageVO("Package Name G", "11"));
		packageList = sortMedicalPackageVOs(packageMap);
		System.out.println("Sorted: " + packageList.toString());


	}

	public static ArrayList sortMedicalPackageVOs(Map inPackageMap) {
	  	
		Iterator packageIterator = null;
		String packageID = "";
		PackageVO packageVO = null;
		String packageName = "";
		List packageVOList = new ArrayList();
		int sortedPosition = 0;
		ArrayList returnValue = new ArrayList();
		
		//Map used to keep track of the package ordering (Assumed first, stand alone last and everything else in the middle)
		TreeMap sortedPackageVOMap = new TreeMap();
		
		if (inPackageMap != null) {
			packageIterator = inPackageMap.keySet().iterator();
			while (packageIterator.hasNext()) {
				packageID = (String) packageIterator.next();
				packageVO = (PackageVO) inPackageMap.get(packageID);
				packageName = packageVO.getPackageName();
				if ("Package Assumed".equalsIgnoreCase(packageName)) {
					//Assumed package must be first.
					sortedPackageVOMap.put(new Integer(0), packageVO);
				} else if ("Package Standalone".equalsIgnoreCase(packageName)) {
					//Stand alone package must be last.
					sortedPackageVOMap.put(new Integer(inPackageMap.size()), packageVO);
				} else {
					packageVOList.add(packageVO);
				}
			} // End of Package Iteration.
			
			// Sort the list (if any found) by package name:
			Collections.sort(packageVOList, new TestComparator());
			
			// Put sorted List into Map of all potential packages.
			packageIterator = packageVOList.iterator();
			while (packageIterator.hasNext()) {
				sortedPosition++;
				sortedPackageVOMap.put(new Integer(sortedPosition), packageIterator.next());
			}
			
			// Get Sorted Package List.
			returnValue.addAll(sortedPackageVOMap.values());
		}

		return returnValue;

	}
	
}
