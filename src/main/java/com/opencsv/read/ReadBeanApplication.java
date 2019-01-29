package com.opencsv.read;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.MappingStrategy;

public class ReadBeanApplication {


    public static void main(String[] args) {
        simpleSyncPositionBeanExample();
        namedSyncColumnBeanExample();
    }
    
    public static String simpleSyncPositionBeanExample() {
        Path path = null;
        try {
            path = Helpers.twoColumnCsvPath();
        } catch (Exception ex) {
            Helpers.err(ex);
        }
        return beanBuilderExample(path, SimplePositionBean.class).toString();
    }

    public static String namedSyncColumnBeanExample() {
        Path path = null;
        try {
            path = Helpers.namedColumnCsvPath();
        } catch (Exception ex) {
            Helpers.err(ex);
        }
        return beanBuilderExampleNamed(path, NamedColumnBean.class).toString();
    }
    
    public static List<CsvBean> beanBuilderExample(Path path, Class clazz) {
        ColumnPositionMappingStrategy ms = new ColumnPositionMappingStrategy();
        return beanBuilderExample(path, clazz, ms);
    }

    public static List<CsvBean> beanBuilderExample(Path path, Class clazz, MappingStrategy ms) {
        CsvTransfer csvTransfer = new CsvTransfer();
        try {
            ms.setType(clazz);

            Reader reader = Files.newBufferedReader(path);
            
            final CSVParser parser =
            		new CSVParserBuilder()
            		.withSeparator(CSVParser.DEFAULT_SEPARATOR)
            		.withIgnoreQuotations(true)
            		.build();
            
            final CSVReader reader2 =
            		new CSVReaderBuilder(reader)
            		.withSkipLines(1) //skip the first line which is the header.
            		.withCSVParser(parser)
            		.build();

            CsvToBean ctb = new CsvToBean();
            csvTransfer.setCsvList(ctb.parse(ms,reader2));
            
// This was the orginal code which was also parsing the header...which we don't want.            
//            CsvToBean cb = new CsvToBeanBuilder(reader).withType(clazz)
//                .withMappingStrategy(ms)
//                .build();
//            csvTransfer.setCsvList(cb.parse());
            
            reader.close();

        } catch (Exception ex) {
            Helpers.err(ex);
        }
        printCsvBean(csvTransfer.getCsvList());
        return csvTransfer.getCsvList();
    }
    
    public static List<CsvBean> beanBuilderExampleNamed(Path path, Class clazz) {
        CsvTransfer csvTransfer = new CsvTransfer();
        try {

            Reader reader = Files.newBufferedReader(path);
            
            CsvToBean cb = new CsvToBeanBuilder(reader).withType(clazz)
              //  .withMappingStrategy(ms)
                .build();
            csvTransfer.setCsvList(cb.parse());
            
            reader.close();

        } catch (Exception ex) {
            Helpers.err(ex);
        }
        printCsvBean(csvTransfer.getCsvList());
        return csvTransfer.getCsvList();
    }
    
    public static void printCsvBean (List<CsvBean> beans) {
    	for (CsvBean bean : beans) {
    		Helpers.print(bean.toString());
    	}
    }
    
    
}
