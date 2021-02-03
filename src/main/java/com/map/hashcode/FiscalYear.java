package com.map.hashcode;

import java.util.Objects;

import org.apache.commons.lang.builder.ToStringBuilder;


public class FiscalYear {
	
	private Integer year;
	private String period;

	public FiscalYear(Integer year, String period) {
		this.year = year;
		this.period = period;
	}

	/**
	 * equals and hashCode for determining object equality which can be used in collections.
	 */
	
    @Override
    public boolean equals(Object o) {

        if (o == this) {
            return true;
        }
        if (!(o instanceof FiscalYear)) {
            return false;
        }
        FiscalYear oc = (FiscalYear) o;
        return  Objects.equals(year, oc.year) && Objects.equals(period, oc.period) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(year,period);
    }

	
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	
}
