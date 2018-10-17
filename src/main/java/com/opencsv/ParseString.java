package com.opencsv;

import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import au.com.bytecode.opencsv.bean.CsvToBean;
import au.com.bytecode.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;

public class ParseString {

	public static void main(String[] args) {
		new ParseString().testParse();
	}
	
	@SuppressWarnings("unchecked")
	public void testParse() {
	    String s = "n,o,foo\n" + //headers
	            "kyle,123456,emp123\n" + //line1
	            "jimmy,abcnum,cust09878"; //line2
	    HeaderColumnNameTranslateMappingStrategy strat = new HeaderColumnNameTranslateMappingStrategy();
	    strat.setType(TestBean.class);
	    Map<String, String> map = new HashMap<String, String>();
	    map.put("n", "name");
	    map.put("o", "orderNumber");
	    map.put("foo", "id");
	    strat.setColumnMapping(map);

	    CsvToBean csv = new CsvToBean();
	    List<TestBean> beans = csv.parse(strat, new StringReader(s));
	    for (TestBean bean : beans) {
	    	System.out.println(bean);
	    }
	}
	
	public static class TestBean {
		String name;
		String orderNumber;
		String id;
		
		public TestBean() {}
		
		public String getName() {
			return this.name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getOrderNumber() {
			return this.orderNumber;
		}
		public void setOrderNumber(String orderNumber) {
			this.orderNumber = orderNumber;
		}
		public String getId() {
			return this.id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String toString() {
		       return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
		}
		
	}
	
}
