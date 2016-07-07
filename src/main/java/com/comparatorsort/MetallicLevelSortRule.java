/**
 ****************************************************************
 * Copyright UNITEDHEALTH GROUP CORPORATION <2011>.
 * This software and documentation contain confidential and
 * Unauthorized use and distribution are prohibited.
 * proprietary information owned by UnitedHealth Group Corporation. 
 *****************************************************************
*/
package com.comparatorsort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author pmorano
 *
 *	This rule is used to sort Metallic Tiers based on a master list hard-coded within the rule.
 */
public class MetallicLevelSortRule  {
	
	/**
	 * This comparator is used to Metallics based on a master list of tiers.
	 * To use, you would use the Collections.sort as follows:
	 * Collections.sort(<YourListToBeSorted>, new StringListComparator(<TheMasterList>));
	 */
	@SuppressWarnings("unchecked")
	class StringListComparator implements Comparator
	{
	    List masterList = new ArrayList();
	    
	    public StringListComparator(List inMaster) {
	        this.masterList = inMaster;
	    }

	    public int compare(Object o1, Object o2) {
	        int indexObject1 = 0;
	        int indexObject2 = 0;
	        int returnValue = 0;

	    	MetallicTier m1 = (MetallicTier) o1;
	    	MetallicTier m2 = (MetallicTier) o2;
	        
			//loop thru the master list and get 
			indexObject1 = this.masterList.indexOf(m1);
			indexObject2 = this.masterList.indexOf(m2);
			
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
	}

	/**
	 * This represents the master list that the rule will be driven on.
	 */
	private static List<MetallicTier> metallicSortMaster = new ArrayList<MetallicTier>();

	/**
	 * Static population of metallic tiers used to enforce sorted sequence.
	 */
	static {
		// master sort metallic types
		metallicSortMaster.add(MetallicTier.PLATINUM); 
		metallicSortMaster.add(MetallicTier.GOLD);
		metallicSortMaster.add(MetallicTier.SILVER);
		metallicSortMaster.add(MetallicTier.BRONZE);
	}

	/**
	 * The metallic tier objects to be sorted.
	 */
	private List<MetallicTier> metallicTiers = new ArrayList<MetallicTier>();
	
	
	/**
	 * Default Constructor
	 * @param inProductTypeList
	 */
	public MetallicLevelSortRule(List<MetallicTier> inMetallicTierList) {
		this.metallicTiers = inMetallicTierList;
		this.execute();
	}

	
	/**{@inheritDoc}*/
	@SuppressWarnings("unchecked")
	private void execute() {
		Collections.sort(this.metallicTiers, new StringListComparator(MetallicLevelSortRule.metallicSortMaster));
	}
	
	/**
	 * @return the sorted list of metallic tiers as defined by the rule.
	 */
	public List<String> getSortedMetallicTiers() {
		List<String> returnValue = new ArrayList();
		
		for (MetallicTier tier : metallicTiers) {
			returnValue.add(tier.value);
		}

		return returnValue;
	}
}