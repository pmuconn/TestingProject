package com.opencsv.write;

import org.apache.commons.lang3.StringUtils;

import com.opencsv.bean.BeanField;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvBindByName;

/**
 * @author pam16105
 *
 * @param <T>
 */
public class CustomMappingStrategy<T> extends ColumnPositionMappingStrategy<T> {
    @Override
    public String[] generateHeader() {
        final int numColumns = findMaxFieldIndex();
        if (!isAnnotationDriven() || numColumns == -1) {
            return super.generateHeader();
        }

        header = new String[numColumns + 1];

        BeanField beanField;
        for (int i = 0; i <= numColumns; i++) {
            beanField = findField(i);
            String columnHeaderName = extractHeaderName(beanField);
            header[i] = columnHeaderName;
        }
        return header;
    }

    private String extractHeaderName(final BeanField beanField) {
    	String result = StringUtils.EMPTY; //If anything, return empty header name.
    	
    	if (beanField != null && beanField.getField() != null) {
    		result =  beanField.getField().getName(); //If we have a beanfield, set headername to field name.
    	}
    	
        final CsvBindByName bindByNameAnnotation = beanField.getField().getDeclaredAnnotationsByType(CsvBindByName.class)[0];
        if (StringUtils.isNotBlank(bindByNameAnnotation.column())) {
        	result = bindByNameAnnotation.column(); //If we have a CsvBindByName, return the name as header.
        }
        
        return result;
    }
}
