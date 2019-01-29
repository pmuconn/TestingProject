package au.opencsv;

import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.CsvToBean;
import au.com.bytecode.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;

public class ParseCsv {

	public static void main(String[] args) {
		new ParseCsv().testParse();
	}
	
	@SuppressWarnings("unchecked")
	public void testParse() {
	    HeaderColumnNameTranslateMappingStrategy<TestBean> strat = new HeaderColumnNameTranslateMappingStrategy<TestBean>();
	    strat.setType(TestBean.class);
	    Map<String, String> map = new HashMap<String, String>();
	    map.put("Fname", "firstName");
	    map.put("Lname", "lastName");
	    map.put("Id", "id");
	    map.put("doesntExist", "nope");
	    strat.setColumnMapping(map);

	    CSVReader reader = new CSVReader(new InputStreamReader(ClassLoader.getSystemResourceAsStream("test.csv")));
	    
	    CsvToBean<TestBean> csv = new CsvToBean<TestBean>();
	    List<TestBean> beans = csv.parse(strat, reader);
	    
	    for (TestBean bean : beans) {
	    	System.out.println(bean);
	    }
	}
	
	public static class TestBean {
		String firstName;
		String lastName;
		String id;
		
		public TestBean() {}
		
		public String getId() {
			return this.id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String toString() {
		       return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
		}

		public String getFirstName() {
			return this.firstName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		public String getLastName() {
			return this.lastName;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}
		
	}
	
}
