package com.opencsv.read;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class CsvBean { 
	
	
	@Override
	public String toString()
	{
	    return ToStringBuilder.reflectionToString(this);
	}
	
}
