package com;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class SqlReplaceBuild {

	public static void main(String[] args) {
		SqlReplaceBuild o = new SqlReplaceBuild();
		
		String result = o.buildPhoneNumberReplace("COLUMN_NAME");
		System.out.println(result);
	}
	
    public String buildPhoneNumberReplace(String columnName) {
        Map<String,String> replacements = new HashMap<String, String>();
        replacements.put("/","");
        replacements.put(".","");
        replacements.put("+","");
        replacements.put("-","");
        replacements.put("(","");
        replacements.put(")","");
        replacements.put("EXT","X");

        String result = "";
        for (Map.Entry<String, String> entry : replacements.entrySet()) {
            String from = entry.getKey();
            String to = entry.getValue();
            if (result.isEmpty()) {
                result = "REPLACE(" + columnName + ",'" + from + "','" + (StringUtils.isBlank(to) ? "'" : to) + ")";
            } else {
                result = "REPLACE(" + result + ",'" + from + "','" + (StringUtils.isBlank(to) ? "'" : to) + ")";
            }
        }

        return result;
    }

    //can implement using a generic method with constant map
    private static final Map<String, String> PHONE_REPLACEMENTS =
            Collections.unmodifiableMap(new HashMap<String, String>() {{
                put(" ","");
                put(",","");
                put("/","");
                put(".","");
                put("+","");
                put("-","");
                put("(","");
                put(")","");
                put("EXT","X");
            }});

    
    private String buildReplace(String columnName, Map<String,String> replacements) {
        String result = "";
        for (Map.Entry<String, String> entry : replacements.entrySet()) {
            String from = entry.getKey();
            String to = entry.getValue();
            if (result.isEmpty()) {
                result = "REPLACE(" + columnName + ",'" + from + "','" + (StringUtils.isBlank(to) ? "'" : to) + ")";
            } else {
                result = "REPLACE(" + result + ",'" + from + "','" + (StringUtils.isBlank(to) ? "'" : to) + ")";
            }
        }

        return result;
    }


}
